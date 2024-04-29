package com.huadiao.util;

import com.huadiao.entity.upload.FileInfo;
import com.huadiao.entity.upload.PreloadReturnValue;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author flowerwine
 * @date 2024 年 04 月 20 日
 */
public abstract class FragmentUpload {
    /**
     * 存储临时文件的文件夹
     */
    private final String TEMP_DIR;

    /**
     * 上传好的文件存储位置
     */
    private final String FILE_STORE_DIR;

    /**
     * 分块大小
     */
    private final int CHUNK_SIZE;

    /**
     * 暂存上传的文件信息
     */
    private final Map<String, FileInfo> FILE_INFO_MAP = new ConcurrentHashMap<>();

    public FragmentUpload(String tempDir, String fileStoreDir, int chunkSize) {
        this.TEMP_DIR = tempDir;
        this.CHUNK_SIZE = chunkSize;
        this.FILE_STORE_DIR = fileStoreDir;

        // 目录不存在就创建
        createDirectory(TEMP_DIR);
        createDirectory(FILE_STORE_DIR);
    }

    /**
     * 预上传文件
     * @param serverFilename 服务端文件名
     * @param clientFilename 客户端文件名
     * @param size 文件大小
     * @return 返回结果
     */
    public PreloadReturnValue preUpload(String serverFilename, String clientFilename, Long size) {
        FileInfo fileInfo = getFileInfo(serverFilename);

        if(fileInfo == null) {
            fileInfo = new FileInfo();

            fileInfo.setFileSize(size);

            int dotIndex = clientFilename.lastIndexOf(".");
            fileInfo.setSuffix(clientFilename.substring(dotIndex));

            // 保存客户端传递过来的文件名
            fileInfo.setFilenameFromClient(clientFilename.substring(0, dotIndex));
            // 保存服务器生成的文件名
            fileInfo.setFilenameFromServer(serverFilename);

            // 计算分片数量
            long count = 0;
            int chunkAmount = 0;
            while (count < size) {
                count += CHUNK_SIZE;
                chunkAmount++;
            }
            // 保存分片数量
            fileInfo.setChunkCount(chunkAmount);

            // 设置已经上传的分片记录数组
            fileInfo.setIsChunkUploadedArr(new boolean[chunkAmount]);

            // 创建分片上传的临时目录名称
            String chunkTempDirName = customTempDirName(serverFilename);
            File file = createDirectory(this.TEMP_DIR + File.separator + chunkTempDirName);
            fileInfo.setChunkTempDir(file);

            FILE_INFO_MAP.put(serverFilename, fileInfo);
        }

        // 设置上传时间
        fileInfo.setUploadTime(new Date());

        // 查找已上传的分片索引
        List<Integer> list = new ArrayList<>();
        boolean[] isChunkUploadedArr = fileInfo.getIsChunkUploadedArr();

        for(int i = 0; i < isChunkUploadedArr.length; i++) {
            if(isChunkUploadedArr[i]) {
                list.add(i);
            }
        }
        return handlePreloadReturnValue(fileInfo, list, CHUNK_SIZE);
    }

    /**
     * 自定义存储分片的临时文件夹名称
     * @param serverFilename 服务器生成的文件名
     * @return 返回名称
     */
    protected String customTempDirName(String serverFilename) {
        return String.format("%s_%s_%s", "chunk", serverFilename, System.currentTimeMillis());
    }

    /**
     * 持续上传文件分片
     * @param file 分片文件
     * @param index 分片在整个文件中的索引
     * @param serverFilename 服务端文件名
     * @return 返回分片是否上传成功, 返回 false 表示服务端不存在该用户的上传记录
     * @throws IOException 可能会抛出异常
     */
    public boolean upload(MultipartFile file, Integer index, String serverFilename) throws IOException {
        FileInfo fileInfo = getFileInfo(serverFilename);

        // 如果提供的文件名对应的 FileInfo 查找不到, 或者该分片已经存在
        if(Objects.isNull(index) || Objects.isNull(fileInfo) || fileInfo.getIsChunkUploadedArr()[index]) {
            return false;
        }

        // 创建分片文件并写入数据
        File chunk = new File(fileInfo.getChunkTempDir().getAbsolutePath() + File.separator + index);
        chunk.createNewFile();
        file.transferTo(chunk);

        // 设置该分片状态为已上传
        fileInfo.getIsChunkUploadedArr()[index] = true;

        return true;
    }

    /**
     * 上传完毕, 对分片进行合并
     * @param serverFilename 服务端文件名
     * @return 返回合并是否成功, 返回 false 表示服务端不存在该用户的上传记录
     * @throws IOException 可能会抛出异常
     */
    public boolean uploaded(String serverFilename) throws IOException {
        FileInfo fileInfo = getFileInfo(serverFilename);

        if(Objects.isNull(fileInfo)) {
            return false;
        } else {
            // 检查所有分片是否均上传完毕
            boolean[] isChunkUploadedArr = fileInfo.getIsChunkUploadedArr();
            for(boolean b : isChunkUploadedArr) {
                if(!b) {
                    return false;
                }
            }
        }

        // 拼接最终文件路径, 并创建文件
        String storeFilename = customStoreFilename(fileInfo);
        File resultFile = new File(this.FILE_STORE_DIR + File.separator + storeFilename);
        resultFile.createNewFile();

        // 合并分片, 采用随机写入
        RandomAccessFile writeFile = new RandomAccessFile((resultFile), "rw");
        RandomAccessFile readFile;
        byte[] bytes = new byte[CHUNK_SIZE];

        File[] files = fileInfo.getChunkTempDir().listFiles();

        assert files != null;
        for (File file : files) {
            int pos = CHUNK_SIZE * Integer.parseInt(file.getName());
            writeFile.seek(pos);
            readFile = new RandomAccessFile(file, "r");
            while (readFile.read(bytes) != -1) {
                writeFile.write(bytes);
            }
            readFile.close();
        }
        writeFile.close();

        // 删除分片和临时目录
        for (File file : files) {
            file.delete();
        }
        fileInfo.getChunkTempDir().delete();

        // 清除文件信息
        clearFileInfo(serverFilename);

        return true;
    }

    /**
     * 取消上传
     * @param serverFilename 服务端生成的文件名
     * @return 返回是否取消成功, 返回 false 表示服务端不存在该用户的上传记录
     */
    public boolean cancel(String serverFilename) {
        FileInfo fileInfo = getFileInfo(serverFilename);

        if(Objects.isNull(fileInfo)) {
            return false;
        }

        File[] files = fileInfo.getChunkTempDir().listFiles();
        assert files != null;
        for (File file : files) {
            file.delete();
        }

        // 清除文件信息
        clearFileInfo(serverFilename);

        return true;
    }

    private FileInfo getFileInfo(String serverFilename) {
        return this.FILE_INFO_MAP.get(serverFilename);
    }

    private void clearFileInfo(String serverFilename) {
        this.FILE_INFO_MAP.remove(serverFilename);
    }

    /**
     * 创建目录, 如果目录不存在就创建, 否则就忽略
     * @param path 要创建的目录路径
     * @return 返回 {@link File} 对象
     */
    private File createDirectory(String path) {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 自定义文件存储的名称
     * @param fileInfo 文件信息
     * @return 返回自定义的文件名
     */
    protected abstract String customStoreFilename(FileInfo fileInfo);

    /**
     * 处理 preload 的返回值
     * @param fileInfo 文件信息
     * @param uploadedChunk 已上传的分片记录
     * @param chunkSize 分片大小
     * @return 处理好的返回值
     */
    protected abstract PreloadReturnValue handlePreloadReturnValue(FileInfo fileInfo, List<Integer> uploadedChunk, int chunkSize);

}

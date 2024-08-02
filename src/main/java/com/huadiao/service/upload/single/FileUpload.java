package com.huadiao.service.upload.single;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author flowerwine
 * @date 2024 年 07 月 20 日
 */
public abstract class FileUpload {

    private final String storeDir;

    public FileUpload(String storeDir) {
        this.storeDir = storeDir;

        createDirectory(storeDir);
    }

    public FileUploadReturnValue upload(MultipartFile file) throws IOException {
        FileUploadReturnValue result = new FileUploadReturnValue();

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filename = customFileName(file) + suffix;

        result.setFilename(filename);
        file.transferTo(new File(storeDir + File.separator + filename));

        return result;
    }

    /**
     * 自定义文件名, 不包含后缀
     * @param file 文件
     * @return 自定义文件名
     */
    protected String customFileName(MultipartFile file) {
        return UUID.randomUUID().toString();
    }

    private File createDirectory(String path) {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}

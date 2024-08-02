package com.huadiao.service;

import com.huadiao.service.upload.fragment.PreloadReturnValue;
import com.huadiao.service.upload.fragment.FragmentUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @author flowerwine
 * @date 2024 年 04 月 21 日
 */
@Slf4j
public abstract class AbstractFragmentUploadService extends AbstractService {

    @Resource
    protected FragmentUpload videoFragmentUploadBean;

    /**
     * 通用预上传函数
     * @param uid 用户 uid
     * @param clientFilename 服务端文件名
     * @param size 文件总大小
     * @return 返回 {@link PreloadReturnValue} 对象
     */
    protected PreloadReturnValue commonPreload(Integer uid, String clientFilename, Long size) {
        FragmentUpload fragmentUploadBean = videoFragmentUploadBean;
        String serverFilename = getUserUploadingServerFilename(uid);

        // 用户还未上传过文件, 生成随机名称
        if(serverFilename == null) {
            serverFilename = customServerFilename();
        }
        // 用户上传过文件, 将清除还未上传成功的文件
        else {
            fragmentUploadBean.cancel(serverFilename);
        }

        setUserUploadingServerFilename(uid, serverFilename);
        return fragmentUploadBean.preUpload(serverFilename, clientFilename, size);
    }

    protected String customServerFilename() {
        return UUID.randomUUID().toString();
    }

    /**
     * 通用上传分片函数
     * @param uid 用户 uid
     * @param file 分片文件
     * @param index 分片在整个文件中的索引
     * @return 返回分片上传是否成功
     * @throws IOException 肯恩惠抛出异常
     */
    protected boolean commonUpload(Integer uid, MultipartFile file, Integer index) throws IOException {
        String serverFilename = getUserUploadingServerFilename(uid);

        // 如果未经过预上传, 不允许进行后续操作
        if(serverFilename == null) {
            return false;
        }

        FragmentUpload fragmentUploadBean = videoFragmentUploadBean;
        try {
            return fragmentUploadBean.upload(file, index, serverFilename);
        } catch (IOException e) {
            log.warn("上传分片失败, 错误信息: {}", e.getMessage());
            throw new IOException(e);
        }
    }

    /**
     * 通用上传完成函数, 主要处理文件分片的合并
     * @param uid 用户 uid
     * @return 返回合并是否成功
     * @throws IOException 可能会抛出异常
     */
    protected boolean commonUploaded(Integer uid) throws IOException {
        FragmentUpload fragmentUploadBean = videoFragmentUploadBean;
        String serverFilename = getUserUploadingServerFilename(uid);

        // 如果未经过预上传, 不允许进行后续操作
        if(serverFilename == null) {
            return false;
        }

        try {
            return fragmentUploadBean.uploaded(serverFilename);
        } catch (IOException e) {
            log.warn("合并分片失败, 错误信息: {}", e.getMessage());
            throw new IOException(e);
        }
    }

    /**
     * 通用取消上传函数
     * @param uid 用户 uid
     * @return 返回是否取消成功
     */
    protected boolean commonCancel(Integer uid) {
        String serverFilename = getUserUploadingServerFilename(uid);

        // 如果未经过预上传, 不允许进行后续操作
        if(serverFilename == null) {
            return false;
        }
        FragmentUpload fragmentUploadBean = videoFragmentUploadBean;

        return fragmentUploadBean.cancel(serverFilename);
    }

    /**
     * 获取用户正在上传的服务端文件名
     * @param uid 用户 uid
     * @return 返回信息
     */
    protected abstract String getUserUploadingServerFilename(Integer uid);

    /**
     * 设置用户正在上传的服务端文件名, 每个用户限制只能存在一个正在上传的文件, 所以这是覆盖操作
     * @param uid 用户 uid
     * @param serverFilename 服务端文件名
     */
    protected abstract void setUserUploadingServerFilename(Integer uid, String serverFilename);
}

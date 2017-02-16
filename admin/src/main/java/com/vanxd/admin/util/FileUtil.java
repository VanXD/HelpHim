package com.vanxd.admin.util;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.data.util.VanStringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author wyd on 2017/2/15.
 */
@Component
public class FileUtil {
    @Value("${uploadFile.prefix}")
    private String uploadFilePrefix;
    @Value("${env}")
    private String env;

    /**
     * 保存文件
     * 测试环境、开发环境保存到本地
     * 生产环境另写
     * @param file
     * @return
     */
    public UploadFile save(MultipartFile file) {
        if (null == file || file.isEmpty()) {
            throw new BusinessException("待保存的文件为空！");
        }
        if (env.equals(GlobalKey.PROFILE_PRO)) {
            // todo 生产环境保存图片
            return null;
        }
        return saveLocal(file);
    }

    /**
     * 保存文件到本地
     * @param file
     * @return
     */
    private UploadFile saveLocal(MultipartFile file) {
        String fileName = VanStringUtil.uuid() + getSuffix(file);
        String filePath = uploadFilePrefix + fileName;
        FileChannel fileChannel;
        try {
            fileChannel = new FileOutputStream(filePath).getChannel();
            fileChannel.write(ByteBuffer.wrap(file.getBytes()));
            fileChannel.close();
        } catch (FileNotFoundException e) {
            throw new BusinessException(e.getMessage());
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
        return new UploadFile(fileName, filePath);
    }

    /**
     * 获得文件的扩展名
     * @param file
     * @return
     */
    public String getSuffix(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    public class UploadFile {
        private String fileName;
        private String url;

        public UploadFile(String fileName, String url) {
            this.fileName = fileName;
            this.url = url;
        }

        public UploadFile() {
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

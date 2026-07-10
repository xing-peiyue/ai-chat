package com.chat.common.config;

import com.chat.common.utils.file.FileUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件路径配置
 *
 * @author: xingpeiyue
 * @time: 2021/12/24 16:47
 */
@Data
@Component
@ConfigurationProperties(prefix = "project.upload")
public class UploadProjectProperties {

    /**
     * 上传文件路径
     */
    private String filePath;

    private static String domain;

    public static String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        UploadProjectProperties.domain = domain;
    }

    /**
     * 上传文件静态访问路径
     */
    private String path = "upload";
    private String staticPath = "/" + path + "/**";

    public String getServerUrl() {
        if (!domain.endsWith("/")) {
            return domain + "/" + path;
        }
        return domain + path;
    }

    public String getPath() {
        return path;
    }

    /**
     * 获取文件路径
     */
    public String getFilePath() {
        if (filePath == null) {
            return FileUtils.getProjectPath() + "/upload/";
        }
        if (!filePath.endsWith("/")) {
            return filePath + "/";
        }
        return filePath;
    }

    public String getCertPath() {
        return "/home/cert/";
    }
}

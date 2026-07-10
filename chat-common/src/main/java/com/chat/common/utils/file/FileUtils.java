package com.chat.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.chat.common.config.ChatConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import com.chat.common.utils.DateUtils;
import com.chat.common.utils.StringUtils;
import com.chat.common.utils.uuid.IdUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * 文件处理工具类
 *
 * @author xpy
 */
public class FileUtils {
    /**
     * 获取项目不同模式下的根路径
     *
     * @author: xingpeiyue
     * @time: 2021/12/24 17:52
     */
    public static String getProjectPath() {
        String filePath = FileUtils.class.getResource("").getPath();
        String projectPath = FileUtils.class.getClassLoader().getResource("").getPath();
        StringBuilder path = new StringBuilder();

        if (!filePath.startsWith("file:/")) {
            // 开发模式下根路径
            char[] filePathArray = filePath.toCharArray();
            char[] projectPathArray = projectPath.toCharArray();
            for (int i = 0; i < filePathArray.length; i++) {
                if (projectPathArray.length > i && filePathArray[i] == projectPathArray[i]) {
                    path.append(filePathArray[i]);
                } else {
                    break;
                }
            }
        } else if (!projectPath.startsWith("file:/")) {
            // 部署服务器模式下根路径
            projectPath = projectPath.replace("/WEB-INF/classes/", "");
            projectPath = projectPath.replace("/target/classes/", "");
            try {
                path.append(URLDecoder.decode(projectPath, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return projectPath;
            }
        } else {
            // jar包启动模式下根路径
            String property = System.getProperty("java.class.path");
            int firstIndex = property.lastIndexOf(System.getProperty("path.separator")) + 1;
            int lastIndex = property.lastIndexOf(File.separator) + 1;
            path.append(property, firstIndex, lastIndex);
        }

        File file = new File(path.toString());
        String rootPath = "/";
        try {
            rootPath = URLDecoder.decode(file.getAbsolutePath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rootPath.replaceAll("\\\\", "/");
    }

    public static String getType(String originalFilename) {
        return StrUtil.subAfter(originalFilename, ".", true);
    }

}

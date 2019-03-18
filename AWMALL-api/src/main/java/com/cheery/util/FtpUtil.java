package com.cheery.util;

import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @desc: FTP文件上传工具类
 * @className: FtpUtil
 * @author: RONALDO
 * @date: 2019-03-11 15:54
 */
@Data
public class FtpUtil {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public static boolean uploadFile(File file) throws IOException {
        FtpUtil ftpUtil = new FtpUtil("106.13.45.248", 21, "ronaldo", "nTkA7zFGKt3jnrYP");
        logger.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img/", file);
        logger.info("开始连接ftp服务器,结束上传,上传结果:{}", result);
        return result;
    }

    private boolean uploadFile(String remotePath, File file) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        //连接FTP服务器
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                fis = new FileInputStream(file);
                ftpClient.storeFile(file.getName(), fis);
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                uploaded = false;
                e.printStackTrace();
            } finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    private boolean connectServer(String ip, int port, String user, String pwd) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("连接FTP服务器异常", e);
        }
        return isSuccess;
    }

    public FtpUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

}

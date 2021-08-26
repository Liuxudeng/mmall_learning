package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service("ifileService")
public class IFileServiceImpl implements IFileService {
    //因为该功能要多次使用，所以在这里打印日志
    private Logger logger = (Logger) LoggerFactory.getLogger(IFileServiceImpl.class);

@Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);

        //上传文件的名字
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;

        //     logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
//logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);


        //声明目录
        File fileDir = new File(path);
        if (!fileDir.exists()) {

            //赋予可写的用户权限
            fileDir.setWritable(true);

            //创建路径
            fileDir.mkdir();
        }

        File targetFile = new File(path, uploadFileName);

        try {
            file.transferTo(targetFile);

            //已经上传到ftp服务器中
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            //todo 将targetFile上传到我们的FTP服务器上
            targetFile.delete();

        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }

        return targetFile.getName();


    }
}

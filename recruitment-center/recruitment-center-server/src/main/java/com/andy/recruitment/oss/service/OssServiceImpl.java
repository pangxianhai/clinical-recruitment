package com.andy.recruitment.oss.service;

import com.aliyun.oss.OSSClient;
import com.xgimi.commons.util.encrypt.HashUtil;
import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 存储服务实现阿里云实现
 *
 * @author 庞先海 2019-04-13
 */
@Service
public class OssServiceImpl implements OssService {

    private final OssProperties ossProperties;

    @Autowired
    public OssServiceImpl(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Override
    public String saveFile(byte[] data, String suffix) {
        OSSClient ossClient = new OSSClient(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(),
                                            ossProperties.getAccessKeySecret());
        String fileName = ossProperties.getDirname() + "/" + HashUtil.md5Hash(data) + "." + suffix;
        ossClient.putObject(ossProperties.getBucketName(), fileName, new ByteArrayInputStream(data));
        ossClient.shutdown();
        return fileName;
    }

    @Override
    public void deleteFile(String fileName) {
        OSSClient ossClient = new OSSClient(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(),
                                            ossProperties.getAccessKeySecret());
        ossClient.deleteObject(ossProperties.getBucketName(), fileName);
        ossClient.shutdown();
    }

    @Override
    public String generateUrl(String fileName) {
        return "http://" + ossProperties.getImageDomain() + "/" + fileName;
    }

    @Override
    public String generateUrl(String fileName, int width, int height) {
        return this.generateUrl(fileName) + "?x-oss-process=image/resize,m_fixed,w_" + width + ",h_" + height;
    }
}

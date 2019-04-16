package com.andy.recruitment.oss.service;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.encrypt.HashUtil;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
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
        String fileName = HashUtil.md5Hash(data) + "." + suffix;
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

    public String generateUrl(String fileName) {
        OSSClient ossClient = new OSSClient(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(),
                                            ossProperties.getAccessKeySecret());
        Date expiration = new Date(DateUtil.currentMilliseconds() + 1000 * 60 * 30 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(ossProperties.getBucketName(), fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

        ossClient.shutdown();
        return signedUrl.toString();
    }
}

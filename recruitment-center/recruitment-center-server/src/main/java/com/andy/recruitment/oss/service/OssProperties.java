package com.andy.recruitment.oss.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 对象存储服务配置
 *
 * @author 庞先海 2019-04-13
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    /**
     * 阿里云访问域名
     */
    private String endpoint;
    /**
     * 阿里云访问key
     */
    private String accessKeyId;
    /**
     * 阿里云访问密钥
     */
    private String accessKeySecret;
    /**
     * 阿里云存储空间（Bucket）
     */
    private String bucketName;
    /**
     * 文件访问域名
     */
    private String imageDomain;
    /**
     * 存储目录
     */
    private String dirname;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getImageDomain() {
        return imageDomain;
    }

    public void setImageDomain(String imageDomain) {
        this.imageDomain = imageDomain;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }
}

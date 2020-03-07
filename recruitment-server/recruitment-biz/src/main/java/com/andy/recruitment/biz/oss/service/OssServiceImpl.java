package com.andy.recruitment.biz.oss.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 阿里云oss存储实现
 *
 * @author 庞先海 2020-03-05
 */
@Service
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.imageDomain}")
    private String imageDomain;

    @Override
    public String generateUrl(String fileName) {
        return imageDomain + "/" + fileName;
    }

    @Override
    public String generateUrl(String fileName, int width, int height) {
        return imageDomain + "/" + fileName + "?x-oss-process=image/resize,m_fixed,w_" + width + ",h_" + height;
    }
}

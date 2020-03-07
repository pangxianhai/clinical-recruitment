package com.andy.recruitment.biz.oss.service;

/**
 * 阿里云oss存储
 *
 * @author 庞先海 2020-03-05
 */
public interface OssService {

    /**
     * 生成图片地址
     *
     * @param fileName 文件名
     * @return 图片地址
     */
    String generateUrl(String fileName);

    /**
     * 生成缩放图片地址
     *
     * @param fileName 文件名
     * @param width    宽度
     * @param height   高度
     * @return 图片地址
     */
    String generateUrl(String fileName, int width, int height);
}

package com.andy.recruitment.oss.service;

/**
 * 对象存储服务
 *
 * @author 庞先海 2019-04-13
 */
public interface OssService {

    /**
     * 上传文件
     *
     * @param data   图片数据
     * @param suffix 文件后缀名
     * @return 文件名
     */
    String saveFile(byte[] data, String suffix);

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    void deleteFile(String fileName);

    /**
     * 生成图片地址
     *
     * @param fileName 文件名
     * @return 图片地址
     */
    String generateUrl(String fileName);
}

package com.andy.recruitment.oss.ao;

/**
 * 对象存储AO
 *
 * @author 庞先海 2019-04-13
 */
public interface OssAO {

    /**
     * 上传文件
     *
     * @param data   图片数据
     * @param suffix 文件后缀名
     * @return 文件名
     */
    String saveFile(byte[] data, String suffix);
}

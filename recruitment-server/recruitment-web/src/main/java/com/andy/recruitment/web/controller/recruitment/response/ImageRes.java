package com.andy.recruitment.web.controller.recruitment.response;

import java.io.Serializable;
import lombok.Data;

/**
 * 图片信息
 *
 * @author 庞先海 2020-03-05
 */
@Data
public class ImageRes implements Serializable {

    private static final long serialVersionUID = 1865682840495248169L;

    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 缩略图地址
     */
    private String thumbnailUrl;


    public ImageRes() {}

    public ImageRes(String imageUrl, String thumbnailUrl) {
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
}

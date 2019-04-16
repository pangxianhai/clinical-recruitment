package com.andy.recruitment.web.controller.oss.response;

import java.io.Serializable;

/**
 * 图片上传返回结果
 *
 * @author 庞先海 2019-04-16
 */
public class UploadImageVO implements Serializable {

    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 缩略图地址
     */
    private String thumbnailUrl;
    /**
     * 图片ID
     */
    private String imageId;

    public UploadImageVO() {}

    public UploadImageVO(String imageId, String imageUrl, String thumbnailUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

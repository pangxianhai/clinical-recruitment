package com.andy.recruitment.web.controller.oss.webservice;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.oss.ao.OssAO;
import com.andy.recruitment.web.controller.oss.request.OssUploadRQ;
import com.andy.recruitment.web.controller.oss.response.UploadImageVO;
import com.xgimi.commons.util.encrypt.EncodeUtil;
import com.xgimi.logger.log4j.Logger;
import com.xgimi.logger.log4j.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对象存储接口
 *
 * @author 庞先海 2019-04-13
 */
@RestController
@RequestMapping("/oss")
public class OssWebservice {

    @Logger
    private MyLogger logger;

    private final OssAO ossAO;

    @Autowired
    public OssWebservice(OssAO ossAO) {
        this.ossAO = ossAO;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UploadImageVO uploadFile(@RequestBody OssUploadRQ uploadRQ) {
        String suffix;
        byte[] data;
        try {
            String[] block = uploadRQ.getData().split(";");
            String prefix = block[0];
            suffix = prefix.split("/")[1];
            String[] content = block[1].split(",");
            if (! "base64".equalsIgnoreCase(content[0])) {
                throw new BusinessException(BusinessErrorCode.FILE_FORMATE_ERROR);
            }
            data = EncodeUtil.base64DecodeByte(content[1]);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.FILE_FORMATE_ERROR, e);
        }
        String imageId = this.ossAO.saveFile(data, suffix);
        String imageUrl = this.ossAO.generateUrl(imageId);
        return new UploadImageVO(imageId, imageUrl);
    }
}

package com.andy.recruitment.oss.ao;

import com.andy.recruitment.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 对象存储AO
 *
 * @author 庞先海 2019-04-13
 */
@Component
public class OssAOImpl implements OssAO {

    private final OssService ossService;

    @Autowired
    public OssAOImpl(OssService ossService) {
        this.ossService = ossService;
    }

    @Override
    public String saveFile(byte[] data, String suffix) {
        return this.ossService.saveFile(data, suffix);
    }
}

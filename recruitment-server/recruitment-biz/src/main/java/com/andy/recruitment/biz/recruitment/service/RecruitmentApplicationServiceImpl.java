package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.api.hospital.response.DepartmentDetailRes;
import com.andy.recruitment.api.patient.response.PatientInfoDetailRes;
import com.andy.recruitment.api.recruitment.response.ImageRes;
import com.andy.recruitment.api.recruitment.response.RecruitmentApplicationDetailRes;
import com.andy.recruitment.api.recruitment.response.RecruitmentInfoRes;
import com.andy.recruitment.api.reference.response.ReferenceDetailInfoRes;
import com.andy.recruitment.biz.hospital.service.DepartmentService;
import com.andy.recruitment.biz.oss.service.OssService;
import com.andy.recruitment.biz.patient.service.PatientInfoService;
import com.andy.recruitment.biz.reference.service.ReferenceService;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentApplicationDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentApplicationQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.DateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招募申请记录服务实现
 *
 * @author 庞先海 2020-03-01
 */
@Service
public class RecruitmentApplicationServiceImpl implements RecruitmentApplicationService {

    private final static String DISEASE_IMAGE_SPACE = ",";

    private final static String DATE_PATTERN = "yyyy-MM-dd";

    private final RecruitmentApplicationDAO recruitmentApplicationDAO;

    private final OssService ossService;

    private final RecruitmentService recruitmentService;

    private final PatientInfoService patientInfoService;

    private final ReferenceService referenceService;

    private final DepartmentService departmentService;

    @Autowired
    public RecruitmentApplicationServiceImpl(RecruitmentApplicationDAO recruitmentApplicationDAO, OssService ossService,
        RecruitmentService recruitmentService, PatientInfoService patientInfoService, ReferenceService referenceService,
        DepartmentService departmentService) {
        this.recruitmentApplicationDAO = recruitmentApplicationDAO;
        this.ossService = ossService;
        this.recruitmentService = recruitmentService;
        this.patientInfoService = patientInfoService;
        this.referenceService = referenceService;
        this.departmentService = departmentService;
    }

    @Override
    public void addRecruitmentApplication(RecruitmentApplicationDO applicationDo, String operator) {
        this.recruitmentApplicationDAO.addRecruitmentApplication(applicationDo, operator);
    }

    @Override
    public PageResult<RecruitmentApplicationDetailRes> getRecruitmentApplicationInfo(
        RecruitmentApplicationQuery queryParam, Paginator paginator) {
        PageResult<RecruitmentApplicationDO> pageResult = recruitmentApplicationDAO.getRecruitmentApplicationInfo(
            queryParam, paginator);
        List<RecruitmentApplicationDetailRes> detailResList = this.transformApplicationDetailRes(pageResult.getData());
        return new PageResult<>(detailResList, pageResult.getPaginator());
    }

    @Override
    public RecruitmentApplicationDetailRes getRecruitmentApplicationInfo(Long applicationId) {
        RecruitmentApplicationDO applicationDo = this.recruitmentApplicationDAO.getRecruitmentApplicationInfo(
            applicationId);
        if (applicationDo == null) {
            return null;
        }
        List<RecruitmentApplicationDO> applicationDoList = Collections.singletonList(applicationDo);
        List<RecruitmentApplicationDetailRes> applicationDetailResList = this.transformApplicationDetailRes(
            applicationDoList);
        if (CollectionUtils.isEmpty(applicationDetailResList)) {
            return null;
        }
        return applicationDetailResList.get(0);
    }

    private List<RecruitmentApplicationDetailRes> transformApplicationDetailRes(
        List<RecruitmentApplicationDO> applicationDoList) {
        if (CollectionUtils.isEmpty(applicationDoList)) {
            return Collections.emptyList();
        }
        List<Long> recruitmentIdList = new ArrayList<>(applicationDoList.size());
        List<Long> departmentIdList = new ArrayList<>(applicationDoList.size());
        List<Long> patientUserIdList = new ArrayList<>(applicationDoList.size());
        List<Long> referenceUserIdList = new ArrayList<>(applicationDoList.size());
        for (RecruitmentApplicationDO applicationDo : applicationDoList) {
            if (applicationDo.getRecruitmentId() != null) {
                recruitmentIdList.add(applicationDo.getRecruitmentId());
            }
            if (applicationDo.getDepartmentId() != null) {
                departmentIdList.add(applicationDo.getDepartmentId());
            }
            if (applicationDo.getPatientUserId() != null) {
                patientUserIdList.add(applicationDo.getPatientUserId());
            }
            if (applicationDo.getReferenceUserId() != null) {
                referenceUserIdList.add(applicationDo.getReferenceUserId());
            }
        }

        Map<Long, RecruitmentInfoRes> recruitmentInfoResMap = this.recruitmentService.getRecruitmentInfoRes(
            recruitmentIdList);
        Map<Long, DepartmentDetailRes> departmentDetailResMap = this.departmentService.getDepartmentDetailRes(
            departmentIdList);
        Map<Long, PatientInfoDetailRes> patientInfoDetailResMap = this.patientInfoService.getReferenceDetailRes(
            patientUserIdList);
        Map<Long, ReferenceDetailInfoRes> referenceDetailInfoResMap = this.referenceService.getReferenceDetailInfoRes(
            referenceUserIdList);

        List<RecruitmentApplicationDetailRes> applicationDetailResList = new ArrayList<>(applicationDoList.size());
        for (RecruitmentApplicationDO applicationDo : applicationDoList) {
            RecruitmentApplicationDetailRes applicationDetailRes = this.transformApplicationDetailRes(applicationDo);
            if (applicationDetailRes == null) {
                continue;
            }
            if (MapUtils.isNotEmpty(recruitmentInfoResMap)) {
                applicationDetailRes.setRecruitmentInfoRes(recruitmentInfoResMap.get(applicationDo.getRecruitmentId()));
            }
            if (MapUtils.isNotEmpty(departmentDetailResMap)) {
                applicationDetailRes.setDepartmentDetailRes(
                    departmentDetailResMap.get(applicationDo.getDepartmentId()));
            }
            if (MapUtils.isNotEmpty(patientInfoDetailResMap)) {
                applicationDetailRes.setPatientInfoDetailRes(
                    patientInfoDetailResMap.get(applicationDo.getPatientUserId()));
            }
            if (MapUtils.isNotEmpty(referenceDetailInfoResMap)) {
                applicationDetailRes.setReferenceDetailInfoRes(
                    referenceDetailInfoResMap.get(applicationDo.getReferenceUserId()));
            }
            applicationDetailResList.add(applicationDetailRes);
        }
        return applicationDetailResList;
    }

    private RecruitmentApplicationDetailRes transformApplicationDetailRes(RecruitmentApplicationDO applicationDo) {
        if (applicationDo == null) {
            return null;
        }
        RecruitmentApplicationDetailRes applicationDetailRes = new RecruitmentApplicationDetailRes();
        BeanUtils.copyProperties(applicationDo, applicationDetailRes);
        applicationDetailRes.setDiseaseImageList(this.buildImageRes(applicationDo.getDiseaseImage()));
        applicationDetailRes.setApplicationId(applicationDo.getId());
        applicationDetailRes.setApplicationTime(DateUtil.format(applicationDo.getCreatedTime(), DATE_PATTERN));
        return applicationDetailRes;
    }

    private List<ImageRes> buildImageRes(String diseaseImage) {
        if (StringUtils.isEmpty(diseaseImage)) {
            return Collections.emptyList();
        }
        String[] diseaseImageArr = diseaseImage.split(DISEASE_IMAGE_SPACE);
        List<ImageRes> imageResList = new ArrayList<>(diseaseImageArr.length);
        for (String image : diseaseImageArr) {
            String imageUrl = ossService.generateUrl(image);
            String thumbnailUrl = ossService.generateUrl(image, 80, 80);
            ImageRes imageRes = new ImageRes(imageUrl, thumbnailUrl);
            imageResList.add(imageRes);
        }
        return imageResList;
    }
}

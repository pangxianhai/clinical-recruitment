package com.andy.recruitment.biz.recruitment.service;

import com.andy.recruitment.dao.hospital.entity.DepartmentDO;
import com.andy.recruitment.dao.recruitment.dao.RecruitmentDepartmentDAO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentDepartmentDO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目关联的科室实现
 *
 * @author 庞先海 2020-04-08
 */
@Service
public class RecruitmentDepartmentServiceImpl implements RecruitmentDepartmentService {

    private final RecruitmentDepartmentDAO recruitmentDepartmentDAO;

    @Autowired
    public RecruitmentDepartmentServiceImpl(RecruitmentDepartmentDAO recruitmentDepartmentDAO) {
        this.recruitmentDepartmentDAO = recruitmentDepartmentDAO;
    }

    @Override
    public List<RecruitmentDepartmentDO> listDepartmentByRecruitment(Long recruitmentId) {
        return this.recruitmentDepartmentDAO.listDepartmentByRecruitment(recruitmentId);
    }

    @Override
    public List<Long> listDepartmentIdByRecruitment(Long recruitmentId) {
        List<RecruitmentDepartmentDO> recruitmentDepartmentDoList = this.recruitmentDepartmentDAO.listDepartmentByRecruitment(
            recruitmentId);
        if (CollectionUtils.isEmpty(recruitmentDepartmentDoList)) {
            return Collections.emptyList();
        }
        return recruitmentDepartmentDoList.stream().filter(Objects::nonNull).map(
            RecruitmentDepartmentDO::getDepartmentId).collect(Collectors.toList());
    }

    @Override
    public void updateRecruitmentDepartment(Long recruitmentId, List<DepartmentDO> departmentDoList, String operator) {
        if (CollectionUtils.isEmpty(departmentDoList)) {
            departmentDoList = Collections.emptyList();
        }
        //比如 sourceDepartmentIdList=1,2,3  inputDepartmentIdList = 2,3,4 最终db里的数据应是2,3,4
        //在inputDepartmentIdList 移除已经在sourceDepartmentIdList 结果是4  needAddDepartmentIdList中应是4
        // sourceDepartmentIdList list中移除已经在 inputDepartmentIdList 中的 needDeleteDepartmentIdList中应是1
        List<Long> sourceDepartmentIdList = this.listDepartmentIdByRecruitment(recruitmentId);
        List<Long> inputDepartmentIdList = departmentDoList.stream().map(DepartmentDO::getId).collect(
            Collectors.toList());

        List<Long> needAddDepartmentIdList = new ArrayList<>(inputDepartmentIdList);
        //从需要添加的id 移除已经存在的 剩下的就是需要向db中添加的
        needAddDepartmentIdList.removeAll(sourceDepartmentIdList);

        List<Long> needDeleteDepartmentIdList = new ArrayList<>(sourceDepartmentIdList);
        if (CollectionUtils.isNotEmpty(sourceDepartmentIdList)) {
            //从原有列表中删除 需要添加的 则是要删除
            needDeleteDepartmentIdList.removeAll(inputDepartmentIdList);
        }

        if (CollectionUtils.isNotEmpty(needAddDepartmentIdList)) {
            Map<Long, Long> didMap = departmentDoList.stream().collect(
                Collectors.toMap(DepartmentDO::getId, DepartmentDO::getHospitalId));

            for (Long addDepartmentId : needAddDepartmentIdList) {
                this.recruitmentDepartmentDAO.addRecruitmentDepartment(recruitmentId, didMap.get(addDepartmentId),
                    addDepartmentId, operator);
            }
        }
        if (CollectionUtils.isNotEmpty(needDeleteDepartmentIdList)) {
            for (Long deleteDepartmentId : needDeleteDepartmentIdList) {
                this.recruitmentDepartmentDAO.deleteRecruitmentDepartment(recruitmentId, deleteDepartmentId);
            }
        }
    }

}

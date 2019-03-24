package com.andy.recruitment.web.scheduled;

import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 项目定时任务
 *
 * @author 庞先海 2019-03-24
 */
@Component
public class RecruitmentScheduled {

    private final RecruitmentAO recruitmentAO;

    @Autowired
    public RecruitmentScheduled(RecruitmentAO recruitmentAO) {
        this.recruitmentAO = recruitmentAO;
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void recruitmentStartScheduled() {
        RecruitmentQueryParam queryParam = new RecruitmentQueryParam();
        queryParam.setStatus(RecruitmentStatus.NOT_BEGIN);
        long currentTime = DateUtil.currentMilliseconds();
        this.recruitmentEach(queryParam, recruitmentInfo -> {
            if (recruitmentInfo.getStartTime().getTime() <= currentTime) {
                RecruitmentInfo updateInfo = new RecruitmentInfo();
                updateInfo.setRecruitmentId(recruitmentInfo.getRecruitmentId());
                updateInfo.setStatus(RecruitmentStatus.IN_PROCESS);
                this.recruitmentAO.updateRecruitmentInfo(updateInfo, "定时开始任务");
            }
        });
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void recruitmentStopScheduled() {
        RecruitmentQueryParam queryParam = new RecruitmentQueryParam();
        queryParam.setStatus(RecruitmentStatus.IN_PROCESS);
        long currentTime = DateUtil.currentMilliseconds();
        this.recruitmentEach(queryParam, recruitmentInfo -> {
            if (recruitmentInfo.getStopTime().getTime() <= currentTime) {
                RecruitmentInfo updateInfo = new RecruitmentInfo();
                updateInfo.setRecruitmentId(recruitmentInfo.getRecruitmentId());
                updateInfo.setStatus(RecruitmentStatus.FINISHED);
                this.recruitmentAO.updateRecruitmentInfo(updateInfo, "定时结束任务");
            }
        });
    }

    private void recruitmentEach(RecruitmentQueryParam queryParam, Handler handler) {
        Paginator paginator = new Paginator(0, 1000);
        do {
            paginator.nextPage();
            PageResult<RecruitmentInfo> pageResult = this.recruitmentAO.getRecruitmentInfo(queryParam, paginator);
            if (CollectionUtil.isEmpty(pageResult.getData())) {
                return;

            } else {
                for (RecruitmentInfo recruitmentInfo : pageResult.getData()) {
                    handler.doHandler(recruitmentInfo);
                }
            }
            paginator = pageResult.getPaginator();
        } while (paginator.hasNextPage());
    }

    private interface Handler {

        /**
         * 招募信息处理
         *
         * @param recruitmentInfo 招募信息
         */
        void doHandler(RecruitmentInfo recruitmentInfo);
    }
}

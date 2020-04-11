export const PatientStatus = {
  NORMAL: 1,
  FREEZE: 2
};

export const RecruitmentStatus = {
  NOT_BEGIN: 0,
  IN_PROCESS: 1,
  FINISHED: 2
};

export const ApplicationStatus = {
  NOT_ACCEDE: 1,
  ACCEDED: 2,
  REFUSED: 3
};

export const RecruitmentCategory = {
  TUMOUR: 1,
  NON_TUMOR: 2
};

/**
 * 推荐人角色
 */
export const ReferenceRole = {
  //研究员 科长
  HEADS_OF_DEPARTMENT: 1,
  //医生
  DOCTOR: 2
};

/**
 * 推荐人状态
 */
export const ReferenceStatus = {
  //冻结
  FREEZE: 0,
  //未审核
  UNAUDITED: 1,
  //审核拒绝
  REFUSE: 2,
  //审核通过
  ADOPT: 3
};




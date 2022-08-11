package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassCheckDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassEditDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassFeeItemEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassStudyInfoEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 培训班
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 11:35:05
 */
public interface EduTrainingClassService extends IService<EduTrainingClassEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean createTrainingClass(EduTrainingClassEntity eduTrainingClassEntity,
                                List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities,
                                List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities,
                                EduFlowTraceEntity entity, String confirmEmployeeId);

    Boolean updateTrainingClass(EduTrainingClassEntity eduTrainingClassEntity,
                                List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities,
                                List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities,
                                String roleCode, EduEmployeeEntity currentUser);

    Boolean updateTrainingClassFinal(EduTrainingClassEntity eduTrainingClassEntity,
                                List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities,
                                List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities,
                                String roleCode, EduEmployeeEntity currentUser, String confirmId, String confirmName);

    Boolean deleteTrainingClass(EduTrainingClassEditDto eduTrainingClassEditDto);

    Boolean checkSendTrainingClass(EduTrainingClassEditDto eduTrainingClassEditDto, String employeeId,HttpServletRequest request);

    //Boolean isTrainingClassAllowConfirm(String classId, String employeeId);

    Boolean trainingClassConfirmPass(EduTrainingClassCheckDto classId, String employeeId, boolean isPass, String opinion, HttpServletRequest request);

    PageUtils timeOut(Map<String, Object> params);


    Integer findNormal();

    Integer findOnline(int i);

    Integer findTotal();

    Integer findFinalNum();

    //Integer getTrainingClassStatus(String classId);

    //Boolean isTrainingClassShowConfirmBtn(String classId, String employeeId);
}


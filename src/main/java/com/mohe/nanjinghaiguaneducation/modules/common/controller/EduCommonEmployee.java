package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.EduEmployeeSearchDto;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduDeptSearchDto;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "通用接口")
@RequestMapping("common/employee")
public class EduCommonEmployee {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduEmployeeService eduEmployeeService;


    @PostMapping("/search")
    @ApiOperation("根据条件搜索人员")
    public ResultData<PageUtils> employeeList(@ApiParam(value = "搜索人员条件") @RequestBody EduEmployeeSearchDto eduEmployeeSearchDto){
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            Map<String, Object> filter = new HashMap<>();
            int page = eduEmployeeSearchDto.getPage();
            int limit = eduEmployeeSearchDto.getLimit();
            if(page<1 || limit<1){
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            filter.put("page", page);
            filter.put("limit", limit);
            String employeeCode = eduEmployeeSearchDto.getEmployeeCode();
            String employeeName = eduEmployeeSearchDto.getEmployeeName();
            if(employeeCode!=null){
                filter.put("employeeCode", employeeCode);
            }
            if(employeeName!=null){
                filter.put("employeeName", employeeName);
            }
            PageUtils pageUtils = eduEmployeeService.queryPage(filter);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    /**
     * 搜索部门
     */
    @PostMapping("/queryDept")
    @ApiOperation("搜索部门")
    public ResultData<PageUtils> queryDept(@RequestBody EduDeptSearchDto eduDeptSearchDto) {
        ResultData<PageUtils> resultData = new ResultData<>();
        logger.info("搜索部门 ---- ");
        Map<String, Object> filter = new HashMap<>();
        int page = eduDeptSearchDto.getPage();
        int limit = eduDeptSearchDto.getLimit();
        if(page<1 || limit<1){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
        filter.put("page", page);
        filter.put("limit", limit);
        String departmentCode = eduDeptSearchDto.getDepartmentCode();
        String departmentName = eduDeptSearchDto.getDepartmentName();
        if(departmentCode!=null){
            filter.put("departmentCode", departmentCode);
        }
        if(departmentName!=null){
            filter.put("departmentName", departmentName);
        }
        PageUtils pageUtils = eduEmployeeService.queryDept(filter);
        resultData.setData(pageUtils);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        return resultData;
    }
}

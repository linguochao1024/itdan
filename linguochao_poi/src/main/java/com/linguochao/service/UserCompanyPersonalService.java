package com.linguochao.service;

import com.linguochao.entity.EmployeeReportResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2019/8/6 13:43
 */
@Service
public class UserCompanyPersonalService {
    public List<EmployeeReportResult> findByReport(int companyId, String s) {
        List<EmployeeReportResult> list=new ArrayList<>();
        return list;
    }
}

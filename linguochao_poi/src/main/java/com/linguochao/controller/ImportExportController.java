package com.linguochao.controller;

import com.linguochao.entity.EmployeeReportResult;
import com.linguochao.entity.User;
import com.linguochao.service.UserCompanyPersonalService;
import com.linguochao.utils.ExcelExportUtil;
import com.linguochao.utils.ExcelImportUtil;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author linguochao
 * @Description 导入导出控制器
 * @Date 2019/8/6 13:42
 */
public class ImportExportController {

    @Autowired
    UserCompanyPersonalService userCompanyPersonalService;

    @Autowired
    HttpServletResponse response;

    //批量导出
    @RequestMapping(value = "/export/{month}", method = RequestMethod.GET)
    public void export(@PathVariable(name = "month") String month) throws Exception {

        int companyId=1;
        //1.构造数据
        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport(companyId,month+"%");
        //2.加载模板流数据
        Resource resource = new ClassPathResource("excel-template/hr-demo.xlsx");
        FileInputStream fis = new FileInputStream(resource.getFile());
        new ExcelExportUtil(EmployeeReportResult.class,2,2). export(response,fis,list, "人事报表.xlsx");
    }

    //批量导入数据
    @RequestMapping(value="/user/import", method = RequestMethod.POST)
    public Result importExcel(@RequestParam(name = "file") MultipartFile attachment ) throws Exception {
        //获取文件输入流
        InputStream is = attachment.getInputStream();
        //调用工具类读取数据
        List<User> list = new ExcelImportUtil( User.class ). readExcel(is, 1, 2);
        return null;
    }
}

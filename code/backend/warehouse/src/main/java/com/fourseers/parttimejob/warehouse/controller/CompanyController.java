package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.entity.Company;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONObject createCompany(@RequestBody Company company) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (companyService.findByCompanyName(company.getCompanyName()) != null) {
                jsonObject.put("code", 400);
                jsonObject.put("message", "company name exist");
            } else {
                companyService.save(company);
                jsonObject.put("code", 200);
                jsonObject.put("message", "success");
            }
        } catch (ConstraintViolationException ex) {
            jsonObject.put("code", 400);
            jsonObject.put("message", "failed");
        }
        return jsonObject;
    }
}

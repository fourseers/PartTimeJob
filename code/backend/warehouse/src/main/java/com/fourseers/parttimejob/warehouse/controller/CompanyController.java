package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.entity.Company;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> createCompany(@RequestBody JSONObject body,
                                                    @RequestHeader("x-internal-token") Integer userId) {
        JSONObject jsonObject = new JSONObject();

        Company company = new Company();
        company.setCompanyName(body.getString("companyName"));
        company.setAdminId(userId);

        HttpStatus status = HttpStatus.OK;
        try {
            if (companyService.findByCompanyName(company.getCompanyName()) != null) {
                jsonObject.put("status", 400);
                jsonObject.put("message", "company name exist");
                status = HttpStatus.BAD_REQUEST;
            } else {
                companyService.save(company);
                jsonObject.put("status", 200);
                jsonObject.put("message", "success");
            }
        } catch (ConstraintViolationException ex) {
            jsonObject.put("status", 400);
            jsonObject.put("message", "failed");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }
}

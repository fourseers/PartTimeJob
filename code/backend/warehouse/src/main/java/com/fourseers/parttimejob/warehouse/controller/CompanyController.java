package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping(value = "/merchant/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Response<JSONObject>> getCompany(@RequestHeader("x-internal-token") String username) {
        Company company = companyService.findByUsername(username);

        if (company == null) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "user not belong to any company");
        }

        JSONObject body = new JSONObject();
        body.put("company_name", company.getCompanyName());
        return ResponseBuilder.build(HttpStatus.OK, body, "success");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Response<JSONObject>> createCompany(@RequestBody JSONObject body,
                                                    @RequestHeader("x-internal-token") String bossName) {
        Company company = new Company();
        company.setCompanyName(body.getString("company_name"));

        try {
            if (companyService.findByCompanyName(company.getCompanyName()) != null) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "company name exists");
            } else {
                companyService.save(company, bossName);
                return ResponseBuilder.build(HttpStatus.OK, null, "success");
            }
        } catch (ConstraintViolationException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "failed");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
    }
}

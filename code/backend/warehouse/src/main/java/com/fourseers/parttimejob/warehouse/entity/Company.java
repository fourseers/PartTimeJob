package com.fourseers.parttimejob.warehouse.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "COMPANY")
public class Company {

    private Integer companyId;
    private String companyName;
    private Integer adminId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Column(name = "COMPANY_NAME", length = 64)
    @Size(max = 64)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "ADMIN_ID")
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}

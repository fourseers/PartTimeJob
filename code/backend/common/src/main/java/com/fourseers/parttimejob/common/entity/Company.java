package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "COMPANY")
public class Company {

    private Integer companyId;
    private String companyName;
    private MerchantUser boss;
    private List<MerchantUser> manager;
    private List<Shop> shops;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @NotBlank
    @Size(max = 64)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @OneToOne
    public MerchantUser getBoss() {
        return boss;
    }

    public void setBoss(MerchantUser boss) {
        this.boss = boss;
    }

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    public List<MerchantUser> getManager() {
        return manager;
    }

    public void setManager(List<MerchantUser> manager) {
        this.manager = manager;
    }

    @OneToMany(mappedBy = "company")
    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}

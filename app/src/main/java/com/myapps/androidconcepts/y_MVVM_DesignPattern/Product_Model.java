package com.myapps.androidconcepts.y_MVVM_DesignPattern;

public class Product_Model {
    private String Pname;
    private Long mobileNo;

    public Product_Model(String pname, Long mobileNo) {
        Pname = pname;
        this.mobileNo = mobileNo;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }
}

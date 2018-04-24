package com.example.sravanreddy.flopkart.model;

/**
 * Created by sravanreddy on 4/11/18.
 */

public class Catogories {
private String cid, cname, discription, cimagerl;

    public Catogories(String cid, String cname, String discription, String cimagerl) {
        this.cid = cid;
        this.cname = cname;
        this.discription = discription;
        this.cimagerl = cimagerl;
    }

    public String getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public String getDiscription() {
        return discription;
    }

    public String getCimagerl() {
        return cimagerl;
    }
}

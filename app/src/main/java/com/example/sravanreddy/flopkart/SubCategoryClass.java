package com.example.sravanreddy.flopkart;

/**
 * Created by sravanreddy on 4/12/18.
 */

public class SubCategoryClass {
    private String scid, scname, scdescription, scimageurl;

    public SubCategoryClass(String scid, String scname, String scdescription, String scimageurl) {
        this.scid = scid;
        this.scname = scname;
        this.scdescription = scdescription;
        this.scimageurl = scimageurl;
    }

    public String getScid() {
        return scid;
    }

    public String getScname() {
        return scname;
    }

    public String getScdescription() {
        return scdescription;
    }

    public String getScimageurl() {
        return scimageurl;
    }
}

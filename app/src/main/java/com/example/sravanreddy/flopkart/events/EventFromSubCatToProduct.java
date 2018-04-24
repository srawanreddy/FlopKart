package com.example.sravanreddy.flopkart.events;

/**
 * Created by sravanreddy on 4/13/18.
 */

public class EventFromSubCatToProduct {
    private String sCid;

    public EventFromSubCatToProduct(String sCid) {
        this.sCid = sCid;
    }

    public String getsCid() {
        return sCid;
    }
}

package com.example.sravanreddy.flopkart;

public class OrderDescription {
    private String orderid, orderstatus, name, billingadd, deliveryadd, mobile, email, itemid, itemname, itemquantity, totalprice,
            paidprice, placedon;

    public OrderDescription(String orderid, String orderstatus, String name, String billingadd, String deliveryadd, String mobile, String email, String itemid, String itemname, String itemquantity, String totalprice, String paidprice, String placedon) {
        this.orderid = orderid;
        this.orderstatus = orderstatus;
        this.name = name;
        this.billingadd = billingadd;
        this.deliveryadd = deliveryadd;
        this.mobile = mobile;
        this.email = email;
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemquantity = itemquantity;
        this.totalprice = totalprice;
        this.paidprice = paidprice;
        this.placedon = placedon;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public String getName() {
        return name;
    }

    public String getBillingadd() {
        return billingadd;
    }

    public String getDeliveryadd() {
        return deliveryadd;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getItemid() {
        return itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public String getPaidprice() {
        return paidprice;
    }

    public String getPlacedon() {
        return placedon;
    }
}

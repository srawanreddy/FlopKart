package com.example.sravanreddy.flopkart;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderConfirmation implements Parcelable{
    String orderid, orderstatus, name, billingadd, deliveryadd, mobile, email, itemid, itemname, itemquantity, totalprice, paidprice, placedon;

    public OrderConfirmation(String orderid, String orderstatus, String name, String billingadd, String deliveryadd, String mobile, String email, String itemid, String itemname, String itemquantity, String totalprice, String paidprice, String placedon) {
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

    protected OrderConfirmation(Parcel in) {
        orderid = in.readString();
        orderstatus = in.readString();
        name = in.readString();
        billingadd = in.readString();
        deliveryadd = in.readString();
        mobile = in.readString();
        email = in.readString();
        itemid = in.readString();
        itemname = in.readString();
        itemquantity = in.readString();
        totalprice = in.readString();
        paidprice = in.readString();
        placedon = in.readString();
    }

    public static final Creator<OrderConfirmation> CREATOR = new Creator<OrderConfirmation>() {
        @Override
        public OrderConfirmation createFromParcel(Parcel in) {
            return new OrderConfirmation(in);
        }

        @Override
        public OrderConfirmation[] newArray(int size) {
            return new OrderConfirmation[size];
        }
    };

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

    public static Creator<OrderConfirmation> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderid);
        parcel.writeString(orderstatus);
        parcel.writeString(name);
        parcel.writeString(billingadd);
        parcel.writeString(deliveryadd);
        parcel.writeString(mobile);
        parcel.writeString(email);
        parcel.writeString(itemid);
        parcel.writeString(itemname);
        parcel.writeString(itemquantity);
        parcel.writeString(totalprice);
        parcel.writeString(paidprice);
        parcel.writeString(placedon);

    }
}

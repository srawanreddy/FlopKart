package com.example.sravanreddy.flopkart.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductDetails implements Parcelable{
    private String id, name, quantity, price, discription, image;

    public ProductDetails(String id, String name, String quantity, String price, String discription, String image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discription = discription;
        this.image = image;
    }

    protected ProductDetails(Parcel in) {
        id = in.readString();
        name = in.readString();
        quantity = in.readString();
        price = in.readString();
        discription = in.readString();
        image = in.readString();
    }

    public static final Creator<ProductDetails> CREATOR = new Creator<ProductDetails>() {
        @Override
        public ProductDetails createFromParcel(Parcel in) {
            return new ProductDetails(in);
        }

        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscription() {
        return discription;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(id);
    parcel.writeString(name);
    parcel.writeString(quantity);
    parcel.writeString(price);
    parcel.writeString(discription);
    parcel.writeString(image);
    }
}

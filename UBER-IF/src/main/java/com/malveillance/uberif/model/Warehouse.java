package com.malveillance.uberif.model;

public class Warehouse extends Shape {
    public String address;

    public Warehouse(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void accept( ShapeVisitor v ){
        v.visit(this);
    }

    @Override
    public String toString() {
        return "Warehouse Address: " + address;
    }
}

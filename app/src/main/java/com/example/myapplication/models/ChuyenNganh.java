package com.example.myapplication.models;

public enum ChuyenNganh {
    CNTT("Đồ ăn Hàn Quốc"),
    KT("ĐỒ ăn Việt Nam"),
    TKDH("Đồ ăn Thái Lan"),
    QTKD("Đồ ăn Tây Ban Nha");

    private String description;

    ChuyenNganh(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
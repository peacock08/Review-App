
package com.example.myapplication.models;

public enum Loai {
    CNTT("Đồ ăn Hàn Quốc"),
    KT("Đồ ăn Việt Nam"),
    TKDH("Đồ ăn Thái Lan"),
    QTKD("Đồ ăn Tây Ban Nha");

    private String description;

    Loai(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
package com.example.myapplication.models;

import java.io.Serializable;

public class MonAn implements Serializable {
    private int ma;
    private Loai loai;
    private String ten, ngayBatDau, hocPhi;
    private int kichHoat;

    public MonAn(
            int ma,
            String ten,
            Loai loai,
            String ngayBatDau,
            String hocPhi,
            int kichHoat
    ) {
        this.ma = ma;
        this.loai = loai;
        this.ten = ten;
        this.ngayBatDau = ngayBatDau;
        this.hocPhi = hocPhi;
        this.kichHoat = kichHoat;
    }

    public MonAn(
            String ten,
            Loai loai,
            String ngayBatDau,
            String hocPhi,
            int kichHoat
    ) {
        this.loai = loai;
        this.ten = ten;
        this.ngayBatDau = ngayBatDau;
        this.hocPhi = hocPhi;
        this.kichHoat = kichHoat;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public Loai getChuyenNganh() {
        return loai;
    }

    public void setChuyenNganh(Loai loai) {
        this.loai = loai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(String hocPhi) {
        this.hocPhi = hocPhi;
    }

    public int getKichHoat() {
        return kichHoat;
    }

    public void setKichHoat(int kichHoat) {
        this.kichHoat = kichHoat;
    }
}
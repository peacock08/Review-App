package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.models.MonAn;


import java.util.List;

public class KhoaHocAdapter extends ArrayAdapter {
    Context context;
    List<MonAn> monAnList;
    private ListItemListener listItemListener;
    public KhoaHocAdapter(@NonNull Context context, @NonNull List list) {
        super(context, R.layout.item, list);

        this.context = context;
        this.monAnList = list;
    }

    public void setListItemListener(ListItemListener listItemListener) {
        this.listItemListener = listItemListener;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, null, true);
        TextView tenView = v.findViewById(R.id.item_ten);
        TextView chuyenNganh = v.findViewById(R.id.item_chuyenNganh);
        TextView ngayBatDauView = v.findViewById(R.id.item_ngayBatDau);
        TextView hocPhiView = v.findViewById(R.id.item_hocPhi);
        TextView kichHoatBox = v.findViewById(R.id.item_kichHoat);

        MonAn monAn = monAnList.get(position);
        tenView.setText(monAn.getTen());
        chuyenNganh.setText(monAn.getChuyenNganh().getDescription());
        ngayBatDauView.setText(monAn.getNgayBatDau());
        hocPhiView.setText(monAn.getHocPhi());
        kichHoatBox.setText(monAn.getKichHoat() == 1 ? "Đã review" : "Chưa review");

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.onItemClicked(v, position);
            }
        });
        return v;
    }

    //Item clicked sent to Main
    public interface ListItemListener {
        void onItemClicked(View v, int position);
    }
}
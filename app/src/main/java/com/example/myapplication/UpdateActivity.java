package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.MonAnDAO;
import com.example.myapplication.models.Loai;
import com.example.myapplication.models.MonAn;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    Button updateButton, removeButton, cancelButton, dateButton;
    EditText tenView, hocPhiView;
    TextView ngayBatDauView;
    RadioGroup chuyenNganhGroup;
    CheckBox kichHoatBox;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        updateButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);
        removeButton = findViewById(R.id.deleteButton);

        tenView = findViewById(R.id.update_ten);
        hocPhiView = findViewById(R.id.update_hocPhi);
        ngayBatDauView = findViewById(R.id.update_ngayBatDau);
        dateButton = findViewById(R.id.dateButton);
        chuyenNganhGroup = findViewById(R.id.update_chuyenNganh_group);
        kichHoatBox = findViewById(R.id.update_kichHoat);


        Intent intent = getIntent();
        MonAn monAn = (MonAn) intent.getSerializableExtra("item");

        Loai[] loais = Loai.values();
        for (int i = 0; i < loais.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(loais[i].getDescription());
            radioButton.setId(i);
            radioButton.setChecked(monAn.getChuyenNganh()
                    .getDescription()
                    .equalsIgnoreCase(loais[i].getDescription()));

            chuyenNganhGroup.addView(radioButton);
        }

        tenView.setText(monAn.getTen());
        hocPhiView.setText(monAn.getHocPhi());
        ngayBatDauView.setText(monAn.getNgayBatDau());
        kichHoatBox.setChecked(monAn.getKichHoat() == 1);

        MonAnDAO db = new MonAnDAO(this);

        // Date Button
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int cy = c.get(Calendar.YEAR);
                int cm = c.get(Calendar.MONTH);
                int cd = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        UpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(
                                    DatePicker datePicker,
                                    int y,
                                    int m,
                                    int d
                            ) {
                                ngayBatDauView.setText(
                                        d + "/" + m + "/" + y);
                            }
                        }, cy, cm, cd
                );
                dialog.show();
            }
        });

        //Cancel Button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Remove Button
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(monAn.getMa());
                finish();
            }
        });

        //Update Button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = tenView.getText().toString();
                String hocPhi = hocPhiView.getText().toString();
                String ngay = ngayBatDauView.getText().toString();
                boolean kichHoat = kichHoatBox.isChecked();
                String chuyenNganhTxt = ((RadioButton) chuyenNganhGroup.findViewById(chuyenNganhGroup.getCheckedRadioButtonId())).getText()
                        .toString();
                Loai loai = null;
                Loai[] chuyenNganhs1 = Loai.values();
                for (Loai cn : chuyenNganhs1) {
                    if (cn.getDescription().equalsIgnoreCase(chuyenNganhTxt)) {
                        loai = cn;
                    }
                }

                if (ten.isEmpty() || hocPhi.isEmpty() || ngay.isEmpty()) {
                    Toast.makeText(
                            UpdateActivity.this,
                            "Vui lòng điền đầy đủ thông tin",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    monAn.setTen(ten);
                    monAn.setChuyenNganh(loai);
                    monAn.setNgayBatDau(ngay);
                    monAn.setHocPhi(hocPhi);
                    monAn.setKichHoat(kichHoat ? 1 : 0);
                    db.update(monAn);

                    finish();
                }
            }
        });
    }
}

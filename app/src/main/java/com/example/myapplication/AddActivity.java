package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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

public class AddActivity extends AppCompatActivity {
    Button addButton, cancelButton, dateButton;
    EditText txtTen, hocPhiView;
    TextView txtNgay;
    RadioGroup chuyenNganhGroup;
    CheckBox congTacCheckBox;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        addButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);
        txtTen = findViewById(R.id.add_ten);
        hocPhiView = findViewById(R.id.add_hocPhi);
        txtNgay = findViewById(R.id.add_ngayBatDau);
        dateButton = findViewById(R.id.dateButton);
        chuyenNganhGroup = findViewById(R.id.add_chuyenNganh_group);
        congTacCheckBox = findViewById(R.id.add_kichHoat);

        Loai[] loais = Loai.values();
        for (int i = 0; i < loais.length; i++) {
            System.out.println(loais[i].name());
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(loais[i].getDescription());
            radioButton.setId(i);
            if (i == 0) {
                radioButton.setChecked(true);
            }

            chuyenNganhGroup.addView(radioButton);
        }

        MonAnDAO db = new MonAnDAO(this);

        //Date Button
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int cy = c.get(Calendar.YEAR);
                int cm = c.get(Calendar.MONTH);
                int cd = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(
                                    DatePicker datePicker,
                                    int y,
                                    int m,
                                    int d
                            ) {
                                txtNgay.setText(
                                        d + "/" + m + "/" + y);
                            }
                        }, cy, cm, cd
                );
                dialog.show();
            }
        });

        // Cancel Button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Add Button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtTen.getText().toString();
                String hocPhi = hocPhiView.getText().toString();
                String ngay = txtNgay.getText().toString();
                boolean congtac = congTacCheckBox.isChecked();
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
                            AddActivity.this,
                            "Vui lòng điền đầy đủ thông tin",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    MonAn monAn = new MonAn(ten, loai, ngay, hocPhi, congtac ? 1 : 0);
                    db.insert(monAn);
                    finish();
                }

            }
        });
    }
}
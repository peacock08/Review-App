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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.KhoaHocDAO;
import com.example.myapplication.models.KhoaHoc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText emailTxt, passwordTxt;
    ProgressBar progressBar;
    TextView signUpTxt;
    Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailTxt = findViewById(R.id.editTextTextEmailAddress);
        passwordTxt = findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.progressBar);
        signUpTxt = findViewById(R.id.textViewSignUp);
        loginButton = findViewById(R.id.loginButton);

        progressBar.setVisibility(View.GONE);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Signup
        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = String.valueOf(emailTxt.getText());
                String password = String.valueOf(passwordTxt.getText());
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Missing information", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Login fail",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

//    public static class AddActivity extends AppCompatActivity {
//        Button addButton, cancelButton, dateButton;
//        EditText txtTen, hocPhiView;
//        TextView txtNgay;
//        RadioGroup chuyenNganhGroup;
//        CheckBox congTacCheckBox;
//
//        @SuppressLint("MissingInflatedId")
//        @Override
//        protected void onCreate(@Nullable Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.add);
//
//            addButton = findViewById(R.id.updateButton);
//            cancelButton = findViewById(R.id.cancelButton);
//            txtTen = findViewById(R.id.add_ten);
//            hocPhiView = findViewById(R.id.add_hocPhi);
//            txtNgay = findViewById(R.id.add_ngayBatDau);
//            dateButton = findViewById(R.id.dateButton);
//            chuyenNganhGroup = findViewById(R.id.add_chuyenNganh_group);
//            congTacCheckBox = findViewById(R.id.add_kichHoat);
//
//            Loai[] loais = Loai.values();
//            for (int i = 0; i < loais.length; i++) {
//                System.out.println(loais[i].name());
//                RadioButton radioButton = new RadioButton(this);
//                radioButton.setText(loais[i].getDescription());
//                radioButton.setId(i);
//                if (i == 0) {
//                    radioButton.setChecked(true);
//                }
//
//                chuyenNganhGroup.addView(radioButton);
//            }
//
//            KhoaHocDAO db = new KhoaHocDAO(this);
//
//            //Date Button
//            dateButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Calendar c = Calendar.getInstance();
//                    int cy = c.get(Calendar.YEAR);
//                    int cm = c.get(Calendar.MONTH);
//                    int cd = c.get(Calendar.DAY_OF_MONTH);
//
//                    DatePickerDialog dialog = new DatePickerDialog(
//                            AddActivity.this,
//                            new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(
//                                        DatePicker datePicker,
//                                        int y,
//                                        int m,
//                                        int d
//                                ) {
//                                    txtNgay.setText(
//                                            d + "/" + m + "/" + y);
//                                }
//                            }, cy, cm, cd
//                    );
//                    dialog.show();
//                }
//            });
//
//            // Cancel Button
//            cancelButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//
//            //Add Button
//            addButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String ten = txtTen.getText().toString();
//                    String hocPhi = hocPhiView.getText().toString();
//                    String ngay = txtNgay.getText().toString();
//                    boolean congtac = congTacCheckBox.isChecked();
//                    String chuyenNganhTxt = ((RadioButton) chuyenNganhGroup.findViewById(chuyenNganhGroup.getCheckedRadioButtonId())).getText()
//                                                                                                                      .toString();
//                    Loai loai = null;
//                    Loai[] chuyenNganhs1 = Loai.values();
//                    for (Loai cn : chuyenNganhs1) {
//                        if (cn.getDescription().equalsIgnoreCase(chuyenNganhTxt)) {
//                            loai = cn;
//                        }
//                    }
//
//
//                    if (ten.isEmpty() || hocPhi.isEmpty() || ngay.isEmpty()) {
//                        Toast.makeText(
//                                AddActivity.this,
//                                "Vui lòng điền đầy đủ thông tin",
//                                Toast.LENGTH_SHORT
//                        ).show();
//                    } else {
//                        KhoaHoc khoaHoc = new KhoaHoc(ten, loai, ngay, hocPhi, congtac ? 1 : 0);
//                        db.insert(khoaHoc);
//                        finish();
//                    }
//
//                }
//            });
//        }
//    }
}

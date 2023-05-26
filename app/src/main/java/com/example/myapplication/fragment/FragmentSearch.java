package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.UpdateActivity;
import com.example.myapplication.adapter.KhoaHocAdapter;
import com.example.myapplication.database.MonAnDAO;
import com.example.myapplication.models.MonAn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment implements KhoaHocAdapter.ListItemListener {
    SearchView searchView;
    Spinner spinner;
    Button searchButton;
    ListView listView;
    List<MonAn> monAns = new ArrayList<>();
    MonAnDAO db;
    KhoaHocAdapter adapter;
    private ImageView avatarImageView;
    private Button changeAvatarButton;
    private boolean isDefaultAvatar = true;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchButton = v.findViewById(R.id.search_btnSubmit);
        spinner = v.findViewById(R.id.spinner);
        searchView = v.findViewById(R.id.search_searchView);
        listView = v.findViewById(R.id.search_listItem);

        db = new MonAnDAO(getContext());
        adapter = new KhoaHocAdapter(getContext(), monAns);
        adapter.setListItemListener(this);
        listView.setAdapter(adapter);

        // Ánh xạ view từ layout
        avatarImageView = v.findViewById(R.id.avatarImageView);
        changeAvatarButton = v.findViewById(R.id.changeAvatarButton);

        // Thiết lập sự kiện click cho nút Change Avatar
        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để thay đổi hình ảnh của avatar
                changeAvatar();
            }
        });
        // Khai báo biến final dialog
        final AlertDialog[] dialog = {null};
        Button buttonChangeInformation = v.findViewById(R.id.buttonChangeInformation);
        // Khai báo biến currentEmail để lưu trữ email hiện tại
        String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String currentName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String currentphone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        buttonChangeInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo dialog bằng cách sử dụng AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater = requireActivity().getLayoutInflater();

                // Inflate layout và thiết lập giao diện cho dialog
                View dialogView = inflater.inflate(R.layout.fragment_change_information, null);
                builder.setView(dialogView);

                // Khởi tạo các thành phần trong dialogView
                EditText editTextName = dialogView.findViewById(R.id.editTextName1);
                EditText editTextAge = dialogView.findViewById(R.id.editTextAge1);
                EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber1);
                EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail1);
                Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate1);
                Button buttonCancel = dialogView.findViewById(R.id.buttonCancel1);
                // Đặt giá trị email hiện tại vào trường email và vô hiệu hóa sửa đổi
                editTextEmail.setText(currentEmail);
                editTextName.setText(currentName);
                editTextAge.setText("");
                editTextPhoneNumber.setText(currentphone);
                // Tạo dialog từ builder và gán vào biến dialog
                dialog[0] = builder.create();

                // Xử lý sự kiện click vào nút "Sửa"
                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Lấy thông tin từ các trường nhập
                        String name = editTextName.getText().toString().trim();
                        String age = editTextAge.getText().toString().trim();
                        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                        String email = editTextEmail.getText().toString().trim();

                        // Cập nhật thông tin người dùng trên Firebase (sử dụng các phương thức tương ứng)
                        updateUserInfo(name, age, phoneNumber, email);


                        // Đóng dialog
                        dialog[0].dismiss();
                    }
                });

                // Xử lý sự kiện click vào nút "Hủy"
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Đóng dialog
                        dialog[0].dismiss();
                    }
                });

                // Hiển thị dialog
                dialog[0].show();
            }
        });

        // Search Button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = String.valueOf(searchView.getQuery());
                int index = (int) spinner.getSelectedItemId();

                monAns.clear();
                monAns.addAll(db.search(key, index));
                adapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    private void changeAvatar() {
        if (isDefaultAvatar) {
            // Đổi hình ảnh thành ảnh có địa chỉ drawable/girl
            avatarImageView.setImageResource(R.drawable.girl);
            isDefaultAvatar = false;
        } else {
            // Đổi hình ảnh về ảnh ban đầu (ví dụ: drawable/avatar)
            avatarImageView.setImageResource(R.drawable.nam);
            isDefaultAvatar = true;
        }
    }

    private void updateUserInfo(String name, String age, String phoneNumber, String email) {
        // Thực hiện cập nhật thông tin người dùng trên Firebase (sử dụng các phương thức tương ứng)
        // Ví dụ:
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }






    @Override
    public void onItemClicked(View v, int position) {
        Intent intent = new Intent(getContext(), UpdateActivity.class);
        MonAn monAn = monAns.get(position);
        intent.putExtra("item", monAn);
        startActivity(intent);
    }

    // Load lại fragment sau khi add
    @Override
    public void onResume() {
        super.onResume();
        monAns.clear();
        adapter.notifyDataSetChanged();
    }
}

package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.UpdateActivity;
import com.example.myapplication.adapter.KhoaHocAdapter;
import com.example.myapplication.database.MonAnDAO;
import com.example.myapplication.models.MonAn;

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

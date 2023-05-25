package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.UpdateActivity;
import com.example.myapplication.adapter.KhoaHocAdapter;
import com.example.myapplication.database.KhoaHocDAO;
import com.example.myapplication.models.KhoaHoc;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FragmentList extends Fragment implements KhoaHocAdapter.ListItemListener {
    ListView listView;
    KhoaHocDAO db;
    KhoaHocAdapter adapter;
    List<KhoaHoc> khoaHocs;
    ImageButton logOutButton;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        listView = v.findViewById(R.id.list_listItem);
        logOutButton = v.findViewById(R.id.logOutButton);
        db = new KhoaHocDAO(getContext());

        khoaHocs = db.getAll();
        adapter = new KhoaHocAdapter(getContext(), khoaHocs);
        adapter.setListItemListener(this);
        listView.setAdapter(adapter);

//        //Logout Button
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onItemClicked(View v, int position) {
        Intent intent = new Intent(getContext(), UpdateActivity.class);
        KhoaHoc khoaHoc = khoaHocs.get(position);
        intent.putExtra("item", khoaHoc);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        khoaHocs.clear();
        khoaHocs.addAll(db.getAll());
        adapter.notifyDataSetChanged();
    }

}

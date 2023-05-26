package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.UpdateActivity;
import com.example.myapplication.adapter.KhoaHocAdapter;
import com.example.myapplication.api.WeatherAPI;
import com.example.myapplication.database.MonAnDAO;
import com.example.myapplication.models.MonAn;
import com.example.myapplication.models.WeatherInfo;
import com.example.myapplication.models.WeatherResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentList extends Fragment implements KhoaHocAdapter.ListItemListener {
    ListView listView;
    MonAnDAO db;
    KhoaHocAdapter adapter;
    List<MonAn> monAns;
    ImageButton logOutButton;
    private static final String API_KEY = "YOUR_API_KEY";
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        listView = v.findViewById(R.id.list_listItem);
        logOutButton = v.findViewById(R.id.logOutButton);
        db = new MonAnDAO(getContext());

        monAns = db.getAll();
        adapter = new KhoaHocAdapter(getContext(), monAns);
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

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.accuweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo instance của WeatherAPI
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        TextView temperatureTextView = v.findViewById(R.id.temperatureTextView);
        // Gửi yêu cầu để lấy thông tin thời tiết cho thành phố mong muốn (ví dụ Hanoi)
        Call<WeatherResponse> call = weatherAPI.getWeather("Hà Nội", API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        WeatherInfo weatherInfo = weatherResponse.getWeatherInfo();
                        double temperature = weatherInfo.getTemperature();

                        // Cập nhật giao diện với thông tin thời tiết

                        temperatureTextView.setText(String.valueOf(temperature));
                    }
                }else {
                    // Xử lý khi không nhận được phản hồi thành công từ API thời tiết
                    temperatureTextView.setText("Thời tiết đang cập nhật");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Xử lý khi yêu cầu thất bại
            }
        });

        return v;
    }

    @Override
    public void onItemClicked(View v, int position) {
        Intent intent = new Intent(getContext(), UpdateActivity.class);
        MonAn monAn = monAns.get(position);
        intent.putExtra("item", monAn);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        monAns.clear();
        monAns.addAll(db.getAll());
        adapter.notifyDataSetChanged();
    }

}

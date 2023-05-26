package com.example.myapplication.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentInfo extends Fragment {

    private ListView listViewArticles;
    private ArrayAdapter<String> articleAdapter;

    private List<String> articleTitles;
    private List<String> articleUrls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        listViewArticles = view.findViewById(R.id.listViewArticles);
        articleTitles = getArticleTitles(); // Lấy danh sách tiêu đề bài báo
        articleUrls = getArticleUrls(); // Lấy danh sách địa chỉ bài báo

        articleAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, articleTitles);
        listViewArticles.setAdapter(articleAdapter);

        listViewArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleUrl = articleUrls.get(position);
                navigateToArticle(articleUrl);
            }
        });

        Button buttonMyLocation = view.findViewById(R.id.buttonMyLocation);
        buttonMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapWithMyLocation();
            }
        });


        return view;
    }

    private List<String> getArticleTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("Review gỏi đu đủ Ty Thy");
        titles.add("Review quán cơm 10 Khó");
        titles.add("Top món ăn ngon ở Tây Ninh");
        titles.add("Món ăn hà Nội vừa rẻ lại vừa bổ dưỡng");
        titles.add("Top 20 quán ăn ngon ở Thái Bình");
        titles.add("Review món bánh xèo thịt nướng - Hương vị thơm ngon đậm đà");
        titles.add("Review món phở gà - Một món ngon truyền thống của Việt Nam");
        titles.add("Những món ăn vỉa hè ngon và độc đáo");
        titles.add("Khám phá hương vị ẩm thực đặc biệt");
        titles.add(" Điểm đến lý tưởng cho tiệc cuối tuần");
        titles.add("Review gỏi đu đủ Ty Thy");
        titles.add("Review quán cơm 10 Khó");
        titles.add("Top món ăn ngon ở Tây Ninh");
        titles.add("Món ăn hà Nội vừa rẻ lại vừa bổ dưỡng");
        titles.add("Top 20 quán ăn ngon ở Thái Bình");
        titles.add("Review gỏi đu đủ Ty Thy");
        titles.add("Review quán cơm 10 Khó");
        titles.add("Top món ăn ngon ở Tây Ninh");
        titles.add("Món ăn hà Nội vừa rẻ lại vừa bổ dưỡng");
        titles.add("Top 20 quán ăn ngon ở Nam Định");
        return titles;
    }

    private List<String> getArticleUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("https://reviewphongtap.com/review-goi-du-du-ty-thy/");
        urls.add("https://tunaucom123.com.vn/review-quan-com-muoi-kho-co-ngon-khong-1148.html");
        urls.add("https://diachiamthuc.vn/mon-ngon-tay-ninh/");
        urls.add("https://www.luavietours.com/review-25-mon-an-ha-noi-vua-re-lai-vua-no-news709.html");
        urls.add("https://kenhhomestay.com/quan-an-ngon-thai-binh/");
        urls.add("https://reviewphongtap.com/review-goi-du-du-ty-thy/");
        urls.add("https://tunaucom123.com.vn/review-quan-com-muoi-kho-co-ngon-khong-1148.html");
        urls.add("https://diachiamthuc.vn/mon-ngon-tay-ninh/");
        urls.add("https://www.luavietours.com/review-25-mon-an-ha-noi-vua-re-lai-vua-no-news709.html");
        urls.add("https://kenhhomestay.com/quan-an-ngon-thai-binh/");
        urls.add("https://reviewphongtap.com/review-goi-du-du-ty-thy/");
        urls.add("https://tunaucom123.com.vn/review-quan-com-muoi-kho-co-ngon-khong-1148.html");
        urls.add("https://diachiamthuc.vn/mon-ngon-tay-ninh/");
        urls.add("https://www.luavietours.com/review-25-mon-an-ha-noi-vua-re-lai-vua-no-news709.html");
        urls.add("https://kenhhomestay.com/quan-an-ngon-thai-binh/");
        urls.add("https://reviewphongtap.com/review-goi-du-du-ty-thy/");
        urls.add("https://tunaucom123.com.vn/review-quan-com-muoi-kho-co-ngon-khong-1148.html");
        urls.add("https://diachiamthuc.vn/mon-ngon-tay-ninh/");
        urls.add("https://www.luavietours.com/review-25-mon-an-ha-noi-vua-re-lai-vua-no-news709.html");
        urls.add("https://kenhhomestay.com/quan-an-ngon-thai-binh/");
        return urls;
    }

    private void navigateToArticle(String articleUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
        startActivity(intent);
    }

    private void openMapWithMyLocation() {
        // Sử dụng Intent để mở ứng dụng Bản đồ
        Uri geoLocation = Uri.parse("geo:0,0?q=restaurant");
        Intent intent = new Intent(Intent.ACTION_VIEW, geoLocation);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

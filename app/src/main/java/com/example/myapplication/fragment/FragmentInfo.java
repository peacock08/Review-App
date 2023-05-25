package com.example.myapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        return view;
    }

    private List<String> getArticleTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("Bài báo 1");
        titles.add("Bài báo 2");
        titles.add("Bài báo 3");
        titles.add("Bài báo 4");
        titles.add("Bài báo 5");
        return titles;
    }

    private List<String> getArticleUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("https://www.example.com/article-1");
        urls.add("https://www.example.com/article-2");
        urls.add("https://www.example.com/article-3");
        urls.add("https://www.example.com/article-4");
        urls.add("https://www.example.com/article-5");
        return urls;
    }

    private void navigateToArticle(String articleUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
        startActivity(intent);
    }
}

package com.demo.healthconnect.Article;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.healthconnect.R;

public class Article extends AppCompatActivity {

    ListView listView;
    String[] listItem;
    String[] listItemUri = new String[6];

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        listView=(ListView)findViewById(R.id.listView_article);
        listItem = getResources().getStringArray(R.array.article_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        listItemUri[0] = "https://health.economictimes.indiatimes.com/news";
        listItemUri[1] = "https://www.webmd.com/news/articles";
        listItemUri[2] = "https://www.healthline.com/health-news";
        listItemUri[3] = "https://timesofindia.indiatimes.com/life-style/health-fitness/fitness";
        listItemUri[4] = "https://timesofindia.indiatimes.com/life-style/health-fitness/diet";
        listItemUri[5] = "https://timesofindia.indiatimes.com/life-style/health-fitness/de-stress";

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value = adapter.getItem(position);
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                startActivity(intent);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listItemUri[position] ));
                startActivity(browserIntent);
            }
        });

    }
}
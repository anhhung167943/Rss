package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> arrayTieude = new ArrayList<>();
    ArrayList<String> arrayLink = new ArrayList<>();

    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayTieude);
        lv.setAdapter(adapter);

        new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,NewActivity.class);
                intent.putExtra("LinkTinTuc", arrayLink.get(i));
                startActivity(intent);
            }
        });
    }

    private class  ReadRSS extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
             super.onPostExecute(s);

             XMLDOMParser parser = new XMLDOMParser();

            Document document = parser.getDocument(s);

            NodeList nodeList = document.getElementsByTagName("item");

            String TieuDe = "";
            String Link = "";

            for(int i = 0;i < nodeList.getLength();i++){
                Element element = (Element) nodeList.item(i);
                TieuDe = parser.getValue(element,"title");
                arrayTieude.add(TieuDe);
                Link = parser.getValue(element,"link");
                arrayLink.add(Link);
            }

            adapter.notifyDataSetChanged();
            //Toast.makeText(MainActivity.this, TieuDe , Toast.LENGTH_LONG).show();
        }
    }
}

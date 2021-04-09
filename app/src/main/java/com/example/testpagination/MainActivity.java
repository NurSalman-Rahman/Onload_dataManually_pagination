package com.example.testpagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.testpagination.Adapter.MyAdapter;
import com.example.testpagination.Interface.ILoadMore;
import com.example.testpagination.Model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    /*ArrayList<Medicine> mediList;*/

    List<Item> items = new ArrayList<>();
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // random10Data();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(recyclerView, this, items);
        recyclerView.setAdapter(adapter);
        random10Data();


        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if (items.size() <= 100) {
                    items.add(null);
                    adapter.notifyItemInserted(items.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            items.remove(items.size() - 1);
                            adapter.notifyItemRemoved(items.size());

                            // Random data
                            int index = items.size();
                            int end = index + 20;


                            for (int i = index; i < end; i++) {

                                String name = UUID.randomUUID().toString();
                                /*    Item item = new Item(name,name.length());*/
                                Item item = new Item(name, name.length());
                                items.add(item);


                            }


                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();

                        }
                    }, 2000);

                } else {
                    Toast.makeText(MainActivity.this, "load data complete ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //Ramdom data
    private void random10Data() {


        for (int i = 0; i < 20; i++) {
            String name = UUID.randomUUID().toString();
            Item item = new Item(name, name.length());
            /* Item item = new Item(name,name.length());*/
            items.add(item);


        }


    }
}
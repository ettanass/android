package com.example.hpprobook.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CardListActivity extends Activity {

    private static final String TAG = "CardListActivity";
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        listView = (ListView) findViewById(R.id.card_listView);


        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

        for (int i = 0; i < 10; i++) {

            Card card = new Card("tt", "tt","12","http://images.marmitoncdn.org/recipephotos/multiphoto/81/811a7c8d-c356-4d5e-83dd-6eea299738e4_normal.jpg","azerty");
            cardArrayAdapter.add(card);
        }

        listView.setAdapter(cardArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: handle row clicks here

                Intent intent = new Intent(CardListActivity.this, DetailsActivity   .class);
                startActivity(intent);
            }

        });
    }
}
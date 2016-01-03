package com.example.hpprobook.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button searchbtn = (Button)findViewById(R.id.btnsearch);

        searchbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }

        });

        Button aboutbtn = (Button)findViewById(R.id.btnabout);

        aboutbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, About.class);
                startActivity(intent);
            }

        });

        Button recipebtn = (Button)findViewById(R.id.btnrecipe);

        recipebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CardListActivity.class);
                startActivity(intent);
            }

        });

    }
}

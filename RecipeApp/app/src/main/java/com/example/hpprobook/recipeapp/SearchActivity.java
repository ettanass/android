package com.example.hpprobook.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
    TextView editTextSearch;
    Button btn_serach_elements;
    String nameIngredient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btn_serach_elements=(Button)findViewById(R.id.btnsearchelements);
        editTextSearch = (TextView)findViewById(R.id.editTextSearch);

        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)) {
                    // TODO Auto-generated method stub

                    nameIngredient = editTextSearch.getText().toString();

                    System.out.println("nameIngredient:" + nameIngredient);

                    //Checking whether the EditText is empty or not

                    if ((nameIngredient.equals("")) == false) {

                        //Creating a new intent

                        Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);

                        //Sending data to next activity using putExtra method

                        intent.putExtra("INGREDIENTS", nameIngredient);

                        //starting new activity

                        startActivity(intent);

                    } else {
                        Toast.makeText(SearchActivity.this, "Vous devez entrer au moins un ingrédient", Toast.LENGTH_LONG).show();

                    }
                    return true;
                }
                return false;
            }
        });





    }

}


package com.example.hpprobook.recipeapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class RecipeDetailsActivity extends AppCompatActivity {
String urlRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Bundle bunble=getIntent().getExtras();
        if(bunble!=null){
            //Getting the value stored in the nameIngredient "NAME"
            urlRecipe=bunble.getString("url");
            //appending the value to the contents of textView1.
            //String[] arr=ingredients.split(" ");
            //receivedText.append(Arrays.toString(arr));

        }


        new Description().execute();
    }

    private class Description extends AsyncTask<Void, Void ,Void> {
        String title;
        String ingredient;
        String recette;
        String temps_prep;
        String image;
        String url;
        String todo;

        protected void onPreExecute(){

            super.onPreExecute();
            //  progressDialog = new ProgressDialog(DetailsActivity.this);
            //  progressDialog.setMessage("Chargement des données...");
            //  progressDialog.setIndeterminate(false);
            //  progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Document document = Jsoup.connect(urlRecipe).userAgent("USER_AGENT_HERE").get();
                Elements elements = document.select("div.m_content_recette_cadre");
                 title = document.select("span.fn").text();

                ingredient = elements.select("div.m_content_recette_ingredients").text();
                ingredient = ingredient.replace("-", " \n -");
                recette = elements.select("div.m_content_recette_todo").text();
                recette = recette.replace("Préparation de la recette :", "Préparation de la recette : \n ");


                //  temps_prep = element.select("span.preptime").text();
//                    url = element.select("div.m_titre_resultat > a").first().attr("abs:href");
                  //  todo = element.select("div.m_titre_resultat").text();

                    //extraire l'image
                 //   Document imageDoc = Jsoup.connect("").userAgent("USER_AGENT_HERE").get();
                    image = elements.select("img.m_pinitimage").attr("abs:src");
                    temps_prep = elements.select("p.m_content_recette_info").text();
                temps_prep = temps_prep.replace("minutes", "min \n ");
                    //String t = element.select("a img").get(0).attr("src");

                    //Elements links = element.select("a");
                    //Element link = links.first();
                    // 'abs:' makes "/greatsushi" = "http://example.com/greatsushi":
                    //String url = link.attr("id");
                    Log.d("test", "t" + title);


                    //String url = image.attr("src");

                    //Log.d("test", "t" + t);//element.ownText()


            }catch (IOException ex){
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          TextView timerecipe = (TextView) findViewById(R.id.preparationtime);
          timerecipe.setText(temps_prep);
            TextView titre = (TextView) findViewById(R.id.recipetitle);
            titre.setText(title);
            TextView ingredients = (TextView) findViewById(R.id.ingredients);
            ingredients.setText(ingredient);
            TextView recipe = (TextView) findViewById(R.id.recipe);
            recipe.setText(recette);
          // TextView titre = (TextView) findViewById(R.id.title);
         //   titre.setText(title);
//          //  TextView recipe = (TextView) findViewById(R.id.todo);
          //  recipe.setText(todo);

           ImageView logoImg = (ImageView) findViewById(R.id.imagerecipe);
           new ImageDownloader(logoImg).execute(image);

            //progressDialog.dismiss();
            //titre.setVisibility(View.VISIBLE);
            //listView.setVisibility(View.VISIBLE);
        }
    }


    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageDownloader(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

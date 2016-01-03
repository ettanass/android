package com.example.hpprobook.recipeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class DetailsActivity extends Activity {
    private static final String TAG = "CardListActivity";
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    TextView receivedText;
    String ingredients;
    String url ;
    ImageView logoImg;
    ProgressDialog progressDialog ;
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        receivedText = (TextView)findViewById(R.id.receivedText);
        Bundle bunble=getIntent().getExtras();
        if(bunble!=null){
            //Getting the value stored in the nameIngredient "NAME"
            ingredients=bunble.getString("INGREDIENTS");
            //appending the value to the contents of textView1.
            //String[] arr=ingredients.split(" ");
            //receivedText.append(Arrays.toString(arr));
            receivedText.append(ingredients);
        }

        listView = (ListView) findViewById(R.id.card_listView);


        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

        for (int i = 0; i < 10; i++) {
            card = new Card("tt", "tt","12","http://images.marmitoncdn.org/recipephotos/multiphoto/81/811a7c8d-c356-4d5e-83dd-6eea299738e4_normal.jpg","http://www.marmiton.org/recettes/recette_tarte-a-la-tomate-rapide_11490.aspx");
           Log.d("test",card.getUrl());
            cardArrayAdapter.add(card);
        }


        listView.setAdapter(cardArrayAdapter);
             listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     // TODO: handle row clicks here

                     Intent intent = new Intent(DetailsActivity.this, RecipeDetailsActivity.class);
                     intent.putExtra("url", card.getUrl());
                     startActivity(intent);
                 }

             });

        new Description().execute();
        //Description();
        //new ImageRecipe().execute();




    }

    private class Description extends AsyncTask<Void, Void ,Void> {
        String title;
        String temps_prep;
        String image;
        String url;

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
                Document document = Jsoup.connect("http://www.marmiton.org/recettes/recherche.aspx?aqt="+ingredients).userAgent("USER_AGENT_HERE").get();
                    Elements elements = document.select("div.recette_classique");

                for (Element element : elements) {
                    title = element.select("div.m_titre_resultat").text();
                    temps_prep = element.select("div.m_detail_time > div").get(0).text();
                    url = element.select("div.m_titre_resultat > a").first().attr("abs:href");

                    //extraire l'image
                    Document imageDoc = Jsoup.connect(url).userAgent("USER_AGENT_HERE").get();
                    image = imageDoc.select("img.m_pinitimage").attr("abs:src");
                    //temps_prep = element.select("div.m_detail_time > div").get(0).text();

                    //String t = element.select("a img").get(0).attr("src");

                    //Elements links = element.select("a");
                    //Element link = links.first();
                    // 'abs:' makes "/greatsushi" = "http://example.com/greatsushi":
                    //String url = link.attr("id");
                    Log.d("test","t"+ title);
                    break;

                    //String url = image.attr("src");

                    //Log.d("test", "t" + t);//element.ownText()

                }
            }catch (IOException ex){
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           TextView timerecipe = (TextView) findViewById(R.id.timerecipe);
            timerecipe.setText(temps_prep);

            TextView titre = (TextView) findViewById(R.id.title);
            titre.setText(title);

            ImageView logoImg = (ImageView) findViewById(R.id.imageView1);
            new ImageDownloader(logoImg).execute(image);

            //progressDialog.dismiss();
            //titre.setVisibility(View.VISIBLE);
            //listView.setVisibility(View.VISIBLE);
        }
    }

        //progressDialog.dismiss();
        //titre.setVisibility(View.VISIBLE);
        //listView.setVisibility(View.VISIBLE);




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

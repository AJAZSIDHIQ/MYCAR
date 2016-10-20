package com.example.asus.mycar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 19-10-2016.
 */

public class sort extends AppCompatActivity{
    Button popularity,pricelth,pricehtl,recently;
    List<String> namelist = new ArrayList<String>();
    List<String> companylist = new ArrayList<String>();
    List<String> pricelist = new ArrayList<String>();
    List<String> idlist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort);


        addListenerOnpopularity();
        addListenerOnpricelth();
        addListenerOnpricehtl();
        addListenerOnrecently();




    }


    public void addListenerOnpopularity() {

        final Context context = this;

        popularity = (Button) findViewById(R.id.popularity);

        popularity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String value="MILEAGE%20DESC";
                Intent intent = new Intent(context,listview.class);
                getData(value);
                String[] name = new String[namelist.size()];
                name = namelist.toArray(name);
                String[] company = new String[companylist.size()];
                company = companylist.toArray(company);
                String[] price = new String[pricelist.size()];
                price = pricelist.toArray(price);
                String[] id = new String[idlist.size()];
                id = idlist.toArray(id);


                Bundle bundle = new Bundle();
                bundle.putStringArray("id",id);
                bundle.putStringArray("name",name);
                bundle.putStringArray("company",company);
                bundle.putStringArray("price",price);
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });

    }

    public void addListenerOnpricelth() {

        final Context context = this;

        pricelth = (Button) findViewById(R.id.pricelth);

        pricelth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String value="PRICE";
                Intent intent = new Intent(context,listview.class);
                getData(value);
                String[] name = new String[namelist.size()];
                name = namelist.toArray(name);
                String[] company = new String[companylist.size()];
                company = companylist.toArray(company);
                String[] price = new String[pricelist.size()];
                price = pricelist.toArray(price);
                String[] id = new String[idlist.size()];
                id = idlist.toArray(id);


                Bundle bundle = new Bundle();
                bundle.putStringArray("id",id);
                bundle.putStringArray("name",name);
                bundle.putStringArray("company",company);
                bundle.putStringArray("price",price);
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });

    }

    public void addListenerOnpricehtl() {

        final Context context = this;

        pricehtl = (Button) findViewById(R.id.pricehtl);

        pricehtl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String value="PRICE%20DESC";
                Intent intent = new Intent(context,listview.class);
                getData(value);
                String[] name = new String[namelist.size()];
                name = namelist.toArray(name);
                String[] company = new String[companylist.size()];
                company = companylist.toArray(company);
                String[] price = new String[pricelist.size()];
                price = pricelist.toArray(price);
                String[] id = new String[idlist.size()];
                id = idlist.toArray(id);


                Bundle bundle = new Bundle();
                bundle.putStringArray("id",id);
                bundle.putStringArray("name",name);
                bundle.putStringArray("company",company);
                bundle.putStringArray("price",price);
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });

    }

    public void addListenerOnrecently() {

        final Context context = this;

        recently = (Button) findViewById(R.id.button8);

        recently.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String value="ENGINE%20DESC";
                Intent intent = new Intent(context,listview.class);
                getData(value);
                String[] name = new String[namelist.size()];
                name = namelist.toArray(name);
                String[] company = new String[companylist.size()];
                company = companylist.toArray(company);
                String[] price = new String[pricelist.size()];
                price = pricelist.toArray(price);
                String[] id = new String[idlist.size()];
                id = idlist.toArray(id);


                Bundle bundle = new Bundle();
                bundle.putStringArray("id",id);
                bundle.putStringArray("name",name);
                bundle.putStringArray("company",company);
                bundle.putStringArray("price",price);
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });

    }

    public void getData(String value){
        idlist = new ArrayList<String>();
        namelist = new ArrayList<String>();
        companylist = new ArrayList<String>();
        pricelist = new ArrayList<String>();
        String result = "";
        InputStream isr = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://mycar.orgfree.com/sortcar.php?value="+value); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());

        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();

            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }

        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);

            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);

                namelist.add(json.getString("NAME"));
                companylist.add(json.getString("COMPANY"));
                pricelist.add(json.getString("PRICE"));
                idlist.add(json.getString("_id"));

            }



        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

    }

}

package com.example.asus.mycar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

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

import static android.R.attr.id;
import static com.example.asus.mycar.R.id.button3;

/**
 * Created by ASUS on 15-10-2016.
 */

public class filter extends AppCompatActivity{

    Button button3;
    List<String> namelist = new ArrayList<String>();
    List<String> companylist = new ArrayList<String>();
    List<String> pricelist = new ArrayList<String>();
    List<String> idlist = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        addListenerOnButton();


    }

    public void addListenerOnButton() {



        final Context context = this;

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(context, listview.class);

                Spinner mySpinner = (Spinner) findViewById(R.id.Spinner);
                String types = mySpinner.getSelectedItem().toString();

                if (types.equals("Any Type")) {
                    types = "";
                }


                Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner1);
                String companys = mySpinner1.getSelectedItem().toString();

                if (companys.equals("Any Company")) {
                    companys = "";
                }

                Spinner mySpinner2 = (Spinner) findViewById(R.id.spinner2);
                String prices = mySpinner2.getSelectedItem().toString();

                if (prices.equals("Below 5 Lakhs")) {
                    prices = "between%200%20and%20500000";
                } else if (prices.equals("5 Lakhs - 10 Lakhs")) {
                    prices = "between%20500000%20and%201000000";
                } else if (prices.equals("10 Lakhs - 15 Lakhs")) {
                    prices = "between%201000000%20and%201500000";
                } else if (prices.equals("15 Lakhs - 20 Lakhs")) {
                    prices = "between%201500000%20and%202000000";
                } else if (prices.equals("Above 20 Lakhs")) {
                    prices = "between%202000000%20and%2050000000";
                } else if (prices.equals("Any Price")) {
                    prices = "between%200%20and%2050000000";
                }
                Spinner mySpinner3 = (Spinner) findViewById(R.id.spinner3);
                String transmissions = mySpinner3.getSelectedItem().toString();

                if (transmissions.equals("Any Transmission")) {
                    transmissions = "";
                }

                Spinner mySpinner4 = (Spinner) findViewById(R.id.spinner4);
                String fuels = mySpinner4.getSelectedItem().toString();

                if (fuels.equals("Any Fuel")) {
                    fuels = "";
                }

                getData(types,companys,prices,transmissions,fuels);

                String[] name = new String[namelist.size()];
                name = namelist.toArray(name);
                String[] company = new String[companylist.size()];
                company = companylist.toArray(company);
                String[] price = new String[pricelist.size()];
                price = pricelist.toArray(price);
                String[] id=new String[idlist.size()];
                id = idlist.toArray(id);


                Bundle bundle = new Bundle();

                bundle.putStringArray("name",name);
                bundle.putStringArray("company",company);
                bundle.putStringArray("price",price);
                bundle.putStringArray("id",id);
                // bundle.putStringArray("torque",torque);
                // bundle.putStringArray("bhp",bhp);
                //bundle.putIntegerArrayList("imageidl", (ArrayList<Integer>) imageidl);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    public void getData(String types,String companys,String prices,String transmissions,String fuels){
        String result = "";
        InputStream isr = null;
        namelist = new ArrayList<String>();
        companylist = new ArrayList<String>();
        pricelist = new ArrayList<String>();
        idlist = new ArrayList<String>();
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://mycar.orgfree.com/filtercars.php?types="+types+"&companys="+companys+"&prices="+prices+"&transmissions="+transmissions+"&fuels="+fuels); //YOUR PHP SCRIPT ADDRESS
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

                idlist.add(json.getString("_id"));
                namelist.add(json.getString("NAME"));
                companylist.add(json.getString("COMPANY"));
                pricelist.add(json.getString("PRICE"));


            }



        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

    }


}

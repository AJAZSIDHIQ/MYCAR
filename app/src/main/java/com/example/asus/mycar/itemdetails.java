package com.example.asus.mycar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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


public class itemdetails extends AppCompatActivity {

    String id,name,company,type,fuel,transmission,bhp,torque,price,mileage,engine;



    Button wishbutton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_itemdetails);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        //String resource = bundle.getString("resource");
        //int resourceInt = Integer.parseInt(resource);

        final Context context = this;
        //final List<Integer> imageidl= databaseAccess.getQuoteimageid(name, context);


        getData(name);

        // Integer[] imageid = new Integer[imageidl.size()];
        // imageid = imageidl.toArray(imageid);

        TextView nameContent = (TextView)findViewById(R.id.nameDetails);
        TextView companys = (TextView)findViewById(R.id.companyDetails);
        ImageView iv = (ImageView)findViewById(R.id.imageView);
        TextView prices = (TextView)findViewById(R.id.priceDetails);
        TextView types = (TextView)findViewById(R.id.typeInfo);
        TextView fuels = (TextView)findViewById(R.id.fuelDetails);
        TextView mileages = (TextView)findViewById(R.id.mileageDetails);
        TextView trans = (TextView)findViewById(R.id.transmissionDetails);
        TextView engines = (TextView)findViewById(R.id.engineDetails);
        TextView bhps = (TextView)findViewById(R.id.bhpDetails);
        TextView torques = (TextView)findViewById(R.id.torqueDetails);



        nameContent.setText(name);
        companys.setText(company);
        prices.setText("Rs."+" "+price);
        //iv.setImageResource(resourceInt);
        types.setText(type);
        fuels.setText(fuel);
        mileages.setText(mileage+" "+"Kmpl");
        trans.setText(transmission);
        engines.setText(engine+"cc");
        bhps.setText(bhp);
        torques.setText(torque);

        Picasso.with(this)
               .load("http://mycar.orgfree.com/carimages/car"+id+".jpeg")
                .error( R.drawable.progress_image )
                .placeholder( R.drawable.progress_animation )
               .into(iv);

        addListenerOnwishButton();
    }

    public void getData(String value){
        String result = "";
        InputStream isr = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://mycar.orgfree.com/searchcar.php?value="+value); //YOUR PHP SCRIPT ADDRESS
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

                id=json.getString("_id");
                name=json.getString("NAME");
                company=json.getString("COMPANY");
                type=json.getString("CATEGORY");
                fuel=json.getString("FUEL");
                mileage=json.getString("MILEAGE");
                price=json.getString("PRICE");
                bhp=json.getString("BHP");
                torque=json.getString("TORQUE");
                engine=json.getString("ENGINE");
                transmission=json.getString("TRANSMISSION");


            }



        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

    }

    public  void  addListenerOnwishButton(){


        final Context context = this;


        wishbutton = (Button) findViewById(R.id.wishbutton);
        wishbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                MainActivity ma=new MainActivity();
                ma.wishidlist.add(id);
                ma.wishnamelist.add(name);
                ma.wishcompanylist.add(company);
                ma.wishpricelist.add(price);

                Toast.makeText(context,"added to wishlist",Toast.LENGTH_LONG).show();




            }
        });
    }

}

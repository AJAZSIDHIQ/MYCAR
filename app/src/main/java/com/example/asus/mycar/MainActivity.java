package com.example.asus.mycar;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.view.View;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.example.asus.mycar.R.id.company;
import static com.example.asus.mycar.R.id.name;
import static com.example.asus.mycar.R.id.price;
import static com.example.asus.mycar.R.layout.filter;

public class MainActivity extends Activity {
    ViewPager viewPager;
    Customswipeadapter adapter;
    GridView gridView;

    Button button01;
    Button wishlist;
    Button filterbutton;
    Button sortbutton;
    List<String> idlist = new ArrayList<String>();
    List<String> namelist = new ArrayList<String>();
    List<String> companylist = new ArrayList<String>();
    List<String> pricelist = new ArrayList<String>();
    static  List<String> wishidlist = new ArrayList<String>();
    static List<String> wishnamelist = new ArrayList<String>();
    static List<String> wishcompanylist = new ArrayList<String>();
    static List<String> wishpricelist = new ArrayList<String>();
    List<String> torquelist = new ArrayList<String>();
    List<String> bhplist = new ArrayList<String>();
    public static Integer[] imageid = {105, 106, 107, 108, 109, 110, 111};






    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new Customswipeadapter(this);
        viewPager.setAdapter(adapter);





        List<item> rowItems;
        String[] id = {"103","108","102","119"};
        String[] company={"Hyundai","Jaguar","Honda","HONDA"};
         String[] name = {"Creta","XF","City","BRV"};
        String[] price={"999978","4767000","938700","990000"};


        rowItems = new ArrayList<item>();
        for (int i = 0; i < company.length; i++) {
            item item = new item(id[i] , company[i], name[i], price[i]);
            rowItems.add(item);
        }




        gridView = (GridView) findViewById(R.id.gridView);
        GridViewAdapter gridAdapter = new GridViewAdapter(this,R.layout.gridlayout ,rowItems);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                TextView tv= (TextView) v.findViewById(R.id.text);
                String name1 = tv.getText().toString();

                //Create intent
                Intent intent = new Intent(MainActivity.this,itemdetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",name1);
                intent.putExtras(bundle);

                //Start details activity
                startActivity(intent);
            }
        });
        isOnline();

        StrictMode.enableDefaults(); //STRICT MODE ENABLED

        addListenerOnfilterbutton();
        addListenerOnButton();
        addListenerwhishlist();
        addListenerOnsortbutton();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    public void getData(String value) {
        idlist = new ArrayList<String>();
        namelist = new ArrayList<String>();
        companylist = new ArrayList<String>();
        pricelist = new ArrayList<String>();
        torquelist = new ArrayList<String>();
        bhplist = new ArrayList<String>();
        String result = "";
        InputStream isr = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://mycar.orgfree.com/searchcar.php?value=" + value); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());

        }
        //convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();

            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error  converting result " + e.toString());
        }

        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);

                namelist.add(json.getString("NAME"));
                companylist.add(json.getString("COMPANY"));
                pricelist.add(json.getString("PRICE"));
                idlist.add(json.getString("_id"));

            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data " + e.toString());
        }

    }


    public void addListenerOnButton() {

        isOnline();

        final Context context = this;


        button01 = (Button) findViewById(R.id.searchbutton);

        button01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(context, listview.class);
                EditText textView = (EditText) findViewById(R.id.editText);
                String value0 = textView.getText().toString();
                String value = (value0);
                if (value0 ==null){
                    Toast.makeText(context,"Type any name to Search",Toast.LENGTH_LONG).show();
                }
                else {
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
                    bundle.putStringArray("id", id);
                    bundle.putStringArray("name", name);
                    bundle.putStringArray("company", company);
                    bundle.putStringArray("price", price);
                    // bundle.putStringArray("torque",torque);
                    // bundle.putStringArray("bhp",bhp);
                    //bundle.putIntegerArrayList("imageidl", (ArrayList<Integer>) imageidl);
                    i.putExtras(bundle);
                    startActivity(i);
                }

            }

        });

    }

    public void addListenerwhishlist() {
        wishlist = (Button) findViewById(R.id.wishlist);

        final Context context = this;

        wishlist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(context, listview.class);
                String[] name = new String[wishnamelist.size()];
                name = wishnamelist.toArray(name);
                String[] company = new String[wishcompanylist.size()];
                company = wishcompanylist.toArray(company);
                String[] price = new String[wishpricelist.size()];
                price = wishpricelist.toArray(price);
                String[] id = new String[wishidlist.size()];
                id = wishidlist.toArray(id);


                Bundle bundle = new Bundle();
                bundle.putStringArray("id", id);
                bundle.putStringArray("name", name);
                bundle.putStringArray("company", company);
                bundle.putStringArray("price", price);
                i.putExtras(bundle);
                startActivity(i);


            }
        });
    }

    public void addListenerOnfilterbutton() {

        isOnline();

        final Context context = this;

        filterbutton = (Button) findViewById(R.id.filterbutton);

        filterbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, filter.class);
                startActivity(intent);

            }

        });

    }

    public void addListenerOnsortbutton() {

        isOnline();

        final Context context = this;

        sortbutton = (Button) findViewById(R.id.sortbutton);

        sortbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, sort.class);
                startActivity(intent);

            }

        });

    }

    public boolean isOnline() {
        final Context context=this;
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }





}

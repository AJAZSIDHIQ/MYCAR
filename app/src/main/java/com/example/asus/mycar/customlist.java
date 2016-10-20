package com.example.asus.mycar;

/**
 * Created by ASUS on 11-10-2016.
 */

import java.util.List;
import com.example.asus.mycar.R;
import com.example.asus.mycar.item;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.attr.id;
import static java.lang.Integer.parseInt;

public class customlist extends ArrayAdapter<item> {

    Context context;

    public customlist(Context context, int resourceId,
                      List<item> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageid;
        TextView company;
        TextView name;
        TextView price;
        String id;

       TextView temp;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        item rowItem = getItem(position);


        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.eachitem, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.company = (TextView) convertView.findViewById(R.id.company);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.imageid = (ImageView) convertView.findViewById(R.id.imageid);
            holder.temp = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Picasso.with(context)
                .load("http://mycar.orgfree.com/carimages/car"+rowItem.getid()+".jpeg")
                .error( R.drawable.progress_image )
                .placeholder( R.drawable.progress_animation )
                .into(holder.imageid);

        holder.name.setText(rowItem.getname());
        holder.company.setText(rowItem.getcompany());
        holder.price.setText("price : "+rowItem.getprice());
         //holder.imageid.setImageResource(parseInt(rowItem.getid()));
       // holder.imageid.setTag(String.valueOf(rowItem.getimageid()));
       // String resource = (String) holder.imageid.getTag();

        return convertView;
    }
}

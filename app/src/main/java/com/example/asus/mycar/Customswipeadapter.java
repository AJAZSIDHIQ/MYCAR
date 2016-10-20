package com.example.asus.mycar;

/**
 * Created by ASUS on 11-10-2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import static com.example.asus.mycar.R.id.imageView;

public class Customswipeadapter extends PagerAdapter {


    private int[] imageid = {121, 119, 120, 126, 115, 112, 136};
    private Context ctx;
    private LayoutInflater layoutinflator;
    ImageView image;


    public Customswipeadapter(Context ctx) {
        this.ctx = ctx;


    }

    @Override
    public int getCount() {
        return imageid.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (LinearLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutinflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_viewn = layoutinflator.inflate(R.layout.slideview, container, false);
        ImageView imageview = (ImageView) item_viewn.findViewById(R.id.image_view);
        Picasso.with(ctx)
                .load("http://mycar.orgfree.com/carimages/car" + imageid[position] + ".jpeg")
                .error(R.drawable.progress_image)
                .placeholder(R.drawable.progress_animation)
                .into(imageview);
        container.addView(item_viewn);
        return item_viewn;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }




}
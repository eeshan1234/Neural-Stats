package com.example.sarthakmishra.neuralstats;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {R.drawable.predictator, R.drawable.reports ,R.drawable.doctorsnew};

    public String[] slide_heading = {"PREDICTOR",  "GENERATING REPORT", "DOCTORS",};

    public String[] slide_description = {
            "Hello and welcome to Neural Stats Like you, we know that mental health conditions are real, common\n" +
                    "and treatable. And recovery is possible. The tests in this app are one of the quickest\n" +
                    "ways to determine whether you are experiencing symptoms of specific mental health condition. Each test " +
                    "consists of series of questions which should be answered honestly and in one sitting ",

            "A report will be generated based on the answer you have given and the respective problem's\n" +
                    "Intensity will be displayed. One can also see and monitor their program and development in the report page by comparing the current results\n" +
                    "with that of the previous ones.",

            "If the problem you are current facing is unable to control and you might want to seek out for help so we have including the doctor's tab.This enables the user to browse all the nearby hospitals and the hospital's info through Google places.\n" +
                    "The user can easily decide and book an appointment of necessary."
               };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slidelayout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slideimages);
        TextView sliderHeading = (TextView) view.findViewById(R.id.heading);
        TextView sliderDesc = (TextView) view.findViewById(R.id.description);

        slideImageView.setImageResource(slide_images[position]);
        sliderHeading.setText(slide_heading[position]);
        sliderDesc.setText(slide_description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
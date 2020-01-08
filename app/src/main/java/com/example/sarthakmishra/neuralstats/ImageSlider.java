package com.example.sarthakmishra.neuralstats;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageSlider extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mdots;
    private SliderAdapter sliderAdapter;

    private Button mNextBtn;
    private Button mbackbtn;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        mSlideViewPager=(ViewPager)findViewById(R.id.viewpager);
        mDotLayout=(LinearLayout)findViewById(R.id.dotlayout);
        mNextBtn=(Button)findViewById(R.id.next);
        mbackbtn=(Button)findViewById(R.id.prev);
        sliderAdapter=new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        adddotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage+1);
            }
        });
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
    }

    public void adddotsIndicator(int position){
        mdots=new TextView[3];
        mDotLayout.removeAllViews();

        for(int i=0;i<mdots.length;i++) {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.LightBlack));

            mDotLayout.addView(mdots[i]);
        }

        if(mdots.length>0)
            mdots[position].setTextColor(getResources().getColor(R.color.White));

    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            adddotsIndicator(position);
            mCurrentPage=position;

            if(position==0){
                mNextBtn.setEnabled(true);
                mbackbtn.setEnabled(false);
                mbackbtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("Next");
                mbackbtn.setText("");
            }
            else if(position==mdots.length-1){
                mNextBtn.setEnabled(true);
                mbackbtn.setEnabled(true);

                mbackbtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Finish");
                mNextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(ImageSlider.this,Homepage.class);
                        startActivity(i);
                    }
                });
                mbackbtn.setText("Back");
            }

            else{
                mNextBtn.setEnabled(true);
                mbackbtn.setEnabled(true);

                mbackbtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Next");
                mbackbtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}

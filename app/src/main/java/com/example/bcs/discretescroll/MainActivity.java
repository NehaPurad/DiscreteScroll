package com.example.bcs.discretescroll;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class MainActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener,View.OnClickListener {

    private List<Item> data;
    private Shop shop;
    private ImageView[] dots;
    public int dotsCount;
    private ImageButton imgbtn1,imgbtn2;

    private TextView currentItemName;
    private TextView currentItemPrice;
    private ImageView rateItemButton;
    LinearLayout  dotsLayout;
    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter infiniteAdapter;
    private CirclePageIndicator circlePageIndicator;
    private int currentPage = 0;
    private int NUM_PAGE = 0;
    RecyclerView recyclerView;

    private Integer[] IMAGES = {R.drawable.employee1, R.drawable.employee1, R.drawable.employee1};
    private ArrayList<Integer> arrayList;
    RecyclerViewAdapter adapter;
    int i;
    int k;

    
    private static final String TAG = "MainActivity";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgbtn1=(ImageButton)findViewById(R.id.backButton);
        imgbtn2=(ImageButton)findViewById(R.id.frontButton);
        imgbtn1.setVisibility(View.INVISIBLE);

        shop = Shop.get();
        data = shop.getData();

        getImages();
        arrayList=new ArrayList<>();
        arrayList=populateList();
        init();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.backButton:
                LinearLayoutManager llb = (LinearLayoutManager) recyclerView.getLayoutManager();
                llb.scrollToPosition(llb.findFirstVisibleItemPosition() -1);

                int k=mImageUrls.size()-1;
                int l=llb.findLastVisibleItemPosition()-1;
                Log.e("backPosition", String.valueOf(l));
                if (i==0){
                    imgbtn1.setVisibility(View.INVISIBLE);
                    imgbtn2.setVisibility(View.VISIBLE);

                }else if (i>=0){
                    imgbtn1.setVisibility(View.VISIBLE);
                    imgbtn2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.frontButton:
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                llm.scrollToPosition(llm.findLastVisibleItemPosition() +1);

                k=mImageUrls.size()-1;
                Log.e("position", String.valueOf(i));
                if (i==k){
                    imgbtn2.setVisibility(View.INVISIBLE);
                    imgbtn1.setVisibility(View.VISIBLE);
                } else if (i>0){
                    imgbtn1.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.home:
                finish();
                break;

            default:
                showUnsupportedSnackBar();
                break;
        }
    }



    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {
        int positionInDataSet = infiniteAdapter.getRealPosition(position);
//        onItemChanged(data.get(positionInDataSet));
    }

    private void showUnsupportedSnackBar() {
        Snackbar.make(itemPicker, R.string.msg_unsupported_op, Snackbar.LENGTH_SHORT).show();
    }


    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add(R.drawable.car_loan);
        mNames.add("Car Loan");

        mImageUrls.add(R.drawable.credit_card);
        mNames.add("Credit Card");

        mImageUrls.add(R.drawable.personal_loan);
        mNames.add("Personal Loan");

        mImageUrls.add(R.drawable.car_loan);
        mNames.add("Car Loan");


        mImageUrls.add(R.drawable.credit_card);
        mNames.add("Credit Card");

        mImageUrls.add(R.drawable.car_loan);
        mNames.add("Personal Loan");


        mImageUrls.add(R.drawable.car_loan);
        mNames.add("Car Loan");

        mImageUrls.add(R.drawable.credit_card);
        mNames.add("Credit Card");

        mImageUrls.add(R.drawable.car_loan);
        mNames.add("Personal Loan");

        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView1, int dx, int dy) {
                super.onScrolled(recyclerView1, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                 i =llm.findFirstCompletelyVisibleItemPosition();

                 k=mImageUrls.size()-1;
                Log.e("totalPosition", String.valueOf(k));
                Log.e("firstPosition", String.valueOf(i));
                if (i==k ){
                    imgbtn2.setVisibility(View.INVISIBLE);
                    imgbtn1.setVisibility(View.VISIBLE);
                } else if (i>0 && i<k){
                    imgbtn1.setVisibility(View.VISIBLE);
                    imgbtn2.setVisibility(View.VISIBLE);
                }else if (i==0){
                    imgbtn1.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private ArrayList<Integer> populateList(){

        ArrayList<Integer> list = new ArrayList<>();

        for(int i = 0; i < IMAGES.length; i++){


            list.add(IMAGES[i]);
        }

        return list;
    }

    public void init() {

            PagerContainer mContainer = (PagerContainer) findViewById(R.id.pager_container);

            final ViewPager pager = mContainer.getViewPager();

            PageAdapter adapter = new PageAdapter(this, arrayList);
            pager.setAdapter(adapter);

            pager.setOffscreenPageLimit(adapter.getCount());

            pager.setClipChildren(false);

            mContainer.setPageItemClickListener(new PageItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });


            boolean showRotate = getIntent().getBooleanExtra("showRotate", true);

            if (showRotate) {
                new CoverFlow.Builder()
                        .with(pager)
                        .scale(0.3f)
                        .pagerMargin(0f)
                        .spaceSize(0f)
                        .rotationY(0f)
                        .build();
            }
            CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlePagerIndicator);
            circlePageIndicator.setViewPager(pager);
            final float density = getResources().getDisplayMetrics().density;
            circlePageIndicator.setRadius(5 * density);
            NUM_PAGE =arrayList.size();

            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage == NUM_PAGE) {
                        currentPage = 0;
                    }
                    pager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTime = new Timer();
            swipeTime.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, 3000, 3000);


            circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPage = position;
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

}

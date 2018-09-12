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

//    LinearLayoutManager linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();


    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgbtn1=(ImageButton)findViewById(R.id.backButton);
        imgbtn2=(ImageButton)findViewById(R.id.frontButton);
        imgbtn1.setVisibility(View.INVISIBLE);


       // dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

//        currentItemName = (TextView) findViewById(R.id.item_name);
//        currentItemPrice = (TextView) findViewById(R.id.item_price);
//        rateItemButton = (ImageView) findViewById(R.id.item_btn_rate);

        shop = Shop.get();
        data = shop.getData();
//        itemPicker = (DiscreteScrollView) findViewById(R.id.item_picker);
//        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
//        itemPicker.addOnItemChangedListener(this);
//        infiniteAdapter = InfiniteScrollAdapter.wrap(new ShopAdapter(data));
//        itemPicker.setAdapter(infiniteAdapter);
////        itemPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
//        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
//                .setMinScale(0.8f)
//                .build());

        getImages();

       // addBottomDots(0);
        arrayList=new ArrayList<>();
        arrayList=populateList();
        init();

        
//        onItemChanged(data.get(0));

//        findViewById(R.id.item_btn_rate).setOnClickListener(this);
//        findViewById(R.id.item_btn_buy).setOnClickListener(this);
//        findViewById(R.id.item_btn_comment).setOnClickListener(this);

//        findViewById(R.id.home).setOnClickListener(this);
//        findViewById(R.id.btn_smooth_scroll).setOnClickListener(this);
//        findViewById(R.id.btn_transition_time).setOnClickListener(this);
    }



//    private void addBottomDots(int currentPage) {
//        dotsCount = mImageUrls.size();
//        System.out.println("count"+dotsCount);
//        dots = new ImageView[dotsCount];
//
//        dotsLayout.removeAllViews();
//        for (int i = 0; i < dotsCount; i++) {
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//            dots[i].setPadding(10,0,10,0);
//            dotsLayout.addView(dots[i]);
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
////        if (dots.length > 0)
////            dots[currentPage].setTextColor(colorsActive[currentPage]);
//    }

    @Override
    public void onClick(View v) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        switch (v.getId()) {

            case R.id.backButton:
                LinearLayoutManager llb = (LinearLayoutManager) recyclerView.getLayoutManager();
                llb.scrollToPosition(llb.findFirstVisibleItemPosition() -1);

                int k=mImageUrls.size()-1;
                int l=llb.findLastVisibleItemPosition()-1;
                Log.e("backPosition", String.valueOf(l));
                if (l==1){
                    imgbtn1.setVisibility(View.INVISIBLE);
                    imgbtn2.setVisibility(View.VISIBLE);

                }else if (l>0){
                    imgbtn1.setVisibility(View.VISIBLE);
                    imgbtn2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.frontButton:
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                llm.scrollToPosition(llm.findLastVisibleItemPosition() +1);

                int j=mImageUrls.size()-1;
                int i=llm.findLastVisibleItemPosition();
                Log.e("position", String.valueOf(i));
                Log.e("total size", String.valueOf(j));

                if (i==j){
                    imgbtn2.setVisibility(View.INVISIBLE);
                    imgbtn1.setVisibility(View.VISIBLE);
                } else if (i>0){
                    imgbtn1.setVisibility(View.VISIBLE);
                }
                break;
//            case R.id.item_btn_rate:
//                int realPosition = infiniteAdapter.getRealPosition(itemPicker.getCurrentItem());
//                Item current = data.get(realPosition);
//                shop.setRated(current.getId(), !shop.isRated(current.getId()));
//                changeRateButtonState(current);
//                break;
            case R.id.home:
                finish();
                break;

//            case R.id.btn_transition_time:
//                DiscreteScrollViewOptions.configureTransitionTime(itemPicker);
//                break;
//            case R.id.btn_smooth_scroll:
//                DiscreteScrollViewOptions.smoothScrollToUserSelectedPosition(itemPicker, v);
//                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }

//    private void onItemChanged(Item item) {
//        currentItemName.setText(item.getName());
//        currentItemPrice.setText(item.getPrice());
////        changeRateButtonState(item);
//    }

//    private void changeRateButtonState(Item item) {
//        if (shop.isRated(item.getId())) {
//            rateItemButton.setImageResource(R.drawable.ic_star_black_24dp);
//            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopRatedStar));
//        } else {
//            rateItemButton.setImageResource(R.drawable.ic_star_border_black_24dp);
//            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopSecondary));
//        }
//    }

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<Integer> populateList(){

        ArrayList<Integer> list = new ArrayList<>();

        for(int i = 0; i < IMAGES.length; i++){
//            ImageModel imageModel = new ImageModel();
//            imageModel.setImage_drawable(myImageList[i]);

            list.add(IMAGES[i]);
        }

        return list;
    }

    public void init() {
//        for (int i = 0; i < IMAGES.length; i++)
//            arrayList.add(IMAGES[i]);

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

package com.dongshang.mactivity.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dongshang.mactivity.myapplication.waveform.WaveFormInfo;
import com.dongshang.mactivity.myapplication.waveform.WaveFormThumbView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private WaveFormThumbView mWaveFormThumbView;


    Html.ImageGetter mImageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable = getResources().getDrawable(Integer.parseInt(source));
            drawable.setBounds(0, 0, 120, 120);
            return drawable;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] layoutTables = {"⛰️", "🏔️", "🗺️", "🗾", "急啊急啊", "急啊急啊", "急啊急啊", "急啊急啊", "急啊急啊", "急啊急啊", "急啊急啊"};

        ((TextView) findViewById(R.id.tv_okay)).setText(Html.fromHtml("哈哈哈<img src='" + R.drawable.ic_launcher_background + "'></img>", mImageGetter, null));
        TabLayout mTab = findViewById(R.id.layout_tab);

        final List<Info> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Info info = new Info();
            info.content = "A";
            list.add(info);
        }

        final ViewPager pager = findViewById(R.id.view_pager);
//        mTab.setupWithViewPager(pager);
        for (String layoutTable : layoutTables) {
            View inflate = View.inflate(this, R.layout.item_tab, null);
            ImageView viewById = inflate.findViewById(R.id.iv_img);
            ViewGroup.LayoutParams layoutParams = viewById.getLayoutParams();
            layoutParams.width = Math.max(new Random().nextInt(250), 90);
            layoutParams.height = 150;
            viewById.setLayoutParams(layoutParams);
            viewById.setImageResource(R.drawable.bg);
            TabLayout.Tab tab = mTab.newTab();
            tab.setCustomView(inflate);
            LinearLayout layout = tab.view;
            layout.setPadding(0, 0, 0, 0);
            mTab.addTab(tab);
        }

        mTab.setSelectedTabIndicator(getResources().getDrawable(R.mipmap.ic_launcher));

        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout layout = tab.view;

                ViewGroup.MarginLayoutParams params = ((ViewGroup.MarginLayoutParams)layout.getLayoutParams());
                params.leftMargin = 15;
                params.rightMargin = 15;
                layout.setLayoutParams(params);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout layout = tab.view;
//                layout.setPadding(5, 0, 5, 0);

                ViewGroup.MarginLayoutParams params = ((ViewGroup.MarginLayoutParams)layout.getLayoutParams());
                params.leftMargin = 5;
                params.rightMargin = 5;
                layout.setLayoutParams(params);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        final FragmentStatePagerAdapter a = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {

                MyFragment fragment = new MyFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("A", list.get(i));
                fragment.setArguments(bundle);

                return fragment;
            }


            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return layoutTables[position];
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        pager.setAdapter(a);

        findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("MyFragment Notify", "");
                list.get(10).content = "给力";
                pager.setCurrentItem(10);
            }
        });

        ImageView launch = findViewById(R.id.iv_launch);
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ImageDetailAct.class);
                intent.putIntegerArrayListExtra("location", getImagePos(v));
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        mWaveFormThumbView = (WaveFormThumbView) findViewById(R.id.wave_form_thumb_view);
        mWaveFormThumbView.setOnDragThumbListener(new WaveFormThumbView.OnDragThumbListener() {
            @Override
            public void onDrag(double startTime) {

            }
        });

        new ReaderTask() {
            @Override
            protected void onPostExecute(WaveFormInfo waveFormInfo) {
                mWaveFormThumbView.setWave(waveFormInfo); // Must be first.

            }
        }.execute();
    }

    static class Info implements Serializable {
        String content = "";
        int position = 0;
    }

    public class ReaderTask extends AsyncTask<Void, Void, WaveFormInfo> {

        @Override
        protected WaveFormInfo doInBackground(Void... params) {
            InputStream inputStream = null;
            try {
                inputStream = getResources().openRawResource(R.raw.waveform);
                byte[] data = new byte[inputStream.available()];
                inputStream.read(data);
                return JSON.parseObject(data, WaveFormInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * 获取图片的位置信息
     */
    public ArrayList getImagePos(View view) {
        int[] locat = new int[2];
        locat[0] = view.getLeft();
        locat[1] = view.getTop();
        int width = view.getWidth();
        int height = view.getHeight();
        ArrayList<Integer> posArr = new ArrayList<>();
        posArr.add(locat[0]);
        posArr.add(locat[1]);
        posArr.add(locat[0] + width);
        posArr.add(locat[1] + height);
        return posArr;
    }
}

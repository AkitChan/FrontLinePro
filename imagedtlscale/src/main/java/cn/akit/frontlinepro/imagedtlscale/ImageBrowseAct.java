package cn.akit.frontlinepro.imagedtlscale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.akit.frontlinepro.common.base.BaseAdapter;
import cn.akit.frontlinepro.common.base.BaseViewHolder;

@Route(path = "/test/activity")
public class ImageBrowseAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_del_scale);


        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("a");
        }

        RecyclerView rvList = findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(ImageBrowseAct.this));
        rvList.setAdapter(new BaseAdapter(ImageBrowseAct.this, list) {
            @NotNull
            @Override
            public BaseViewHolder createViewHolder(@NotNull Context context, int viewType, @NotNull ViewGroup parent, @NotNull BaseAdapter adapter) {
                return new MyHolder(context, parent, this);
            }
        });
    }


    public static class MyHolder extends BaseViewHolder<String> {

        ImageView mImageView;

        public MyHolder(@NotNull Context context, @NotNull ViewGroup parent, @NotNull BaseAdapter<String> adapter) {
            super(context, parent, adapter, R.layout.item_scale_image_browse);
            mImageView = itemView.findViewById(R.id.iv_img);
        }

        @Override
        public void bindData(String obj) {

            Random random = new Random();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mImageView.getLayoutParams();
            params.width = Math.min(450, random.nextInt(800));
            params.height = Math.min(450, random.nextInt(800));
            params.leftMargin = random.nextInt(300);
            mImageView.setLayoutParams(params);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ImageDetailAct.class);
                    intent.putIntegerArrayListExtra("location", getImagePos(v));
                    getContext().startActivity(intent);
                    ((Activity) getContext()).overridePendingTransition(0, 0);
                }
            });
        }



        /**
         * 获取图片的位置信息
         */
        public static ArrayList getImagePos(View view) {
            int[] locat = new int[2];
            view.getLocationOnScreen(locat);
            int width = view.getWidth();
            int height = view.getHeight();
            ArrayList<Integer> posArr = new ArrayList<>();
            posArr.add(locat[0]);
            posArr.add(locat[1]);
            posArr.add(locat[0] + width);
            posArr.add(locat[1] + height);
            KLog.debug("Location--->" + posArr);
            return posArr;
        }
    }


}

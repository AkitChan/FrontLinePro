package cn.akit.frontlinepro;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenYuXuan on 2019/4/19.
 * email : southxvii@163.com
 */
public class ShareReceiveAct extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();//action
        String type = intent.getType();//类型

        //类型
        if (Intent.ACTION_SEND.equals(action) && type != null /*&& "video/mp4".equals(type)*/) {
            Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            //如果是媒体类型需要从数据库获取路径
            String filePath = getRealPathFromURI(uri);
            Toast.makeText(this, "Path:" + filePath, Toast.LENGTH_SHORT).show();
        }

        //类型
        if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null /*&& "video/mp4".equals(type)*/) {
            ArrayList<Uri> uris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            //如果是媒体类型需要从数据库获取路径
            for (Uri uri : uris) {
                String filePath = getRealPathFromURI(uri);
                Toast.makeText(this, "Path:" + filePath, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 通过Uri获取文件在本地存储的真实路径
     */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        }
        cursor.close();
        return null;
    }
}

package com.example.oss;

import static java.nio.file.Paths.get;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.textclassifier.ConversationActions;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.oss.vo.Url1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView list_oss=null;
    private ImageView oss=null;

    String url="https://rtmp123.oss-cn-hangzhou.aliyuncs.com/images/2022/12/27/2022-12-27-00-32-24-0.png?Expires=1987432345&OSSAccessKeyId=LTAI5tQCTPBtkjwWGU6cSM6V&Signature=bhYc7oOpWgqT2NbI%2FriFa47WINY%3D";
    //在消息队列中实现对控件的更改
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    System.out.println(bmp);
                    oss.setImageBitmap(bmp);
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_oss=findViewById(R.id.list_oss);
        oss=findViewById(R.id.oss);
        //新建线程加载图片信息，发送到消息队列中
        new Thread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void run() {

                List<Url1> url1List=new ArrayList<>();
                //拿到的数据url
                url1List=img(10);
                System.out.println(url1List);

                // TODO Auto-generated method stub
                Bitmap bmp = getURLimage(url);
                System.out.println(bmp);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bmp;
                System.out.println("000");
                handle.sendMessage(msg);
            }
        }).start();
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public List<Url1> img(int num){
        List<Url1> url1List=new ArrayList<>();
        try {
            FormBody.Builder params = new FormBody.Builder();
            Intent intent = getIntent();
            OkHttpClient client = new OkHttpClient();//创建http客户端
            String num1=String.valueOf(num);
            params.add("pageSize",num1);
            okhttp3.Request request = new Request.Builder()
                    .url("http://42.193.114.253/img/find1?pageNum=1")
                    .post(params.build())
                    .build();
            Response response = client.newCall(request).execute();//执行发送的请求
            String responseDate = response.body().string();

            JSONObject jsonObject1=new JSONObject(responseDate);
            JSONArray jsonArray=new JSONArray();
            jsonArray=jsonObject1.getJSONArray("list");
            System.out.println(jsonArray);

            for(int i=0;i<10;i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                int id=jsonObject.getInt("id");
                String url1=jsonObject.getString("url1");
                String time =jsonObject.getString("time");
                Url1 url11=new Url1();
                url11.setId(id);
                url11.setUrl1(url1);
                url11.setTime(time);
                url1List.add(url11);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return url1List;
    }

    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }


}
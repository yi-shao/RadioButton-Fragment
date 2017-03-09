package com.example.frame_demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frame_demo.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * 作者：忆 on 2016/12/5 22:10
 * 功能：okhttp
 */
public class OKHttpActivity extends Activity implements View.OnClickListener {
    /**
     * GET请求
     */
    private static final int GET = 1;
    /**
     * post请求
     */
    private static final int POST = 2;
    private static final String TAG = OKHttpActivity.class.getSimpleName();
    private Button btn_get_post;
    private TextView tv_result;
    private Button btn_get_okhttputils;
    private Button btn_downloadfile;
    private ProgressBar mProgressBar;
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        btn_get_post = (Button) findViewById(R.id.btn_get_post);
        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_get_okhttputils = (Button) findViewById(R.id.btn_get_okhttputils);
        btn_downloadfile=(Button) findViewById(R.id.btn_downloadfile);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //设置点击事件
        btn_get_post.setOnClickListener(this);
        btn_get_okhttputils.setOnClickListener(this);
        btn_downloadfile.setOnClickListener(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET:
                    //获取数据
                    tv_result.setText((String) msg.obj);
                    break;
                case POST:
                    //获取数据
                    tv_result.setText((String) msg.obj);
                    break;

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_post://使用原生的okhttp请求数据,get和post请求
                tv_result.setText("");
                getDataFromPost();
                // getDataFromGet();
                break;
            case R.id.btn_get_okhttputils:
                //getDataByokHttpUtils(tv_result);//get请求
                getDataPostByokHttpUtils(tv_result);//post请求
                break;
            case R.id.btn_downloadfile://下载文件
                downloadFile();
                break;
        }
    }

    /**
     * 使用get请求网络数据
     */
    private void getDataFromGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = get("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
                    Log.e("TAG", result);
                    Message msg = Message.obtain();
                    msg.what = GET;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    private String get(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * okhttp的post请求
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 使用post请求网络数据
     */
    private void getDataFromPost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = post("http://api.m.mtime.cn/PageSubArea/TrailerList.api", "");
                    Log.e("TAG", result);
                    Message msg = Message.obtain();
                    msg.what = POST;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 使用okhttp-utils的get请求网络文本数据
     *
     * @param view
     */
    public void getDataByokHttpUtils(View view) {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        //url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 使用okhttp-utils的post请求网络文本数据
     *
     * @param view
     */
    public void getDataPostByokHttpUtils(View view) {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        //url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .post()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(okhttp3.Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            tv_result.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            tv_result.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(OKHttpActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OKHttpActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            //mProgressBar.setProgress((int) (100 * progress));
        }
    }

    /**
     * 使用okhttp-utils下载大文件
     */
    public void downloadFile() {
        String url = "http://vfx.mtime.cn/Video/2016/12/04/mp4/161204095742030265_480.mp4";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttp-utils-test.mp4")//
                {

                    @Override
                    public void onBefore(okhttp3.Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mProgressBar.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }
}

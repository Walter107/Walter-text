package com.example.administrator.sportsscores;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.sportsscores.Activity_p.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.administrator.sportsscores.Activity_p.URl.URL.Register;
import static com.example.administrator.sportsscores.Activity_p.URl.URL.choseTime;
import static com.example.administrator.sportsscores.Activity_p.URl.URL.querystudent;
//登录界面
public class Main2Activity extends AppCompatActivity {
    private Button  bt_login;
    private EditText ED_id, ED_mm;
    String className, classNumber, gread, institute, instituteNumber, location, major, majornumber, name, passWord, sex, studentCode;
    private TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt_login = findViewById(R.id.bt_login);
        ED_id = findViewById(R.id.editText_id);
        ED_mm = findViewById(R.id.editText_mm);
        textView1 = findViewById(R.id.textView20);
        textView2 = findViewById(R.id.textView21);


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Registered.class);
                startActivity(intent);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //获取用户注册的用户名
        Intent intent_getuse = getIntent();
        String userName = intent_getuse.getStringExtra("用户名");
        ED_id.setText(userName);
    }



    //获取身份信息
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/get_data.json")
                        .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject demoJson = new JSONObject(jsonData);
             className = demoJson.optString("className");
             classNumber = demoJson.optString("classNumber");
             gread = demoJson.optString("gread");
             institute = demoJson.optString("institute");
             instituteNumber = demoJson.optString("instituteNumber");
             location = demoJson.optString("location");
             major = demoJson.optString("major");
             majornumber = demoJson.optString("majornumber");
             name = demoJson.optString("name");
             passWord = demoJson.optString("passWord");
             sex = demoJson.optString("sex");
             studentCode = demoJson.optString("studentCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.example.administrator.sportsscores.Activity_p.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.sportsscores.ApplyImm;
import com.example.administrator.sportsscores.ChooseTime_st;
import com.example.administrator.sportsscores.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//学生登录界面
public class Student extends AppCompatActivity {
    private LinearLayout layout1,layout2,layout3;
    private Button bt_st;
    String startTime,days;
    TextView textView;
    String result;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0X01){
                textView.append(result);
                result = "";
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        layout1 = findViewById(R.id.StLlayout_1);
        layout2 = findViewById(R.id.StLlayout_2);
        layout3 = findViewById(R.id.StLlayout_3);
        textView = findViewById(R.id.textView10);
        bt_st = findViewById(R.id.bt_st);



//查询当前可预约时间段
        bt_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okhttp_1("http://localhost:8080/SportsSystem/welcome.htm\n");
                AlertDialog.Builder builder = new AlertDialog.Builder(Student.this);
                builder.setIcon(R.drawable.ic_launcher_background);
                builder.setTitle("当前可预约时间为");
                builder.setMessage("开始预约时间"+startTime+"从开始之后的天数"+days);
                builder.setPositiveButton("确定",null);
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });






//网上获取公告内容
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("http://localhost:8080/SportsSystem/welcome.htm");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setInstanceFollowRedirects(true);
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//                    OutputStream outputStream = connection.getOutputStream();
//                    PrintStream printStream = new PrintStream(outputStream);
//                    String string = "";
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK);{
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader =new BufferedReader(new InputStreamReader(inputStream));
                        String string2;
                        while ((string2=reader.readLine())!=null)
                        {
                            result+=string2+"\n";
                        }
                        handler.sendEmptyMessage(0X01);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();







        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student.this, ChooseTime_st.class);
                startActivity(intent);
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student.this,MainActivity.class);
                startActivity(intent);
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student.this, ApplyImm.class);
                startActivity(intent);
            }
        });



    }
    //okhttp
    private void okhttp_1(final String pathSt){
        new Thread()
        {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(pathSt)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseXMLWithPull(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    private  void parseXMLWithPull(String xmlData){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            while (eventType!=XmlPullParser.END_DOCUMENT){
                String nodeNmae = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if ("startTime".equals(nodeNmae)) {
                            startTime = xmlPullParser.nextText();
                        } else if ("days".equals(nodeNmae)) {
                            days = xmlPullParser.nextText();
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

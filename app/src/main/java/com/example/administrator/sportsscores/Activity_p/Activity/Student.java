package com.example.administrator.sportsscores.Activity_p.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.sportsscores.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.administrator.sportsscores.Activity_p.URl.URL.queryNews;

public class Student extends AppCompatActivity {
    private LinearLayout layout1,layout2,layout3;
    TextView textView;
    String result;
    Handler handler =new Handler(){
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

        new Thread()
        {
            @Override
            public void run() {
                super.run();
                    String pathString =queryNews;
                try {
                    URL url = new URL(pathString);
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
                Intent intent = new Intent(Student.this, MainActivity.class);
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
                Intent intent = new Intent(Student.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
}

package com.example.administrator.sportsscores;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ChooseTime_st extends AppCompatActivity {
    private EditText sName,sCode,sClass,stime,classTime,location;;
    private Button button;
    String result;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0X01){
                if(result=="-1"){
                    Toast.makeText(ChooseTime_st.this,"人数达到上限",Toast.LENGTH_LONG).show();
                }if (result=="1"){
                    Toast.makeText(ChooseTime_st.this,"预约成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ChooseTime_st.this,"预约失败",Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time_st);
        sName = findViewById(R.id.editText10);
        sCode = findViewById(R.id.editText11);
        sClass = findViewById(R.id.editText12);
        stime = findViewById(R.id.editText13);
        classTime = findViewById(R.id.editText14);
        location = findViewById(R.id.editText15);
        button = findViewById(R.id.bt_chooseTime);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        String pathString ="http://localhost:8080/SportsSystem/welcome.htm";
                        try {
                            URL url = new URL(pathString);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoInput(true);
                            connection.setDoOutput(true);
                            connection.setUseCaches(false);
                            connection.setInstanceFollowRedirects(true);
                            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                            OutputStream outputStream = connection.getOutputStream();
                            PrintStream printStream = new PrintStream(outputStream);
                            String string = "sName="+sName.getText().toString()+"&sCode="+sCode.getText().toString()
                                    +"&sClass="+sClass.getText().toString()+"&stime="+stime.getText().toString()
                                    +"&classTime="+classTime.getText().toString()+"&location="+location.getText().toString();
                            printStream.print(string);
                            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK);{
                                InputStream inputStream = connection.getInputStream();
                                BufferedReader reader =new BufferedReader(new InputStreamReader(inputStream));
                                result=reader.readLine();
                                handler.sendEmptyMessage(0X01);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
    }
}

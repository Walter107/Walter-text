package com.example.administrator.sportsscores;

import android.content.Intent;
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
//学生注册
public class Registered extends AppCompatActivity {
    private Button button;
    private EditText location,className,studentCode,passWord,name,sex;
    String result;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0X01){
                if (result=="1"){
                    Toast.makeText(Registered.this,"注册成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registered.this,Main2Activity.class);
                    intent.putExtra("用户名",studentCode.getText());
                    startActivity(intent);
                }else {
                    Toast.makeText(Registered.this,"注册失败",Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        button = findViewById(R.id.button_Reg);
        studentCode = findViewById(R.id.editText_studentCode);
        passWord = findViewById(R.id.editText_passWord);
        name = findViewById(R.id.editText_name);
        sex = findViewById(R.id.editText_sex);
        className = findViewById(R.id.editText_className);
        location = findViewById(R.id.editText_location);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        String pathString ="";
                        try {
                            URL url = new URL(pathString);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoInput(true);
                            connection.setDoOutput(true);
                            connection.setUseCaches(false);
                            connection.setRequestMethod(String.valueOf(8000));
                            connection.setReadTimeout(8000);
                            connection.setInstanceFollowRedirects(true);
                            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                            OutputStream outputStream = connection.getOutputStream();
                            PrintStream printStream = new PrintStream(outputStream);
                            String string = "location="+location.getText().toString()+"&className="+className.getText().toString()
                                    +"&studentCode="+studentCode.getText().toString()+"&passWord="+passWord.getText().toString()
                                    +"&name="+name.getText().toString()+"&sex="+sex.getText().toString();
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

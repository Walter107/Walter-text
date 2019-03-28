package com.example.administrator.sportsscores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.sportsscores.Activity_p.Activity.Student;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ApplyImm extends AppCompatActivity {
//申请免测
    private Button bt;
    private EditText editText1,editText2,editText3,editText4,editText5,editText6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_imm);
        bt = findViewById(R.id.button);
        editText1 = findViewById(R.id.editText4);
        editText2 = findViewById(R.id.editText11);
        editText3 = findViewById(R.id.editText12);
        editText4 = findViewById(R.id.editText7);
        editText5 = findViewById(R.id.editText8);
        editText6 = findViewById(R.id.editText9);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkhttp();
            }
        });
    }
    public void sendRequestWithOkhttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("Snumber", String.valueOf(editText1.getText()))
                        .add("Greadname",String.valueOf(editText2.getText()))
                        .add("Sname", String.valueOf(editText3.getText()))
                        .add("Institute",String.valueOf(editText4.getText()))
                        .add("institutenumber", String.valueOf(editText5.getText()))
                        .add("Reason",String.valueOf(editText6.getText()))
                        .build();
                Request request = new Request.Builder()
                        .url("")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (responseData=="1"){
                        Toast.makeText(ApplyImm.this,"申请成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ApplyImm.this, Student.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ApplyImm.this,"申请失败",Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

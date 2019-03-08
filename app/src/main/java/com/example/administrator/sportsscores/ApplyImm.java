package com.example.administrator.sportsscores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import static com.example.administrator.sportsscores.Activity_p.URl.URL.applyImm;

public class ApplyImm extends AppCompatActivity {

    private Button bt;
    private EditText editText1,editText2,editText3,editText4,editText5,editText6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_imm);
        bt = findViewById(R.id.button);
        editText1 = findViewById(R.id.editText4);
        editText2 = findViewById(R.id.editText5);
        editText3 = findViewById(R.id.editText6);
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
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("Snumber", String.valueOf(editText1.getText()))
                        .add("Greadname",String.valueOf(editText2.getText()))
                        .add("Sname", String.valueOf(editText3.getText()))
                        .add("Institute",String.valueOf(editText4.getText()))
                        .add("institutenumber", String.valueOf(editText5.getText()))
                        .add("Reason",String.valueOf(editText6.getText()))
                        .build();
                Request request = new Request.Builder()
                        .url(applyImm)
                        .post(requestBody)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    parseXMLWithPull(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        //开始解析某个节点
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("app".equals(nodeName)){
                            Log.d("MainActivity","id is "+id);
                            Log.d("MainActivity","name is "+name);
                            Log.d("MainActivity","version is "+version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

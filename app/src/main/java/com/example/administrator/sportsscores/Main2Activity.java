package com.example.administrator.sportsscores;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import com.example.administrator.sportsscores.Activity_p.Activity.DivisionManager;
import com.example.administrator.sportsscores.Activity_p.Activity.Student;
import com.example.administrator.sportsscores.Activity_p.Activity.SuperAdministrator;
import com.example.administrator.sportsscores.Activity_p.Activity.Teacher;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//登录界面
public class Main2Activity extends AppCompatActivity {
    Toolbar toolbar;
    private Button  bt_login;
    private EditText ED_id, ED_mm;
    private TextView textView1,textView2;
    private String result;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0101){
                Toast.makeText(Main2Activity.this,"你好",Toast.LENGTH_LONG).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt_login = findViewById(R.id.bt_login);
        ED_id = findViewById(R.id.editText_id);
        ED_mm = findViewById(R.id.editText_mm);
        textView1 = findViewById(R.id.textView20);
        textView2 = findViewById(R.id.textView21);
        toolbar = findViewById(R.id.toolbar_Main2Activity);

//        toolbar.setTitle("登录");

//忘记密码
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//注册账号
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Registered.class);
                startActivity(intent);
            }
        });
//登录
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });

        //获取用户注册的用户名
        get_id();
    }


    //获取身份信息
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("name", String.valueOf(ED_id.getText()))
                        .add("password", String.valueOf(ED_mm.getText()))
                        .build();
                Request request = new Request.Builder()
                        .url("https://result.eolinker.com/u8a8Qjf1df6a5f1087457c3975507c1f09f0cf90e08762e?uri=http://localhost:8080/SportsSystem/welcome.htm")//welcome
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                switch (responseData){
                    case "1":
                        Intent intent1 = new Intent(Main2Activity.this, SuperAdministrator.class);
                        startActivity(intent1);
                        break;
                    case "2":
                        Intent intent2 = new Intent(Main2Activity.this, Teacher.class);
                        startActivity(intent2);
                        break;
                    case "3":
                        Intent intent3 = new Intent(Main2Activity.this, DivisionManager.class);
                        startActivity(intent3);
                        break;
                    case "4":
                        Intent intent4 = new Intent(Main2Activity.this, Student.class);
                        startActivity(intent4);
                        break;
                    case "-1":
                        Intent intent5 = new Intent(Main2Activity.this, Student.class);
                        startActivity(intent5);
                        handler.sendEmptyMessage(0101);
//                        Toast.makeText(Main2Activity.this,"",Toast.LENGTH_LONG).show();
                        //AS在子线程中不可进行ui操作，所以Toast应该放到Hander线程里面
                        break;
                    default:break;
                }
//                parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
//    private void parseJSONWithJSONObject(String jsonData) {
//        try {
//            JSONObject demoJson = new JSONObject(jsonData);
//             className = demoJson.optString("className");
//             classNumber = demoJson.optString("classNumber");
//             gread = demoJson.optString("gread");
//             institute = demoJson.optString("institute");
//             instituteNumber = demoJson.optString("instituteNumber");
//             location = demoJson.optString("location");
//             major = demoJson.optString("major");
//             majornumber = demoJson.optString("majornumber");
//             name = demoJson.optString("name");
//             passWord = demoJson.optString("passWord");
//             sex = demoJson.optString("sex");
//             studentCode = demoJson.optString("studentCode");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    //获取用户注册的用户名
    private void get_id(){
        Intent intent_getuse = getIntent();
        String userName = intent_getuse.getStringExtra("用户名");
        ED_id.setText(userName);
    }
}

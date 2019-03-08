package com.example.administrator.sportsscores.Activity_p.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.sportsscores.R;

public class MainActivity extends AppCompatActivity {
    private Button btSuper;
    private Button btTeacher;
    private Button btDivision;
    private Button btStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSuper = findViewById(R.id.btSuper);
        btTeacher = findViewById(R.id.btTeacher);
        btDivision = findViewById(R.id.btDivision);
        btStudent = findViewById(R.id.btStudent);

        btStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Student.class);
                startActivity(intent);
            }
        });

        
    }
    public void btSo(){
        Intent intent = new Intent(MainActivity.this,Student.class);
        startActivity(intent);
    }
}

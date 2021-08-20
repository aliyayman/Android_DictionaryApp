package com.aliyayman.sozluk4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aliyayman.sozluk4.database.DatabaseCopyHelper;
import com.aliyayman.sozluk4.database.Veritabani;
import com.aliyayman.sozluk4.R;

import java.io.IOException;

public class QuizeAnaActivity extends AppCompatActivity {

    private Button buttonBasla;
    private Veritabani vt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize_ana);

        buttonBasla=findViewById(R.id.buttonBasla);
        vt=new Veritabani(this);
        veritabaniKopyala();

        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(QuizeAnaActivity.this,QuizeActivity.class));

            }
        });
    }
    public void veritabaniKopyala(){
        DatabaseCopyHelper helper= new DatabaseCopyHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();

    }
    }

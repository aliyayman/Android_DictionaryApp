package com.aliyayman.sozluk4.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aliyayman.sozluk4.R;

public class ResultActivity extends AppCompatActivity {
    private Button buttonRes;
    private TextView textViewSonuc;
    private TextView textViewYuzde;
    private int dogruSayac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewSonuc=findViewById(R.id.textViewSonuc);
        textViewYuzde=findViewById(R.id.textViewYuzde);
        buttonRes=findViewById(R.id.buttonRes);

        dogruSayac=getIntent().getIntExtra("dogruSayac",0);
        textViewSonuc.setText(dogruSayac+" DOĞRU "+(10-dogruSayac)+" YANLIŞ");
        textViewYuzde.setText("%"+(dogruSayac*10)+" BAŞARI");


        buttonRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ResultActivity.this,QuizeActivity.class));

                finish();
            }
        });

    }
}
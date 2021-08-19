package com.aliyayman.sozluk4.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aliyayman.sozluk4.Adapter.UniteAdapter;
import com.aliyayman.sozluk4.Dao.UnitelerDao;
import com.aliyayman.sozluk4.Entity.Database.DatabaseCopyHelper;
import com.aliyayman.sozluk4.Entity.Database.Veritabani;
import com.aliyayman.sozluk4.Entity.Uniteler;
import com.aliyayman.sozluk4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private Veritabani vt;
    private ArrayList<Uniteler> unitelerList;
    private UniteAdapter adapter;
    private ArrayList<Uniteler> unitelers;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        rv=findViewById(R.id.rv_kelime);
        fab=findViewById(R.id.fab);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,QuizeAnaActivity.class));
            }
        });

        veritabaniKopyala();
        vt=new Veritabani(this);

        unitelerList=new UnitelerDao().tumUniteler(vt);

        adapter=new UniteAdapter(this,unitelerList);

        toolbar.setTitle("Sözlük Uygulaması");
        setSupportActionBar(toolbar);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              alertGoster();

          }
      });

    }
    public void veritabaniKopyala(){

        DatabaseCopyHelper helper=new DatabaseCopyHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();
    }

    public void alertGoster(){

        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.alert_unite_tasarim,null);

        EditText unite_ad=view.findViewById(R.id.uniteAd);


        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("Kelime Tablonu Oluştur");
        ad.setView(view);
        ad.setPositiveButton("oluştur", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String uAd=unite_ad.getText().toString().trim();


                Log.e("kelime eklendi",uAd);

                vt=new Veritabani(MainActivity.this);
                new UnitelerDao().uniteEkle(vt,uAd);
                unitelers=new UnitelerDao().tumUniteler(vt);
                adapter=new UniteAdapter(MainActivity.this,unitelers);
                rv.setAdapter(adapter);


            }
        });
        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.create().show();

    }

}


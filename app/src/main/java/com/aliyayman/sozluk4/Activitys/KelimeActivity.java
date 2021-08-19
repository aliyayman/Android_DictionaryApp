package com.aliyayman.sozluk4.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.aliyayman.sozluk4.Adapter.KelimeAdapter;
import com.aliyayman.sozluk4.Dao.KelimelerDao;
import com.aliyayman.sozluk4.Entity.Database.Veritabani;
import com.aliyayman.sozluk4.Entity.Kelimeler;
import com.aliyayman.sozluk4.Entity.Uniteler;
import com.aliyayman.sozluk4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class KelimeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar_kelime;
    private RecyclerView rv_kelime;
    private FloatingActionButton fab_kelime;
    private ArrayList<Kelimeler> kelimelerList;
    private KelimeAdapter adapter;
    private Uniteler unite;
    private Veritabani vt;
    private Uniteler uniteler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime);


        toolbar_kelime=findViewById(R.id.toolbar_kelime);
        rv_kelime=findViewById(R.id.rv_kelime);
        fab_kelime=findViewById(R.id.fab_kelime);


        uniteler= (Uniteler) getIntent().getSerializableExtra("nesne");
        vt=new Veritabani(this);

        kelimelerList=new KelimelerDao().tumKelimelerUniteId(vt,uniteler.getUnite_id());


      adapter=new KelimeAdapter(this,kelimelerList,vt);

        toolbar_kelime.setTitle(uniteler.getUnite_ad()+" Kelimeleri");
        setSupportActionBar(toolbar_kelime);
       rv_kelime.setHasFixedSize(true);
        rv_kelime.setLayoutManager(new LinearLayoutManager(this));
        rv_kelime.setAdapter(adapter);

        fab_kelime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertGoster();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.action_ara);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("kelime:",newText);
                kelimelerList=new KelimelerDao().kelimeAra(vt,uniteler.getUnite_id(),newText);
                adapter=new KelimeAdapter(KelimeActivity.this,kelimelerList,vt);
                rv_kelime.setAdapter(adapter);


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    public void alertGoster(){

        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.alert_tasarim,null);

        EditText editIng=view.findViewById(R.id.editIng);
        EditText editTc=view.findViewById(R.id.editTc);

        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("Kelime Ekle");
        ad.setView(view);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ing_kelime=editIng.getText().toString().trim();
                String tc_kelime=editTc.getText().toString().trim();

                Log.e("kelime eklendi",ing_kelime);

                vt=new Veritabani(KelimeActivity.this);
                new KelimelerDao().kelimeEkle(vt,ing_kelime,tc_kelime,uniteler.getUnite_id());

                kelimelerList=new KelimelerDao().tumKelimelerUniteId(vt,uniteler.getUnite_id());
                adapter=new KelimeAdapter(KelimeActivity.this,kelimelerList,vt);
                rv_kelime.setAdapter(adapter);


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
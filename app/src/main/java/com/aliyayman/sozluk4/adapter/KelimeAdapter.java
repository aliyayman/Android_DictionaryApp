    package com.aliyayman.sozluk4.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyayman.sozluk4.dao.KelimelerDao;
import com.aliyayman.sozluk4.database.Veritabani;
import com.aliyayman.sozluk4.model.Kelimeler;
import com.aliyayman.sozluk4.R;

import java.util.List;
import java.util.Locale;

public class KelimeAdapter extends RecyclerView.Adapter<KelimeAdapter.CardTasarimTutucu> implements TextToSpeech.OnInitListener {
    private Context mContext;
    private List<Kelimeler> kelimelerList;
    private Veritabani vt;
    private TextToSpeech tts;


    public KelimeAdapter(Context mContext, List<Kelimeler> kelimelerList,Veritabani vt) {
        this.mContext = mContext;
        this.kelimelerList = kelimelerList;
        this.vt=vt;
    }

    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.kelime_card_tasarim,parent,false);
        return  new CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTasarimTutucu holder, int position) {

        Kelimeler kelime= kelimelerList.get(position);
        holder.textViewIng.setText(kelime.getIng());
        holder.textViewTc.setText(kelime.getTc());
        holder.imageViewSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("ses tıklandı",kelime.getIng());

                tts=new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i==TextToSpeech.SUCCESS){
                            tts.setLanguage(Locale.ENGLISH);
                            tts.setSpeechRate(1.0f);
                            tts.speak(kelime.getIng(),TextToSpeech.QUEUE_FLUSH,null);
                        }
                        else{
                            Toast.makeText(mContext,"başarısız",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(mContext,holder.imageView);
                popupMenu.getMenuInflater().inflate(R.menu.image_manu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.action_sil:

                                Log.e("silindi:",kelime.getIng()+kelime.getKelime_id());

                                vt=new Veritabani(mContext);
                                new KelimelerDao().kelimeSil(vt,kelime.getKelime_id());
                               kelimelerList=new KelimelerDao().tumKelimelerUniteId(vt,kelime.getUnite().getUnite_id());
                               notifyDataSetChanged();

                                return true;
                            case R.id.action_duzenle:

                                alertGoster(kelime);


                                return true;
                            default:
                                return false;

                        }


                    }
                });
                popupMenu.show();

            }
        });

    }


    @Override
    public int getItemCount() {

        return kelimelerList.size();
    }

    @Override
    public void onInit(int status) {

        tts=new TextToSpeech(mContext,this);
        if(status == tts.SUCCESS){
            int sonuc=tts.setLanguage(Locale.ENGLISH);
            if(sonuc==tts.LANG_MISSING_DATA ||sonuc==tts.LANG_NOT_SUPPORTED){
                Toast.makeText(mContext,"Bu dil desteklenmiyor.",Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(mContext,"Başarılı",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(mContext,"Başarısız",Toast.LENGTH_SHORT).show();
        }
    }
    private void kelimeOku(String kelimetext) {
        tts=new TextToSpeech(mContext,this);

        if(null==kelimetext || "".equals(kelimetext)){
            Toast.makeText(mContext,"veri giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        tts.setSpeechRate(1.0f);
        tts.setPitch(1.0f);
        tts.speak(kelimetext,tts.QUEUE_FLUSH,null);


    }


    public class CardTasarimTutucu extends RecyclerView.ViewHolder{
        private TextView textViewIng;
        private TextView textViewTc;
        private ImageView imageView;
        private ImageView imageViewSes;


        public CardTasarimTutucu(@NonNull View itemView) {
            super(itemView);
            textViewIng=itemView.findViewById(R.id.textViewIng);
            textViewTc=itemView.findViewById(R.id.textViewTc);
            imageView=itemView.findViewById(R.id.imageView);
            imageViewSes=itemView.findViewById(R.id.imageViewSes);
        }
    }
    public void alertGoster(Kelimeler kelime){
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.alert_tasarim,null);

        EditText editIng=view.findViewById(R.id.editIng);
        EditText editTc=view.findViewById(R.id.editTc);

        editIng.setText(kelime.getIng());
        editTc.setText(kelime.getTc());

        AlertDialog.Builder ad=new AlertDialog.Builder(mContext);
        ad.setTitle("Kelime Güncelle");
        ad.setView(view);
        ad.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String ing_kelime=editIng.getText().toString().trim();
                String tc_kelime=editTc.getText().toString().trim();

                Log.e("guncellendi:",kelime.getIng()+kelime.getKelime_id());

                vt=new Veritabani(mContext);
                new KelimelerDao().kelimeGuncelle(vt,kelime.getKelime_id(),ing_kelime,tc_kelime);
                kelimelerList=new KelimelerDao().tumKelimelerUniteId(vt,kelime.getUnite().getUnite_id());
                notifyDataSetChanged();


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

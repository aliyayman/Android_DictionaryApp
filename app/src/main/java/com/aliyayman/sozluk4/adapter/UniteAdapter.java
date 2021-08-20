package com.aliyayman.sozluk4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyayman.sozluk4.model.Uniteler;
import com.aliyayman.sozluk4.activity.KelimeActivity;
import com.aliyayman.sozluk4.R;

import java.util.List;

public class UniteAdapter extends RecyclerView.Adapter<UniteAdapter.cardNesneTutucu> {
    private Context mContext;
    private List<Uniteler> unitelerList;

    public UniteAdapter(Context mContext, List<Uniteler> unitelerList) {
        this.mContext = mContext;
        this.unitelerList = unitelerList;
    }

    @NonNull
    @Override
    public cardNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.unite_card_tasarim,parent,false);
       return new cardNesneTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cardNesneTutucu holder, int position) {
        Uniteler unite=unitelerList.get(position);

        holder.textViewYazi.setText(unite.getUnite_ad());
        holder.unite_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, KelimeActivity.class);
                intent.putExtra("nesne",unite);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return unitelerList.size();
    }

    public class cardNesneTutucu extends RecyclerView.ViewHolder{
        private CardView unite_card;
        private TextView  textViewYazi;

        public cardNesneTutucu(@NonNull View itemView) {
            super(itemView);
            textViewYazi=itemView.findViewById(R.id.textViewYazi);
            unite_card=itemView.findViewById(R.id.unite_card);
        }
    }
}

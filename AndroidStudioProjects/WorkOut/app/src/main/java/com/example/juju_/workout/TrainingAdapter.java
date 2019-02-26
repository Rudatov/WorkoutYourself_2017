package com.example.juju_.workout;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by juju_ on 23/08/2016.
 */
public class TrainingAdapter extends ArrayAdapter<Etape> {

    //tweets est la liste des models à afficher
    public TrainingAdapter(Context context, List<Etape> etape) {
        super(context, R.layout.simple_textview_perso, etape);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_textview_perso,parent, false);
        }
        Etape etape=getItem(position);

        ExerciceViewHolder viewHolder = new ExerciceViewHolder();

        viewHolder.nom_exercice = (TextView) convertView.findViewById(R.id.textView_name_etape);
        viewHolder.desc_exercice = (TextView) convertView.findViewById(R.id.textView_desc_etape);
        viewHolder.temps_exercice = (TextView) convertView.findViewById(R.id.textView_temps_etape);
        viewHolder.pause_exercice = (TextView) convertView.findViewById(R.id.textView_pause_etape);
        viewHolder.nom_exercice.setText(etape.getTitre());
        viewHolder.desc_exercice.setText(etape.getDesc());
        viewHolder.temps_exercice.setText(""+etape.getTemps());
        viewHolder.pause_exercice.setText(""+etape.getPause());
        convertView.setTag(viewHolder);





        //il ne reste plus qu'à remplir notre vue



        return convertView;
    }

    private class ExerciceViewHolder {
        public TextView nom_exercice;
        public TextView desc_exercice;
        public TextView temps_exercice;
        public TextView pause_exercice;
        public TimerEtapeAnimation anim_circle;

    }
}

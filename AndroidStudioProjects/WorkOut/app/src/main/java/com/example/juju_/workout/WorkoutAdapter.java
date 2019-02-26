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
public class WorkoutAdapter extends ArrayAdapter<Workout> {

    //tweets est la liste des models à afficher
    public WorkoutAdapter(Context context, List<Workout> list_workout) {
        super(context, R.layout.white_text_cell, list_workout);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.white_text_cell,parent, false);
        }
        Workout wo_tmp=getItem(position);

        WorkoutViewHolder viewHolder = new WorkoutViewHolder();

        viewHolder.nom_workout = (TextView) convertView.findViewById(R.id.textView_nom_showork);
        viewHolder.nb_etp_workout = (TextView) convertView.findViewById(R.id.textView_nb_etp_showork);
        viewHolder.nom_workout.setText(wo_tmp.toString());
        viewHolder.nb_etp_workout.setText(""+wo_tmp.getNbEtape());

        convertView.setTag(viewHolder);

        return convertView;
    }

    private class WorkoutViewHolder {
        public TextView nom_workout;
        public TextView nb_etp_workout;


    }
}

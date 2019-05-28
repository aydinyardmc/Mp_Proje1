package com.aydinyardimci.mp_proje1;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Not> {

    public NoteAdapter(Context context, int resource, ArrayList<Not> not) {
        super(context, resource, not);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_note_view,null);
        }

        Not not = getItem(position);
       // boolean isTras = getItem(position).isTrash();

        if(not != null){

            TextView baslik=(TextView) convertView.findViewById(R.id.singleNotTitle);
            TextView tarih = (TextView) convertView.findViewById((R.id.singleNotTime));
            baslik.setText(not.getBaslik());
            tarih.setText(not.getTimeString(getContext()));
            ConstraintLayout bg = (ConstraintLayout) convertView.findViewById(R.id.singleNoteColor);
            bg.setBackgroundColor(Color.parseColor(not.getColor()));
           // bg.setOutlineSpotShadowColor(Color.parseColor("#FF0000"));
            //bg.setOutlineAmbientShadowColor(Color.parseColor("#FF0000"));
        }

        return convertView;
    }
    public Not getNotPosition(int position){
         Not not = getItem(position);
         return not;
    }
}

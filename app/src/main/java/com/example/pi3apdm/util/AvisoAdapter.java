package com.example.pi3apdm.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.pi3apdm.model.AvisoVO;

import java.util.List;

public class AvisoAdapter extends ArrayAdapter<AvisoVO> {
    private Context context;
    private List<AvisoVO> avisos;

    public AvisoAdapter(Context context, List<AvisoVO> avisos) {
        super(context, 0, avisos);
        this.context = context;
        this.avisos = avisos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        AvisoVO aviso = avisos.get(position);

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        text1.setText(aviso.getTitulo());
        text2.setText(aviso.getConteudo());

        return convertView;
    }
}

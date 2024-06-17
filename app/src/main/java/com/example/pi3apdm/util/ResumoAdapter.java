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


import com.example.pi3apdm.model.ResumoVO;

import java.util.List;

public class ResumoAdapter extends ArrayAdapter<ResumoVO> {
    private Context context;
    private List<ResumoVO> resumos;

    public ResumoAdapter(Context context, List<ResumoVO> resumos) {
        super(context, 0, resumos);
        this.context = context;
        this.resumos = resumos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        ResumoVO resumo = resumos.get(position);

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        text1.setText(resumo.getTitulo());
        text2.setText(resumo.getConteudo());

        return convertView;
    }
}

package com.example.pi3apdm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.pi3apdm.model.ResumoVO;


import java.util.ArrayList;
import java.util.List;


public class ResumoDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PI3AMVC_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TB_RESUMOS = "tb_resumos";
    private static final String KEY_ID = "id";
    private static final String TITULO = "titulo";
    private static final String CONTEUDO = "conteudo";
    private static final String PROFESSOR_ID = "professor_id";



    public ResumoDAO(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_RESUMOS = "CREATE TABLE "+ TB_RESUMOS + "(" + KEY_ID + "INTEGER PRIMARY KEY," + TITULO + " TEXT," + CONTEUDO + " TEXT," + PROFESSOR_ID + " TEXT)";
        db.execSQL(CREATE_TB_RESUMOS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_RESUMOS);
        onCreate(db);
    }


    public void addResumo(ResumoVO resumoVO){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITULO, resumoVO.getTitulo());
        contentValues.put(CONTEUDO, resumoVO.getConteudo());
        contentValues.put(PROFESSOR_ID, resumoVO.getProfessorID());

        db.insert(TB_RESUMOS, null, contentValues);
        db.close();
    }

    public int getCountResumos(){
        int count = 0;

        String countQuerySQL = "SELECT * FROM " + TB_RESUMOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuerySQL, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public List<ResumoVO> getAllResumos(){
        List<ResumoVO> ltResumos = new ArrayList<ResumoVO>();
        String SELECT_QUERY = "SELECT * FROM "+TB_RESUMOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        if(cursor.moveToFirst()){
            do {
                ResumoVO resumoVO = new ResumoVO();
                resumoVO.setId(Integer.parseInt(cursor.getString(0)));
                resumoVO.setTitulo(cursor.getString(1));
                resumoVO.setConteudo(cursor.getString(2));
                resumoVO.setProfessorID(Integer.parseInt(cursor.getString(3)));

                ltResumos.add(resumoVO);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ltResumos;
    }

    public ResumoVO getResumo(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        ResumoVO resumoVO = new ResumoVO();

        Cursor cursor = db.query(TB_RESUMOS,
                new String[]{KEY_ID,TITULO,CONTEUDO,PROFESSOR_ID},
                KEY_ID + " = ? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor != null){
            cursor.moveToFirst();

            resumoVO.setId(Integer.parseInt(cursor.getString(0)));
            resumoVO.setTitulo(cursor.getString(1));
            resumoVO.setConteudo(cursor.getString(2));
            resumoVO.setProfessorID(Integer.parseInt(cursor.getString(3)));
        }

        cursor.close();
        db.close();
        return resumoVO;
    }

    public int updateResumo(ResumoVO resumoVO){
        int qtdRegistrosAtualizados = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTEUDO, resumoVO.getConteudo());

        qtdRegistrosAtualizados = db.update(TB_RESUMOS, contentValues,KEY_ID + " = ? ", new String[]{String.valueOf(resumoVO.getId())});

        db.close();
        return qtdRegistrosAtualizados;
    }

    public void deleteResumo(ResumoVO resumoVO){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_RESUMOS,KEY_ID + " = ? ", new String[]{String.valueOf(resumoVO.getId())});

        db.close();
    }
}
package com.example.pi3apdm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.pi3apdm.model.AvisoVO;


import java.util.ArrayList;
import java.util.List;


public class AvisoDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PI3AMVC_DB2";
    private static final int DATABASE_VERSION = 1;
    private static final String TB_AVISOS = "tb_avisos";
    private static final String KEY_ID = "id";
    private static final String TITULO = "titulo";
    private static final String CONTEUDO = "conteudo";
    private static final String PROFESSOR_ID = "professor_id";



    public AvisoDAO(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_AVISOS = "CREATE TABLE " + TB_AVISOS + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + TITULO + " TEXT, " + CONTEUDO + " TEXT, " + PROFESSOR_ID + " INTEGER)";
        db.execSQL(CREATE_TB_AVISOS);
        //Log.d("EXCEPTION", "CRIOU A TABELA");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_AVISOS);
        onCreate(db);
    }


    public void addAviso(AvisoVO avisoVO){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITULO, avisoVO.getTitulo());
        contentValues.put(CONTEUDO, avisoVO.getConteudo());
        contentValues.put(PROFESSOR_ID, avisoVO.getProfessorID());

        db.insert(TB_AVISOS, null, contentValues);
        db.close();
    }

    public int getCountAvisos(){
        int count = 0;

        String countQuerySQL = "SELECT * FROM " + TB_AVISOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuerySQL, null);
        count = cursor.getCount(); // Obtenha o número de linhas antes de fechar o cursor
        cursor.close();
        db.close();

        // return count
        return count;
    }



    public List<AvisoVO> getAllAvisos(){
        List<AvisoVO> ltAvisos = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM " + TB_AVISOS;
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(SELECT_QUERY, null)) {
            if(cursor.moveToFirst()){
                do {
                    AvisoVO avisoVO = new AvisoVO();
                    avisoVO.setId(cursor.getInt(0));
                    avisoVO.setTitulo(cursor.getString(1));
                    avisoVO.setConteudo(cursor.getString(2));
                    avisoVO.setProfessorID(cursor.getInt(3));

                    ltAvisos.add(avisoVO);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            // Lidar com exceções, se necessário
            Log.e("ERROR", e.getMessage());
        }

        return ltAvisos;
    }


    public AvisoVO getAviso(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        AvisoVO avisoVO = new AvisoVO();

        Cursor cursor = db.query(TB_AVISOS,
                new String[]{KEY_ID,TITULO,CONTEUDO,PROFESSOR_ID},
                KEY_ID + " = ? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor != null){
            cursor.moveToFirst();

            avisoVO.setId(Integer.parseInt(cursor.getString(0)));
            avisoVO.setTitulo(cursor.getString(1));
            avisoVO.setConteudo(cursor.getString(2));
            avisoVO.setProfessorID(Integer.parseInt(cursor.getString(3)));
        }

        cursor.close();
        db.close();
        return avisoVO;
    }

    public int updateAviso(AvisoVO avisoVO){
        int qtdRegistrosAtualizados = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTEUDO, avisoVO.getConteudo());

        qtdRegistrosAtualizados = db.update(TB_AVISOS, contentValues,KEY_ID + " = ? ", new String[]{String.valueOf(avisoVO.getId())});

        db.close();
        return qtdRegistrosAtualizados;
    }

    public void deleteAviso(AvisoVO avisoVO){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_AVISOS,KEY_ID + " = ? ", new String[]{String.valueOf(avisoVO.getId())});

        db.close();
    }
}
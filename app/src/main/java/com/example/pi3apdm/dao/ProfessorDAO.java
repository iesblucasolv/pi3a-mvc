package com.example.pi3apdm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.pi3apdm.model.ProfessorVO;


import java.util.ArrayList;
import java.util.List;


public class ProfessorDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PI3AMVC_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TB_PROFESSORES = "tb_professores";
    private static final String KEY_ID = "id";
    private static final String NOME = "nome";
    private static final String MATRICULA = "matricula";



    public ProfessorDAO(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_PROFESSORES = "CREATE TABLE "+ TB_PROFESSORES + "(" + KEY_ID + "INTEGER PRIMARY KEY," + NOME + " TEXT," + MATRICULA + " TEXT)";
        db.execSQL(CREATE_TB_PROFESSORES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_PROFESSORES);
        onCreate(db);
    }


    public void addProfessor(ProfessorVO professorVO){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NOME, professorVO.getNome());
        contentValues.put(MATRICULA, professorVO.getMatricula());

        db.insert(TB_PROFESSORES, null, contentValues);
        db.close();
    }

    public int getCountProfessores(){
        int count = 0;

        String countQuerySQL = "SELECT * FROM " + TB_PROFESSORES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuerySQL, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public List<ProfessorVO> getAllProfessores(){
        List<ProfessorVO> ltProfessores = new ArrayList<ProfessorVO>();
        String SELECT_QUERY = "SELECT * FROM "+TB_PROFESSORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        if(cursor.moveToFirst()){
            do {
                ProfessorVO professorVO = new ProfessorVO();
                professorVO.setId(Integer.parseInt(cursor.getString(0)));
                professorVO.setNome(cursor.getString(1));
                professorVO.setMatricula(cursor.getString(2));

                ltProfessores.add(professorVO);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ltProfessores;
    }

    public ProfessorVO getProfessor(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        ProfessorVO professorVO = new ProfessorVO();

        Cursor cursor = db.query(TB_PROFESSORES,
                new String[]{KEY_ID,NOME,MATRICULA},
                KEY_ID + " = ? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor != null){
            cursor.moveToFirst();

            professorVO.setId(Integer.parseInt(cursor.getString(0)));
            professorVO.setNome(cursor.getString(1));
            professorVO.setMatricula(cursor.getString(2));
        }

        cursor.close();
        db.close();
        return professorVO;
    }

    public int updateProfessor(ProfessorVO professorVO){
        int qtdRegistrosAtualizados = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME, professorVO.getNome());

        qtdRegistrosAtualizados = db.update(TB_PROFESSORES, contentValues,KEY_ID + " = ? ", new String[]{String.valueOf(professorVO.getId())});

        db.close();
        return qtdRegistrosAtualizados;
    }

    public void deleteProfessor(ProfessorVO professorVO){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_PROFESSORES,KEY_ID + " = ? ", new String[]{String.valueOf(professorVO.getId())});

        db.close();
    }
}
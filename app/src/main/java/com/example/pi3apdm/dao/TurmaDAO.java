package com.example.pi3apdm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.pi3apdm.model.TurmaVO;


import java.util.ArrayList;
import java.util.List;


public class TurmaDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PI3AMVC_DB4";
    private static final int DATABASE_VERSION = 1;
    private static final String TB_TURMAS = "tb_turmas";
    private static final String KEY_ID = "id";
    private static final String CODIGO_TURMA = "codigo_turma";
    private static final String SALA = "sala";
    private static final String DISCIPLINA = "disciplina";
    private static final String HORARIO = "horario";
    private static final String PERIODO = "periodo";
    private static final String DIA_SEMANA = "dia_semana";
    private static final String PROFESSOR = "professor";



    public TurmaDAO(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_TURMAS = "CREATE TABLE " + TB_TURMAS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + PROFESSOR + " TEXT, "
                + DIA_SEMANA + " TEXT, "
                + DISCIPLINA + " TEXT, "
                + PERIODO + " TEXT, "
                + CODIGO_TURMA + " TEXT, "
                + SALA + " TEXT, "
                + HORARIO + " TEXT)";
        db.execSQL(CREATE_TB_TURMAS);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_TURMAS);
        onCreate(db);
    }


    public void addTurma(TurmaVO turmaVO){
        ContentValues contentValues = new ContentValues();

        contentValues.put(CODIGO_TURMA, turmaVO.getCodigo_turma());
        contentValues.put(SALA, turmaVO.getSala());
        contentValues.put(DISCIPLINA, turmaVO.getDisciplina());
        contentValues.put(HORARIO, turmaVO.getHorario());
        contentValues.put(PERIODO, turmaVO.getPeriodo());
        contentValues.put(DIA_SEMANA, turmaVO.getDia_semana());
        contentValues.put(PROFESSOR, turmaVO.getProfessor());

        try(SQLiteDatabase db = this.getWritableDatabase()){
            db.insert(TB_TURMAS, null, contentValues);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
        }
    }

    public int getCountTurmas(){
        int count = 0;

        String countQuerySQL = "SELECT * FROM " + TB_TURMAS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuerySQL, null);
        count = cursor.getCount();
        cursor.close();
        db.close();

        //Log.d("DEBUG", "Tamanho da lista de usuários: " + count);


        // return count
        return count;
    }


    public List<TurmaVO> getAllTurmas(){
        List<TurmaVO> ltTurmas = new ArrayList<TurmaVO>();
        String SELECT_QUERY = "SELECT * FROM "+TB_TURMAS;

        try(SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(SELECT_QUERY, null)){
            if(cursor.moveToFirst()){
                do {
                    TurmaVO turmaVO = new TurmaVO();
                    turmaVO.setId(Integer.parseInt(cursor.getString(0)));
                    turmaVO.setCodigo_turma(cursor.getString(1));
                    turmaVO.setSala(cursor.getString(2));
                    turmaVO.setDisciplina(cursor.getString(3));
                    turmaVO.setHorario(cursor.getString(4));
                    turmaVO.setPeriodo(cursor.getString(5));
                    turmaVO.setDia_semana(cursor.getString(6));
                    turmaVO.setProfessor(cursor.getString(7));

                    ltTurmas.add(turmaVO);
                }while (cursor.moveToNext());
            }
        }  catch (Exception e) {
            // Log e tratamento de exceção
            e.printStackTrace();
        }

        return ltTurmas;
    }

    public TurmaVO getTurma(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        TurmaVO turmaVO = new TurmaVO();

        Cursor cursor = db.query(TB_TURMAS,
                new String[]{KEY_ID,CODIGO_TURMA,SALA,DISCIPLINA,HORARIO,PERIODO,DIA_SEMANA,PROFESSOR},
                KEY_ID + " = ? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor != null){
            cursor.moveToFirst();

            turmaVO.setId(Integer.parseInt(cursor.getString(0)));
            turmaVO.setCodigo_turma(cursor.getString(1));
            turmaVO.setSala(cursor.getString(2));
            turmaVO.setDisciplina(cursor.getString(3));
            turmaVO.setHorario(cursor.getString(4));
            turmaVO.setPeriodo(cursor.getString(5));
            turmaVO.setDia_semana(cursor.getString(6));
            turmaVO.setProfessor(cursor.getString(7));
        }

        cursor.close();
        db.close();
        return turmaVO;
    }

    public int updateTurma(TurmaVO turmaVO){
        int qtdRegistrosAtualizados = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CODIGO_TURMA, turmaVO.getCodigo_turma());
        contentValues.put(SALA, turmaVO.getSala());
        contentValues.put(DISCIPLINA, turmaVO.getDisciplina());
        contentValues.put(HORARIO, turmaVO.getHorario());
        contentValues.put(PERIODO, turmaVO.getPeriodo());
        contentValues.put(DIA_SEMANA, turmaVO.getDia_semana());
        contentValues.put(PROFESSOR, turmaVO.getProfessor());

        qtdRegistrosAtualizados = db.update(TB_TURMAS, contentValues,KEY_ID + " = ? ", new String[]{String.valueOf(turmaVO.getId())});

        db.close();
        return qtdRegistrosAtualizados;
    }

    public void deleteTurma(TurmaVO turmaVO){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_TURMAS,KEY_ID + " = ? ", new String[]{String.valueOf(turmaVO.getId())});

        db.close();
    }
}
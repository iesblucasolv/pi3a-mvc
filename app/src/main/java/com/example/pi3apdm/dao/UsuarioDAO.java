package com.example.pi3apdm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.example.pi3apdm.model.UsuarioVO;


import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PI3AMVC_DB";
    private static final int DATABASE_VERSION = 4;
    private static final String TB_USUARIOS = "tb_usuarios";
    private static final String KEY_ID = "id";
    private static final String NOME = "nome";
    private static final String MATRICULA = "matricula";
    private static final String SENHA = "senha";
    private static final String ISAPROFESSOR = "isaprofessor";



    public UsuarioDAO(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_USUARIOS = "CREATE TABLE " + TB_USUARIOS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                NOME + " TEXT, " +
                MATRICULA + " TEXT, " +
                SENHA + " TEXT, " +
                ISAPROFESSOR + " INTEGER)"; // Adiciona a coluna isaprofessor à definição da tabela
        db.execSQL(CREATE_TB_USUARIOS);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_USUARIOS);
        onCreate(db);
    }


    public void addUsuario(UsuarioVO usuarioVO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME, usuarioVO.getNome());
        contentValues.put(MATRICULA, usuarioVO.getMatricula());
        contentValues.put(SENHA, usuarioVO.getSenha());
        contentValues.put(ISAPROFESSOR, usuarioVO.isProfessor() ? 1 : 0); // Converte booleano para inteiro

        //Log.d("DEBUG", "Tamanho da lista de usuários: " + usuarioVO.getMatricula());
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.insert(TB_USUARIOS, null, contentValues);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
        }
    }

    public int getCountUsuarios(){
        int count = 0;

        String countQuerySQL = "SELECT * FROM " + TB_USUARIOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuerySQL, null);
        count = cursor.getCount();
        cursor.close();
        db.close();

        //Log.d("DEBUG", "Tamanho da lista de usuários: " + count);


        // return count
        return count;
    }


    public List<UsuarioVO> getAllUsuarios() {
        List<UsuarioVO> ltUsuarios = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM " + TB_USUARIOS;

        // Utilizar try-with-resources para garantir que o Cursor e o SQLiteDatabase sejam fechados automaticamente
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(SELECT_QUERY, null)) {

            if (cursor.moveToFirst()) {
                do {
                    UsuarioVO usuarioVO = new UsuarioVO();
                    usuarioVO.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                    usuarioVO.setNome(cursor.getString(cursor.getColumnIndexOrThrow(NOME)));
                    usuarioVO.setMatricula(cursor.getString(cursor.getColumnIndexOrThrow(MATRICULA)));
                    usuarioVO.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(SENHA)));
                    // Considerando que ISAPROFESSOR é armazenado como 0 (false) ou 1 (true)
                    usuarioVO.setProfessor(cursor.getInt(cursor.getColumnIndexOrThrow(ISAPROFESSOR)) == 1);

                    ltUsuarios.add(usuarioVO);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            // Log e tratamento de exceção
            e.printStackTrace();
        }

        return ltUsuarios;
    }


    public UsuarioVO getUsuario(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        UsuarioVO usuarioVO = new UsuarioVO();

        Cursor cursor = db.query(TB_USUARIOS,
                new String[]{KEY_ID,NOME,MATRICULA,SENHA,ISAPROFESSOR},
                KEY_ID + " = ? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor != null){
            cursor.moveToFirst();

            usuarioVO.setId(Integer.parseInt(cursor.getString(0)));
            usuarioVO.setNome(cursor.getString(1));
            usuarioVO.setMatricula(cursor.getString(2));
            usuarioVO.setSenha(cursor.getString(3));
            usuarioVO.setProfessor(Boolean.parseBoolean(cursor.getString(4)));
        }

        cursor.close();
        db.close();
        return usuarioVO;
    }

    public int updateUsuario(UsuarioVO usuarioVO){
        int qtdRegistrosAtualizados = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME, usuarioVO.getNome());

        qtdRegistrosAtualizados = db.update(TB_USUARIOS, contentValues,KEY_ID + " = ? ", new String[]{String.valueOf(usuarioVO.getId())});

        db.close();
        return qtdRegistrosAtualizados;
    }

    public void deleteProfessor(UsuarioVO usuarioVO){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_USUARIOS,KEY_ID + " = ? ", new String[]{String.valueOf(usuarioVO.getId())});

        db.close();
    }
}
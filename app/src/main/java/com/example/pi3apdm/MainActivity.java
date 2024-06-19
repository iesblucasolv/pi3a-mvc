package com.example.pi3apdm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pi3apdm.dao.AvisoDAO;
import com.example.pi3apdm.dao.ResumoDAO;
import com.example.pi3apdm.dao.TurmaDAO;
import com.example.pi3apdm.dao.UsuarioDAO;
import com.example.pi3apdm.model.AvisoVO;
import com.example.pi3apdm.model.ResumoVO;
import com.example.pi3apdm.model.TurmaVO;
import com.example.pi3apdm.model.UsuarioVO;
import com.example.pi3apdm.util.AvisoAdapter;
import com.example.pi3apdm.util.ResumoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //validacoes

    public static boolean containsAlphabet(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean containsSpecialCharacters(String str) {
        Pattern pattern = Pattern.compile("[@#$]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean containsDigit(String str) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean containsSpecialCharacter(String str) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9@#$]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }


    //variaveis

    public boolean logado = false;
    public boolean validacoesPA = false;
    UsuarioVO usuarioLogado = new UsuarioVO();


    public void btnOnClickViewPrimeiroAcesso(View view){
        setContentView(R.layout.primeiro_acesso);
    }

    public void btnOnClickViewVerifyLogin(View view){

        TextView textViewValidacaoLogin = (TextView) findViewById(R.id.textViewValidacaoLogin);

        UsuarioVO usuarioAPP = new UsuarioVO();

        EditText matriculaEditText = (EditText) findViewById(R.id.editTextMatriculaLogin);
        String LoginMatricula = matriculaEditText.getText().toString();
        EditText senhaEditText = (EditText) findViewById(R.id.editTextSenhaLogin);
        String LoginSenha = senhaEditText.getText().toString();

        usuarioAPP.setMatricula(LoginMatricula);
        usuarioAPP.setSenha(LoginSenha);

        List<UsuarioVO> ltUsuarios = new ArrayList<UsuarioVO>();

            UsuarioDAO usuarioBD = new UsuarioDAO(this);
            ltUsuarios = usuarioBD.getAllUsuarios();
            try{

                //Toast.makeText(this, String.valueOf(usuarioBD.getCountUsuarios()), Toast.LENGTH_SHORT).show();

                if(usuarioBD.getCountUsuarios() != 0){
                    for(int i = 0; i<usuarioBD.getCountUsuarios();i++){
                        //Toast.makeText(this, ltUsuarios.get(i).getMatricula(), Toast.LENGTH_SHORT).show();
                        if(Objects.equals(ltUsuarios.get(i).getMatricula(), usuarioAPP.getMatricula()) && Objects.equals(ltUsuarios.get(i).getSenha(), usuarioAPP.getSenha())){
                            logado = true;
                            usuarioLogado = ltUsuarios.get(i);
                            textViewValidacaoLogin.setText("");
                        } else{
                            //TextView textViewValidacaoLogin = (TextView) findViewById(R.id.textViewValidacaoLogin);
                            textViewValidacaoLogin.setText("Usuário não encontrado");
                        }
                    }
                }
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if(logado){
                setContentView(R.layout.menu);
            }


    }

    public void btnOnClickCadastrarPrimeiroAcesso(View view){

        TextView textViewValidacao = (TextView) findViewById(R.id.textViewValidacao);
        //textViewValidacao.setText("");

        UsuarioVO usuarioAPP = new UsuarioVO();

        EditText matriculaEditText = (EditText) findViewById(R.id.editTextMatriculaPrimeiroAcesso);
        String PrimeiroAcessoMatricula = matriculaEditText.getText().toString();
        EditText senhaEditText = (EditText) findViewById(R.id.editTextSenhaPrimeiroAcesso);
        String PrimeiroAcessoSenha = senhaEditText.getText().toString();
        EditText ConfirmarSenhaEditText = (EditText) findViewById(R.id.editTextConfirmarSenhaPrimeiroAcesso);
        String PrimeiroAcessoConfirmarSenha = ConfirmarSenhaEditText.getText().toString();
        EditText NomeEditText = (EditText) findViewById(R.id.editTextNomePrimeiroAcesso);
        String PrimeiroAcessoNome = NomeEditText.getText().toString();

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupIsProfessor);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        String selectedOption = "";
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            selectedOption = selectedRadioButton.getText().toString();
        }
        //Toast.makeText(this, selectedOption, Toast.LENGTH_SHORT).show();

        if(!selectedOption.isEmpty() && !PrimeiroAcessoMatricula.isEmpty() && !PrimeiroAcessoSenha.isEmpty() && !PrimeiroAcessoConfirmarSenha.isEmpty() && !PrimeiroAcessoNome.isEmpty()){
            if(selectedOption.equals("Professor")){
                //Toast.makeText(this, "Professor pa", Toast.LENGTH_SHORT).show();
                usuarioAPP.setProfessor(true);
            } else{
                //Toast.makeText(this, "Aluno pa", Toast.LENGTH_SHORT).show();
                usuarioAPP.setProfessor(false);
            }

            usuarioAPP.setNome(PrimeiroAcessoNome);
            usuarioAPP.setMatricula(PrimeiroAcessoMatricula);

            if((PrimeiroAcessoSenha.length() >= 8)){

                if(containsAlphabet(PrimeiroAcessoSenha)){

                    if(containsSpecialCharacters(PrimeiroAcessoSenha)){

                        if(containsDigit(PrimeiroAcessoSenha)){

                            if(!containsSpecialCharacter(PrimeiroAcessoSenha)){

                                if(PrimeiroAcessoSenha.equals(PrimeiroAcessoConfirmarSenha)){
                                    usuarioAPP.setSenha(PrimeiroAcessoSenha);

                                    if(isNumeric(PrimeiroAcessoMatricula) && (PrimeiroAcessoMatricula.length() == 10)){

                                        validacoesPA = true;

                                    } else {
                                        validacoesPA = false;
                                        textViewValidacao.setText("A matrícula deve ser um código numérico de 10 digitos.");
                                    }
                                } else{
                                    validacoesPA = false;
                                    textViewValidacao.setText("As senhas não conferem.");
                                }
                            } else{
                                validacoesPA = false;
                                textViewValidacao.setText("A senha não pode conter nenhum caractere especial além de '@', '#' ou '$'.");
                            }
                        } else{
                            validacoesPA = false;
                            textViewValidacao.setText("A senha deve conter pelo menos um dígito de 0 a 9.");
                        }
                    } else{
                        validacoesPA = false;
                        textViewValidacao.setText("A senha deve conter pelo menos um caractere especial entre '@', '#' ou '$'.");
                    }
                } else{
                    validacoesPA = false;
                    textViewValidacao.setText("A senha deve conter pelo menos uma letra do alfabeto da língua portuguesa.");
                }
            } else{
                validacoesPA = false;
                textViewValidacao.setText("A senha deve conter 8 caracteres ou mais.");
            }
        } else{
            validacoesPA = false;
            textViewValidacao.setText("Todos os campos são obrigatórios.");
        }

        if(validacoesPA){

            textViewValidacao.setText("");

            //logado = false;
            UsuarioDAO usuarioBD = new UsuarioDAO(this);
            //Toast.makeText(this, usuarioAPP.getMatricula(), Toast.LENGTH_SHORT).show();


            usuarioBD.addUsuario(usuarioAPP);
            setContentView(R.layout.activity_main);
            //Toast.makeText(this, "José: " + PrimeiroAcessoSenha.length(), Toast.LENGTH_SHORT).show();

        }
    }

    public void btnOnClickViewMenu(View view){
        setContentView(R.layout.menu);
    }

    public void btnOnClickViewVerAvisos(View view) {
        if (usuarioLogado.isProfessor()) {
            setContentView(R.layout.ver_avisos_vprofessores);
        } else {
            setContentView(R.layout.ver_avisos_valunos);
        }

        try {
            ListView listViewAvisos;
            AvisoAdapter avisoAdapter;
            AvisoDAO avisoDAO;
            ArrayList<Integer> arrayIds;

            listViewAvisos = findViewById(R.id.listViewAvisos);
            avisoDAO = new AvisoDAO(this);

            // Recupera os avisos do banco de dados
            List<AvisoVO> avisos = avisoDAO.getAllAvisos();

            // Configura o adaptador personalizado
            avisoAdapter = new AvisoAdapter(this, avisos);
            listViewAvisos.setAdapter(avisoAdapter);

            // Preenche o arrayIds com os IDs dos avisos
            arrayIds = new ArrayList<>();
            for (AvisoVO aviso : avisos) {
                arrayIds.add(aviso.getId());
            }

            listViewAvisos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if(usuarioLogado.isProfessor()){
                        AvisoVO avisoExcluir = avisos.get(position);
                        avisoDAO.deleteAviso(avisoExcluir);

                        avisos.remove(position);
                        avisoAdapter.notifyDataSetChanged();
                    }


                    return true;
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("DEBUG", e.getMessage());
        }
    }


    public void btnOnClickViewVerResumos(View view){
        if(usuarioLogado.isProfessor()){
            //Toast.makeText(this, "Professor", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.ver_resumos_vprofessores);
        }else{
            //Toast.makeText(this, "Aluno", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.ver_resumos_valunos);
        }

        try {
            ListView listViewResumos;
            ResumoAdapter resumoAdapter;
            ResumoDAO resumoDAO;
            ArrayList<Integer> arrayIds2;

            listViewResumos = findViewById(R.id.listViewResumos);
            resumoDAO = new ResumoDAO(this);

            List<ResumoVO> resumos = resumoDAO.getAllResumos();

            resumoAdapter = new ResumoAdapter(this, resumos);
            listViewResumos.setAdapter(resumoAdapter);

            arrayIds2 = new ArrayList<>();
            for (ResumoVO resumo : resumos) {
                arrayIds2.add(resumo.getId());
            }

            listViewResumos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    if(usuarioLogado.isProfessor()){
                        ResumoVO resumoExcluir = resumos.get(position);
                        resumoDAO.deleteResumo(resumoExcluir);

                        resumos.remove(position);
                        resumoAdapter.notifyDataSetChanged();

                    }

                    return true;
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("DEBUG", e.getMessage());
        }
    }


    public void btnOnClickViewAdicionarAviso(View view){
        EditText TituloAvisoEditText = (EditText) findViewById(R.id.editTextTituloAviso);
        String TituloAviso = TituloAvisoEditText.getText().toString();
        EditText ConteudoAvisoEditText = (EditText) findViewById(R.id.editTextConteudoAviso);
        String ConteudoAviso = ConteudoAvisoEditText.getText().toString();

        TextView textViewValidacaoAviso = (TextView) findViewById(R.id.textViewValidacaoAviso);


        if(!TituloAviso.isEmpty() && !ConteudoAviso.isEmpty()){
            textViewValidacaoAviso.setText("");
            AvisoVO novoAviso = new AvisoVO();
            novoAviso.setConteudo(ConteudoAviso);
            novoAviso.setTitulo(TituloAviso);
            novoAviso.setProfessorID(usuarioLogado.getId());

            AvisoDAO avisoBD = new AvisoDAO(this);
            avisoBD.addAviso(novoAviso);
            setContentView(R.layout.ver_avisos_vprofessores);
            try {
                ListView listViewAvisos;
                AvisoAdapter avisoAdapter;
                AvisoDAO avisoDAO;
                ArrayList<Integer> arrayIds;

                listViewAvisos = findViewById(R.id.listViewAvisos);
                avisoDAO = new AvisoDAO(this);

                // Recupera os avisos do banco de dados
                List<AvisoVO> avisos = avisoDAO.getAllAvisos();

                // Configura o adaptador personalizado
                avisoAdapter = new AvisoAdapter(this, avisos);
                listViewAvisos.setAdapter(avisoAdapter);

                // Preenche o arrayIds com os IDs dos avisos
                arrayIds = new ArrayList<>();
                for (AvisoVO aviso : avisos) {
                    arrayIds.add(aviso.getId());
                }

                listViewAvisos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if(usuarioLogado.isProfessor()){
                            AvisoVO avisoExcluir = avisos.get(position);
                            avisoDAO.deleteAviso(avisoExcluir);

                            // Remove o item da lista e notifica o adaptador
                            avisos.remove(position);
                            avisoAdapter.notifyDataSetChanged();
                        }


                        return true;
                    }
                });


            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", e.getMessage());
            }
        } else {
            //TextView textViewValidacaoAviso = (TextView) findViewById(R.id.textViewValidacaoAviso);
            textViewValidacaoAviso.setText("Todos os campos devem ser preenchidos");

        }

    }

    public void btnOnClickViewEscreverAviso(View view){
        setContentView(R.layout.escrever_aviso);
    }

    public void btnOnClickViewEscreverResumo(View view){
        setContentView(R.layout.escrever_resumo);
    }

    public void btnOnClickAdicionarResumo(View view){
        EditText TituloResumoEditText = (EditText) findViewById(R.id.editTextTituloResumo);
        String TituloResumo = TituloResumoEditText.getText().toString();
        EditText ConteudoResumoEditText = (EditText) findViewById(R.id.editTextConteudoResumo);
        String ConteudoResumo = ConteudoResumoEditText.getText().toString();

        TextView textViewValidacaoResumo = (TextView) findViewById(R.id.textViewValidacaoResumo);


        if(!TituloResumo.isEmpty() && !ConteudoResumo.isEmpty()){
            ResumoVO novoResumo = new ResumoVO();
            novoResumo.setConteudo(ConteudoResumo);
            novoResumo.setTitulo(TituloResumo);
            novoResumo.setProfessorID(usuarioLogado.getId());

            ResumoDAO resumoBD = new ResumoDAO(this);
            resumoBD.addResumo(novoResumo);
            setContentView(R.layout.ver_resumos_vprofessores);
            try {
                ListView listViewResumos;
                ResumoAdapter resumoAdapter;
                ResumoDAO resumoDAO;
                ArrayList<Integer> arrayIds2;

                listViewResumos = findViewById(R.id.listViewResumos);
                resumoDAO = new ResumoDAO(this);

                List<ResumoVO> resumos = resumoDAO.getAllResumos();

                resumoAdapter = new ResumoAdapter(this, resumos);
                listViewResumos.setAdapter(resumoAdapter);

                arrayIds2 = new ArrayList<>();
                for (ResumoVO resumo : resumos) {
                    arrayIds2.add(resumo.getId());
                }

                listViewResumos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        if(usuarioLogado.isProfessor()){
                            ResumoVO resumoExcluir = resumos.get(position);
                            resumoDAO.deleteResumo(resumoExcluir);

                            resumos.remove(position);
                            resumoAdapter.notifyDataSetChanged();

                        }

                        return true;
                    }
                });
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", e.getMessage());
            }
        } else{
            textViewValidacaoResumo.setText("Todos os campos devem ser preenchidos");
        }
    }

    public void btnOnClickViewLogin(View view){
        setContentView(R.layout.activity_main);
    }

    public void btnOnClickViewEditarTurma(View view){
        setContentView(R.layout.editar_turma);
        TurmaVO turma = new TurmaVO();
        TurmaDAO turmaBD = new TurmaDAO(this);

        EditText editTextCodigoTurma = (EditText) findViewById(R.id.editTextCodigoTurma);
        editTextCodigoTurma.setText(turmaBD.getTurma(1).getCodigo_turma());
        //String TituloAviso = TituloAvisoEditText.getText().toString();

        EditText editTextSala = (EditText) findViewById(R.id.editTextSala);
        editTextSala.setText(turmaBD.getTurma(1).getSala());

        EditText editTextDisciplina = (EditText) findViewById(R.id.editTextDisciplina);
        editTextDisciplina.setText(turmaBD.getTurma(1).getDisciplina());

        EditText editTextHorario = (EditText) findViewById(R.id.editTextHorario);
        editTextHorario.setText(turmaBD.getTurma(1).getHorario());

        EditText editTextPeriodo = (EditText) findViewById(R.id.editTextPeriodo);
        editTextPeriodo.setText(turmaBD.getTurma(1).getPeriodo());

        EditText editTextDiaSemana = (EditText) findViewById(R.id.editTextDiaSemana);
        editTextDiaSemana.setText(turmaBD.getTurma(1).getDia_semana());

        EditText editTextProfessorTurma = (EditText) findViewById(R.id.editTextProfessorTurma);
        editTextProfessorTurma.setText(turmaBD.getTurma(1).getProfessor());

    }

    public void btnOnClickViewVerTurma(View view){
        if(usuarioLogado.isProfessor()){
            //Toast.makeText(this, "Professor", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.ver_informacoes_turma_vprofessores);
        }else{
            //Toast.makeText(this, "Aluno", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.ver_informacoes_turma_valunos);
        }

        //TextView TituloPag = (TextView) findViewById(R.id.textView26);
        //String TituloPagina = TituloPag.getText().toString();
        //TituloPag.setText("Texto Alterado");

        TurmaVO turma = new TurmaVO();
        TurmaDAO turmaBD = new TurmaDAO(this);

        /*
        turma.setCodigo_turma("cconm3a");
        turma.setSala("JB1");
        turma.setDisciplina("Projeto Integrador 3A");
        turma.setHorario("18:00 - 19:15");
        turma.setPeriodo("Noturno");
        turma.setDia_semana("Quarta-feira");
        turma.setProfessor("Bruno marcos");
        */

        //turmaBD.addTurma(turma);

        //Toast.makeText(this, turmaBD.getTurma(1).getDisciplina(), Toast.LENGTH_SHORT).show();

        TextView TextViewCodigoTurma = (TextView) findViewById(R.id.textViewCodigoTurma);
        TextViewCodigoTurma.setText(turmaBD.getTurma(1).getCodigo_turma());

        TextView TextViewSala = (TextView) findViewById(R.id.textViewSala);
        TextViewSala.setText(turmaBD.getTurma(1).getSala());

        TextView TextViewDisciplina = (TextView) findViewById(R.id.textViewDisciplina);
        TextViewDisciplina.setText(turmaBD.getTurma(1).getDisciplina());

        TextView TextViewHorario = (TextView) findViewById(R.id.textViewHorario);
        TextViewHorario.setText(turmaBD.getTurma(1).getHorario());

        TextView TextViewPeriodo = (TextView) findViewById(R.id.textViewPeriodo);
        TextViewPeriodo.setText(turmaBD.getTurma(1).getPeriodo());

        TextView TextViewDiaSemana = (TextView) findViewById(R.id.textViewDiaSemana);
        TextViewDiaSemana.setText(turmaBD.getTurma(1).getDia_semana());

        TextView TextViewProfessorTurma = (TextView) findViewById(R.id.textViewProfessorTurma);
        TextViewProfessorTurma.setText(turmaBD.getTurma(1).getProfessor());

    }

    public void btnOnClickSalvarEdicoesTurma(View view){

        TurmaVO turma = new TurmaVO();
        TurmaDAO turmaBD = new TurmaDAO(this);

        EditText editTextCodigoTurma = (EditText) findViewById(R.id.editTextCodigoTurma);
        String CodigoTurma = editTextCodigoTurma.getText().toString();

        EditText editTextSala = (EditText) findViewById(R.id.editTextSala);
        String sala = editTextSala.getText().toString();

        EditText editTextDisciplina = (EditText) findViewById(R.id.editTextDisciplina);
        String disciplina = editTextDisciplina.getText().toString();

        EditText editTextHorario = (EditText) findViewById(R.id.editTextHorario);
        String horario = editTextHorario.getText().toString();

        EditText editTextPeriodo = (EditText) findViewById(R.id.editTextPeriodo);
        String periodo = editTextPeriodo.getText().toString();

        EditText editTextDiaSemana = (EditText) findViewById(R.id.editTextDiaSemana);
        String DiaSemana = editTextDiaSemana.getText().toString();

        EditText editTextProfessorTurma = (EditText) findViewById(R.id.editTextProfessorTurma);
        String professorTurma = editTextProfessorTurma.getText().toString();

        turma.setCodigo_turma(CodigoTurma);
        turma.setSala(sala);
        turma.setDisciplina(disciplina);
        turma.setHorario(horario);
        turma.setPeriodo(periodo);
        turma.setDia_semana(DiaSemana);
        turma.setProfessor(professorTurma);
        turma.setId(1);

        //Toast.makeText(this, turma.getProfessor(), Toast.LENGTH_SHORT).show();

        turmaBD.updateTurma(turma);

        //Toast.makeText(this, CodigoTurma, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.ver_informacoes_turma_vprofessores);

        TurmaVO turma2 = new TurmaVO();
        TurmaDAO turmaBD2 = new TurmaDAO(this);

        TextView TextViewCodigoTurma = (TextView) findViewById(R.id.textViewCodigoTurma);
        TextViewCodigoTurma.setText(turmaBD2.getTurma(1).getCodigo_turma());

        TextView TextViewSala = (TextView) findViewById(R.id.textViewSala);
        TextViewSala.setText(turmaBD2.getTurma(1).getSala());

        TextView TextViewDisciplina = (TextView) findViewById(R.id.textViewDisciplina);
        TextViewDisciplina.setText(turmaBD2.getTurma(1).getDisciplina());

        TextView TextViewHorario = (TextView) findViewById(R.id.textViewHorario);
        TextViewHorario.setText(turmaBD2.getTurma(1).getHorario());

        TextView TextViewPeriodo = (TextView) findViewById(R.id.textViewPeriodo);
        TextViewPeriodo.setText(turmaBD2.getTurma(1).getPeriodo());

        TextView TextViewDiaSemana = (TextView) findViewById(R.id.textViewDiaSemana);
        TextViewDiaSemana.setText(turmaBD2.getTurma(1).getDia_semana());

        TextView TextViewProfessorTurma = (TextView) findViewById(R.id.textViewProfessorTurma);
        TextViewProfessorTurma.setText(turmaBD2.getTurma(1).getProfessor());
    }
}
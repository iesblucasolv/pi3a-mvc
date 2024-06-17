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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pi3apdm.dao.AvisoDAO;
import com.example.pi3apdm.dao.UsuarioDAO;
import com.example.pi3apdm.model.AvisoVO;
import com.example.pi3apdm.model.UsuarioVO;
import com.example.pi3apdm.util.AvisoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    //variaveis

    public boolean logado = false;
    UsuarioVO usuarioLogado = new UsuarioVO();


    public void btnOnClickViewPrimeiroAcesso(View view){
        setContentView(R.layout.primeiro_acesso);
    }

    public void btnOnClickViewVerifyLogin(View view){

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

            if(PrimeiroAcessoSenha.equals(PrimeiroAcessoConfirmarSenha)){
                usuarioAPP.setSenha(PrimeiroAcessoSenha);

                //logado = false;
                UsuarioDAO usuarioBD = new UsuarioDAO(this);
                //Toast.makeText(this, usuarioAPP.getMatricula(), Toast.LENGTH_SHORT).show();
                usuarioBD.addUsuario(usuarioAPP);
                setContentView(R.layout.activity_main);
            }

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
                    AvisoVO avisoExcluir = avisos.get(position);
                    avisoDAO.deleteAviso(avisoExcluir);

                    // Remove o item da lista e notifica o adaptador
                    avisos.remove(position);
                    avisoAdapter.notifyDataSetChanged();

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
    }


    public void btnOnClickViewAdicionarAviso(View view){
        EditText TituloAvisoEditText = (EditText) findViewById(R.id.editTextTituloAviso);
        String TituloAviso = TituloAvisoEditText.getText().toString();
        EditText ConteudoAvisoEditText = (EditText) findViewById(R.id.editTextConteudoAviso);
        String ConteudoAviso = ConteudoAvisoEditText.getText().toString();


        if(!TituloAviso.isEmpty() && !ConteudoAviso.isEmpty()){
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
                        AvisoVO avisoExcluir = avisos.get(position);
                        avisoDAO.deleteAviso(avisoExcluir);

                        // Remove o item da lista e notifica o adaptador
                        avisos.remove(position);
                        avisoAdapter.notifyDataSetChanged();

                        return true;
                    }
                });


            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", e.getMessage());
            }
        }

    }

    public void btnOnClickViewEscreverAviso(View view){
        setContentView(R.layout.escrever_aviso);
    }

    public void btnOnClickViewEscreverResumo(View view){
        setContentView(R.layout.escrever_resumo);
    }

    public void btnOnClickViewLogin(View view){
        setContentView(R.layout.activity_main);
    }
}
package com.example.timeismoney.Activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timeismoney.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {

    private EditText edt_nome_registro;
    private EditText edt_email_registro;
    private EditText edt_senha_registro;
    private EditText edt_conf_senha_registro;
    private Button btn_entrar_registro;
    private Button btn_registrar_registro;
    private FirebaseAuth mAuth;
    private CheckBox ckb_mostra_senha_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        edt_nome_registro = findViewById(R.id.et_nome_registro);
        edt_email_registro = findViewById(R.id.et_email_registro);
        edt_senha_registro = findViewById(R.id.et_senha_registro);
        edt_conf_senha_registro = findViewById(R.id.et_confir_senha_registro);
        ckb_mostra_senha_registro = findViewById(R.id.mostra_senha_registro);
        btn_entrar_registro = findViewById(R.id.bt_entrar_registro);
        btn_registrar_registro = findViewById(R.id.bt_registrar_registro);

        ckb_mostra_senha_registro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edt_senha_registro.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_conf_senha_registro.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    edt_senha_registro.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_conf_senha_registro.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_registrar_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerNome = edt_nome_registro.getText().toString();
                String registerEmail = edt_email_registro.getText().toString();
                String registerSenha = edt_senha_registro.getText().toString();
                String registerConfiSenha = edt_conf_senha_registro.getText().toString();
                if (!TextUtils.isEmpty(registerNome) || !TextUtils.isEmpty(registerEmail) || !TextUtils.isEmpty(registerSenha) || !TextUtils.isEmpty(registerConfiSenha)){
                    if (registerSenha.equals(registerConfiSenha)){
                        mAuth.createUserWithEmailAndPassword(registerEmail,registerSenha)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    abrirTelaLogin();
                                }else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(Registro.this,""+error,Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }else{
                        Toast.makeText(Registro.this,"A senha deve ser a mesma em ambos os campos !",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_entrar_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaLogin();
            }
        });


    }


    private void abrirTelaLogin() {
        Intent intent = new Intent(Registro.this,Login.class);
        startActivity(intent);
        finish();

    }
}
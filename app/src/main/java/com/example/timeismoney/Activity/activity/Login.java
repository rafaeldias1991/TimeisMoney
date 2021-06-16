package com.example.timeismoney.Activity.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



import com.example.timeismoney.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText et_email;
    private EditText et_senha;
    private Button btn_login;
    private Button btn_registro;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private ProgressBar progressBar;
    private CheckBox ckb_senha;
    private Button btn_entrar_google;
    private LoginButton mLogin_btn_face;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        inicializarComponentes();
        inicializarFirebaseCallback();
        clickButton();


        et_email = findViewById(R.id.et_email);
        et_senha = findViewById(R.id.et_senha);
        btn_login = findViewById(R.id.bt_login);
        btn_registro = findViewById(R.id.br_registrar);
        progressBar = findViewById(R.id.lg_progressBar);
        ckb_senha = findViewById(R.id.mostra_senha);
        btn_entrar_google = findViewById(R.id.bt_entrar_google);
        mLogin_btn_face = (LoginButton) findViewById(R.id.bt_entrar_facebook);





        mLogin_btn_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // captura oq o usuario digitou
                String loginEmail = et_email.getText().toString();
                String loginSenha = et_senha.getText().toString();

                //verificação se os campos foram digitados
                if (!TextUtils.isEmpty(loginEmail) || !TextUtils.isEmpty(loginSenha)) {
                    // deixa visivel a barra de progresso
                    progressBar.setVisibility(View.VISIBLE);
                    // consulta o firebase com os dados que o usuario digitou
                    mAuth.signInWithEmailAndPassword(loginEmail,loginSenha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        abrirTelaPrincipal();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(Login.this, "" + error, Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }
            }
        });


        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Registro.class);
                startActivity(intent);
                finish();
            }
        });

        // Mostra a senha que foi digitada ao usuario
        ckb_senha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


    }


    private void clickButton() {
        mLogin_btn_face.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                firebaseLogin(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                alert("Operação cancelada");

            }

            @Override
            public void onError(FacebookException error) {
                alert("Erro no login com Facebook");

            }
        });
    }

    private void firebaseLogin(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            abrirTelaPrincipal();
                        }else {
                            alert("erro de autenticação com Firebase");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void acessaGrafico(View view) {
        Intent intent = new Intent(this, AcessoLivre.class);
        startActivity(intent);
    }

    private void inicializarComponentes(){
        mLogin_btn_face = (LoginButton) findViewById(R.id.bt_entrar_facebook);
        mLogin_btn_face.setReadPermissions("email","public_profile");
    }
    private void inicializarFirebaseCallback(){
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
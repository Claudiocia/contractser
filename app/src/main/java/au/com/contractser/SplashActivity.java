package au.com.contractser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import au.com.contractser.databases.BDPlunge;
import au.com.contractser.databases.BDUser;

public class SplashActivity extends AppCompatActivity {
    ProgressBar mProgressBar;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        exibirProgress(true);


        //Criar o BD a partir do arquivo já existente
        BDPlunge bd = new BDPlunge(this);
        try {
            bd.criarDataBase();
        }catch (IOException e){
            e.printStackTrace();
        }

        BDUser bdUser = new BDUser(this);
        String arg = "admin";
        boolean regUser = bdUser.buscaPorId(arg);
        if (regUser == false){
            //Intent it = new Intent ( Splash.this, CadastrarUser.class);
            Toast.makeText(SplashActivity.this, "Não existe usuário cadastrado. Solicite correção", Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(SplashActivity.this, "Aproveite para visualizar o lay out", Toast.LENGTH_LONG).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent it = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }, 200);
        }

    }

    private void exibirProgress(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }



}

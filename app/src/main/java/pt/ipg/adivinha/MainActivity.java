package pt.ipg.adivinha;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random = new Random();
    private int numeroAdivinhar;
    private int tentativas;

    private int totalTentativas=0;
    private int jogos=0;
    private int vitorias = 0;
    private boolean jogar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       novoJogo();
    }

    private void novoJogo() {
        numeroAdivinhar = random.nextInt(10) + 1; //da um numero entre 0-9 por isso é que somo 1
        tentativas = 0;
        jogos++;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void adivinha(View view){

        EditText editTextPalpite = (EditText) findViewById(R.id.editTextPalpite);
        String s = editTextPalpite.getText().toString();

        if(s.isEmpty()){
            editTextPalpite.setError(getString(R.string.palpite));
            return;
        }

        int numero;
        try {
            numero = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            editTextPalpite.setError(getString(R.string.numero_invalido));
            editTextPalpite.requestFocus();
            return;
        }
    if(numero<1 || numero>10){

            editTextPalpite.setError(getString(R.string.numero_invalido));
            return;
    }

        if(numero == numeroAdivinhar){
            acertou();

        }else {
            String mensagem;

            if (numero < numeroAdivinhar) {
                mensagem = getString(R.string.numero_maior);
                //Snackbar.make(view, getString(R.string.numero_maior) +" "+numero + ". " + getString(R.string.tente_novamente), Snackbar.LENGTH_INDEFINITE).show();

            } else {
                mensagem = getString(R.string.numero_menor);
                //Snackbar.make(view, getString(R.string.numero_menor) +" "+ numero + ". " + getString(R.string.tente_novamente), Snackbar.LENGTH_INDEFINITE).show();

            }
            mensagem += " "+numero + ". " + getString(R.string.tente_novamente);
            Snackbar.make(view, mensagem, Snackbar.LENGTH_INDEFINITE).show(); //length indefinite -> fica a mostra para sempre
            Helpers.escondeTeclado(this);
        }

    }

    private void acertou() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.parabens);
        dialogBuilder.setMessage(R.string.jogar_novamente);
        dialogBuilder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                novoJogo();
            }
        });
        dialogBuilder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();// sair da aplicação
                return;
            }
        });
        dialogBuilder.show();
    }
}

package fr.pisano.meteorest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import fr.pisano.meteorest.repository.VilleRepository;

public class MainActivity extends AppCompatActivity {
    VilleRepository vllRepo;
    String laVille;
    String URL_METEO = "http://api.openweathermap.org/data/2.5/weather?q=";
    String KEY = "&appid=a0e1263cc4891221a8a70c9dc73378d4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Creation de l’objet Ville lié a l’utilisateur avec passage du contexte
        vllRepo = new VilleRepository(getApplicationContext());
        // Verification de l'existence d'une ville
        if (vllRepo.isVilleConfigured()) {
            laVille = vllRepo.getVille();
            // Toast est un message popup
            Toast.makeText(this, laVille, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Pas de ville déjà demandée...", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDemande();
            }
        });
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
            vllRepo.unsetVille();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDemande() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final EditText taskEditText = new EditText(this);
        builder.setMessage("Nom de la ville").setTitle("Demande météo").setView(taskEditText).setNegativeButton("Abandon", null);
        builder.setPositiveButton("Recherche", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                laVille = taskEditText.getText().toString();
                vllRepo.setVille(laVille);

                // Envoi avec l’URL complète demandée par l’API
                sendRequest(URL_METEO + laVille + KEY);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendRequest(String URL) {
        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", "" + response);
                // Lancement de la nouvelle page avec transmission des résultats
                Intent intentMeteo = new Intent(MainActivity.this, MeteoVille.class);
                intentMeteo.putExtra("Reponse", response);
                startActivity(intentMeteo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                getParent().finish();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}

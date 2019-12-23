package fr.pisano.meteorest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MeteoVille extends AppCompatActivity {

    private TextView tvReponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo_ville);
        String reponse = this.getIntent().getExtras().getString("Reponse");
        tvReponse = (TextView) this.findViewById(R.id.tvReponse);
        tvReponse.setText(reponse);
    }
}

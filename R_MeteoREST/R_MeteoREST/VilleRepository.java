package fr.vinsio.meteorest.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.preference.PreferenceManager;

// Classe intermediaire la ville déposée et choisie par l'utilisateur et l'enregistrement Android
public class VilleRepository extends Repository {

	// Constructeur
	public VilleRepository(Context context) {
		super(context);
	}

	// Enregistre la ville dans les SharedPreferences
	public void setVille(String ville) {
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
		Editor prefsEditor = appSharedPrefs.edit();

		prefsEditor.putString("VILLE",ville);	
		prefsEditor.commit();
	}

	// Supprime la ville
	public void unsetVille() {
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(Repository.context);
		Editor prefsEditor = appSharedPrefs.edit();

		prefsEditor.remove("VILLE");
		prefsEditor.commit();
	}

	// Indique si une ville est configurée ou non
	public boolean isVilleConfigured() {
		VilleRepository vllRepo = new VilleRepository(Repository.context);
		String laVille = vllRepo.getVille();

		if (laVille.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	// Recupere la ville de l'utilisateur
	public String getVille()	{
		SharedPreferences app = PreferenceManager.getDefaultSharedPreferences(Repository.context);
		return app.getString("VILLE", "");
	}	
}

package com.bouami.infoetb2014.app;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mohammed on 08/03/14.
 */
public class ChargerDonnees {
    public InputStream recuperer(String url, String method) throws IOException {
        InputStream in = null;

        try {
            URL urlString = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlString.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.connect();
            in = conn.getInputStream();
        } catch (IOException e){
            Log.e("Erreur de Chargement", "Erreur de chargement des données " + e.toString());
        }
        return in;
    }
}

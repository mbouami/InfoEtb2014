package com.bouami.infoetb2014.app;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammed on 08/03/14.
 */
public class JSONParser {
    public List<Ville> parse(String url, String method) throws IOException {
        JsonReader reader = null;
        InputStream in = null;
        ChargerDonnees donnees = new ChargerDonnees();
        in = donnees.recuperer(url, method);
        reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readDataArray(reader);
        }
        finally {
            reader.close();
        }
    }

    public List<Ville> readDataArray(JsonReader reader)  throws IOException {
        List<Ville> result = new ArrayList<Ville>();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("resultats") && reader.peek() != JsonToken.NULL) {
                result = readEntreesArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return result;
    }
    private List<Ville> readEntreesArray(JsonReader reader)  throws IOException {
        List<Ville> entree = new ArrayList<Ville>();
        reader.beginArray();
        while (reader.hasNext()) {
            entree.add(readVille(reader));
        }
        reader.endArray();
        return entree;
    }

    private Ville readVille(JsonReader reader)  throws IOException {
        String id = null;
        String nom = null;
        String district = null;
        String depart = null;
        List<Etab> etablissements = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            } else if (name.equals("nom")) {
                nom = reader.nextString();
            } else if (name.equals("district")) {
                district = reader.nextString();
            } else if (name.equals("depart")) {
                depart = reader.nextString();
            } else if (name.equals("etablissements") && reader.peek() != JsonToken.NULL) {
                etablissements = readEtabArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Ville(id,nom,district,depart,etablissements);

    }

    private List<Etab> readEtabArray(JsonReader reader)  throws IOException {
        List<Etab> entree = new ArrayList<Etab>();
        reader.beginArray();
        while (reader.hasNext()) {
            entree.add(readEtab(reader));
        }
        reader.endArray();
        return entree;
    }

    private Etab readEtab(JsonReader reader)  throws IOException {
        // TODO Auto-generated method stub
        String id = null, nom = null, classement = null,
                cat = null, type = null, rne = null,
                tel = null, fax = null, web = null,
                email = null, adresse = null, cp = null, ville = null;
        List<Personnel> lepersonnel = null;
        List<Animateurs> animateurs = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            } else if (name.equals("nom")) {
                nom = reader.nextString();
            } else if (name.equals("classement")) {
                classement = reader.nextString();
            } else if (name.equals("cat")) {
                cat = reader.nextString();
            } else if (name.equals("type")) {
                type = reader.nextString();
            } else if (name.equals("rne")) {
                rne = reader.nextString();
            } else if (name.equals("tel")) {
                tel = reader.nextString();
            } else if (name.equals("fax") && reader.peek() != JsonToken.NULL) {
                fax = reader.nextString();
            } else if (name.equals("web") && reader.peek() != JsonToken.NULL) {
                web = reader.nextString();
            } else if (name.equals("email")) {
                email = reader.nextString();
            } else if (name.equals("adresse")) {
                adresse = reader.nextString();
            } else if (name.equals("cp")) {
                cp = reader.nextString();
            } else if (name.equals("nomville")) {
                ville = reader.nextString();
            } else if (name.equals("animateurs")) {
                animateurs = readAnimateursArray(reader);
            } else if (name.equals("personnel") && reader.peek() != JsonToken.NULL) {
                lepersonnel = readPersonnelArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Etab(id,nom,classement,cat,type,rne,tel,fax,web,email,lepersonnel,adresse,cp,ville,animateurs);
    }

    private List<Personnel> readPersonnelArray(JsonReader reader) throws IOException {
        List<Personnel> entree = new ArrayList<Personnel>();
        reader.beginArray();
        while (reader.hasNext()) {
            entree.add(readPersonnel(reader));
        }
        reader.endArray();
        return entree;
    }

    private List<Animateurs> readAnimateursArray(JsonReader reader) throws IOException {
        List<Animateurs> entree = new ArrayList<Animateurs>();
        reader.beginArray();
        while (reader.hasNext()) {
            entree.add(readAnimateur(reader));
        }
        reader.endArray();
        return entree;
    }

    private Personnel readPersonnel(JsonReader reader) throws IOException {
        String id = null;
        String statut = null;
        String nom = null;
        String tel = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            } else if (name.equals("statut")) {
                statut = reader.nextString();
            } else if (name.equals("nom")) {
                nom = reader.nextString();
            } else if (name.equals("tel")) {
                tel = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Personnel(id,statut,nom,tel);

    }

    private Animateurs readAnimateur(JsonReader reader) throws IOException {
        String id = null;
        String animateur = null;
        String tel = null;
        String email = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            } else if (name.equals("animateur")) {
                animateur = reader.nextString();
            } else if (name.equals("tel")) {
                tel = reader.nextString();
            } else if (name.equals("email")) {
                email = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Animateurs(id,animateur,tel,email);

    }
}

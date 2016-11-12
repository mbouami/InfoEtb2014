package com.bouami.infoetb2014.app;

/**
 * Created by Mohammed on 08/03/14.
 */
public class Personnel {
    public final String id;
    public final String statut;
    public final String nom;
    public final String tel;

    public Personnel(String id,String statut,String nom,String tel) {
        this.id = id;
        this.statut = statut;
        this.nom = nom;
        this.tel = tel;
    }

    public String getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }
    public String getStatut() {
        return this.statut;
    }

    public String getTel() {
        return this.tel;
    }
}

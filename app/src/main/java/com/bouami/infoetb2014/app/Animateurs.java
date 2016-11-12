package com.bouami.infoetb2014.app;

/**
 * Created by Mohammed on 08/03/14.
 */
public class Animateurs {
    public final String id;
    public final String animateur;
    public final String tel;
    public final String email;

    public Animateurs(String id,String animateur,String tel,String email) {
        this.id = id;
        this.animateur = animateur;
        this.tel = tel;
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public String getNom() {
        return this.animateur;
    }

    public String getTel() {
        return this.tel;
    }

    public String getEmail() {
        return this.email;
    }
}

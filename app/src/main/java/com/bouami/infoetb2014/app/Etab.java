package com.bouami.infoetb2014.app;

import java.util.List;

/**
 * Created by Mohammed on 08/03/14.
 */
public class Etab {
    public final String id;
    public final String nom;
    public final String classement;
    public final String cat;
    public final String type;
    public final String rne;
    public final String tel;
    public final String fax;
    public final String web;
    public final String email;
    public final String adresse;
    public final String cp;
    public final String ville;
    public final List<Personnel> personnel;
    public final List<Animateurs> animateurs;

    public Etab(String id,String nom,String classement,String cat,String type,String rne,String tel,String fax,String web,String email,List<Personnel> personnel,String adresse,String cp,String ville,List<Animateurs> animateurs){
        this.id = id;
        this.nom = nom;
//		   this.animateur = animateur;
        this.cat = cat;
        this.classement = classement;
        this.email = email;
        this.personnel = personnel;
        this.animateurs = animateurs;
        this.rne = rne;
        this.tel = tel;
        this.fax = fax;
        this.web = web;
        this.type = type;
        this.adresse= adresse;
        this.cp = cp;
        this.ville = ville;
    }

    public String getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getTel() {
        return this.tel;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFax() {
        return this.fax;
    }

    public String getType() {
        return this.type;
    }

    public String getRne() {
        return this.rne;
    }

    public String getWeb() {
        return this.web;
    }

    public String getAdresse() {
        return String.format("%s - %s %s",this.adresse,this.cp,this.ville);
    }

    public String getMapAdresse() {
        return String.format("%s,+%s+%s,France",this.adresse,this.cp,this.ville);
    }

    public List<Personnel> getPersonnels() {
        return this.personnel;
    }

    public List<Animateurs> getAnimateurs() {
        return this.animateurs;
    }

}

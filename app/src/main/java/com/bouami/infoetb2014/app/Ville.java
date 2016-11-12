package com.bouami.infoetb2014.app;

import java.util.List;

/**
 * Created by Mohammed on 08/03/14.
 */
public class Ville {
    public final String id;
    public final String nom;
    public final String district;
    public  final String depart;
    public final List<Etab> etablissements;

    public Ville(String id,String nom,String district, String depart, List<Etab> etablissements){
        this.id = id;
        this.nom = nom;
        this.district = district;
        this.depart = depart;
        this.etablissements = etablissements;
    }

    public String getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getDistrict() {
        return this.district;
    }

    public String getDepart() {
        return this.depart;
    }

    public String getInfosVilles() {
        return String.format("%s %s-%s",this.nom,this.depart,this.district);
    }

    public List<Etab> getEtablissements() {
        return this.etablissements;
    }
}

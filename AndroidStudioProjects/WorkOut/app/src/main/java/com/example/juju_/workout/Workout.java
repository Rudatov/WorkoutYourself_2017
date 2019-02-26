package com.example.juju_.workout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by juju_ on 19/08/2016.
 */
public class Workout {
    public Timer timer;
    public int nb_etapes;
    public String nom;
    public ArrayList<Etape> list_etape;

    public Workout(int nb_etapes,String nom){
        this.nb_etapes=nb_etapes;
        this.nom=nom;
        list_etape=new ArrayList<Etape>(nb_etapes);
        timer=new Timer();
    }

    public String workout2XML(){
        String toXML="<workout name="+nom+" no_etape="+nb_etapes+">";
        for ( Etape etape : list_etape){
            toXML+=etape.etape2XML();
        }
        toXML+="</workout>";
        return toXML;
    }

    public void addEtape(Etape etape){
        list_etape.add(etape);
    }

    public String toString(){
        return nom;
    }

    public int getNbEtape(){
        return nb_etapes;
    }

    public ArrayList<Etape> getList_etape(){
        return list_etape;
    }
}

package com.example.juju_.workout;

/**
 * Created by juju_ on 19/08/2016.
 */
public class Etape {
    public String titre;
    public String desc="";
    public int temps;
    public int pause;

    public Etape(String titre,int temps,int pause){
        this.titre=titre;
        this.pause=pause;
        this.temps=temps;
    }

    public Etape(String titre,String desc,int temps,int pause){
        this.titre=titre;
        this.pause=pause;
        this.temps=temps;
        this.desc=desc;
    }

    public String etape2XML(){
        String toXML="<etape titre="+titre+" desc="+desc+" temps="+temps+" pause="+pause+"></etape>";
        return toXML;
    }
    public int getTemps(){
        return temps;
    }

    public int getPause() {
        return pause;
    }
    public String getDesc() {
        return desc;
    }
    public String getTitre(){
        return titre;
    }


}

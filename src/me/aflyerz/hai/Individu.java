package me.aflyerz.hai;

import java.util.ArrayList;
import java.util.List;

public class Individu implements Comparable<Individu>{

    private final int[] nbPremier = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,97};
    private ArrayList<Character> actionsJouees;
    private double winRate;

    public Individu(ArrayList<Character> actionsJouees,double winRate){

        this.actionsJouees = actionsJouees;
        this.winRate = winRate;

    }

    public String toString(){ return "Actions jouees : "+actionsJouees+" Winrate : "+this.winRate; };

    public int getNbPart(){
        return actionsJouees.size()/getNbPremier();
    }

    private int getNbPremier(){
        for(int i = 0; i < nbPremier.length; i++) if(actionsJouees.size()%nbPremier[i] == 0) return nbPremier[i];
        return -1;
    }

    public List<Character> getGenePart(int part){
        return actionsJouees.subList((getNbPremier()*part),(getNbPremier()*part+getNbPremier()));
    }

    public int compareTo(Individu b){ return Double.compare(this.winRate, b.winRate);}
    public double getWinRate(){ return this.winRate; }
    public ArrayList<Character> getActionsJouees(){ return actionsJouees;}


}

package me.aflyerz.hai;

import java.util.ArrayList;

public interface Jeu extends Cloneable{

    public void jouer(char action);
    public ArrayList<Character> actionsJouable();
    public boolean getPartieGagner();
    public String getNomJoueurCourant();
    public boolean getPartieTerminer();
    public Jeu clone();

}

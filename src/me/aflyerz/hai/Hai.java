package me.aflyerz.hai;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Hai extends Thread{

    private String nomJoueur;
    private ArrayList<Individu> alIndividu;
    private int nbIndividu,nbTentative,nbChild;
    private Jeu jeu;

    public Hai(Jeu jeu, String nomJoueur, int nbTentative, int nbIndividu, int nbChild){

        this.jeu = jeu;
        this.nomJoueur = nomJoueur;
        this.nbIndividu = nbIndividu;
        this.nbTentative = nbTentative;
        this.nbChild = nbChild;

    }

    public void run(){

        createIndividu(this.nbIndividu);
        System.out.println(this.alIndividu.get(alIndividu.size()-1));
        int childAttempt = 0;
        while(!mutationFinished() && childAttempt < 10){ createChild(); childAttempt++;}
        System.out.println(this.alIndividu.get(alIndividu.size()-1));
        antiLose(this.jeu);

    }

    public Character getAction(){ return alIndividu.get(alIndividu.size()-1).getActionsJouees().get(0); }

    private boolean mutationFinished(){

        ArrayList<Character> strat = alIndividu.get(0).getActionsJouees();

        for(Individu i : alIndividu){
            if(!strat.equals(i.getActionsJouees()))return false;
        }

        return true;

    }

    private void createChild(){

        Individu i1 = alIndividu.get(alIndividu.size()-1);
        Individu i2 = alIndividu.get(alIndividu.size()-2);
        Individu i3 = alIndividu.get(alIndividu.size()-3);
        this.alIndividu = new ArrayList<Individu>();

        while(alIndividu.size() < this.nbChild){

            ArrayList<Character> alC = new ArrayList<Character>();
            for(int i = 0; i < i1.getNbPart(); i++) alC.addAll(new ArrayList<Character>(getRandomIndividu(i1,i2,i3).getGenePart((int)(Math.random()*i1.getNbPart()))));
            double fitness = fitness(jeu,alC);
            alIndividu.add(new Individu(alC,fitness));

        }

        Collections.sort(alIndividu);

    }

    private Individu getRandomIndividu(Individu i1, Individu i2, Individu i3){

        int random = (int)((Math.random()*3)+1);
        switch (random) {
            case 1:
                return i1;
            case 2:
                return i2;
            case 3:
                return i3;
            default:
                return null;
        }

    }

    private void createIndividu(int nbIndividu){

        alIndividu = new ArrayList<Individu>();

        while(alIndividu.size() < nbIndividu){

            ArrayList<Character> strategy = newStrategy(this.jeu);
            double fitness = fitness(this.jeu,strategy);
            alIndividu.add(new Individu(strategy,fitness));

        }

        Collections.sort(alIndividu);
    }

    private ArrayList<Character> newStrategy(Jeu jeu){

        ArrayList<Character> strategy = new ArrayList<Character>();
        ArrayList<Character> actionPossible = jeu.actionsJouable();
        while(!actionPossible.isEmpty())strategy.add(actionPossible.remove((int)(Math.random()*actionPossible.size())));

        return strategy;

    }

    private ArrayList<Character> allPosibilities(Jeu jeu){
        ArrayList<Character> alC = new ArrayList<Character>();
        for(Character c : jeu.actionsJouable()) if(!alC.contains(c)) alC.add(c);
        return alC;
    }

    private void antiLose(Jeu jeu){

        for(Character c : jeu.actionsJouable()){
            Jeu cloneJeu = jeu.clone();
            cloneJeu.jouer(getAction());
            cloneJeu.jouer(c);
            if(cloneJeu.getPartieGagner() && !cloneJeu.getNomJoueurCourant().equals(nomJoueur)){
                alIndividu.get(alIndividu.size()-1).getActionsJouees().set(0,c);
                return;
            }
        }

    }

    private double fitness(Jeu jeu, ArrayList<Character> strategy){

        int nbWin = 0;
        for (int ii = 0; ii < nbTentative; ii++) {
            Jeu cloneJeu = jeu.clone();
            int nbStep = 0;
            while (!cloneJeu.getPartieTerminer()) {
                cloneJeu.jouer(strategy.get(nbStep));
                if (!cloneJeu.getPartieTerminer()) cloneJeu.jouer(newStrategy(cloneJeu).get(0));
                nbStep++;
            }
            if (cloneJeu.getNomJoueurCourant().equals(nomJoueur)) {
                nbWin++;
            }
        }
        return ((double)nbWin/(double)nbTentative);
    }

}

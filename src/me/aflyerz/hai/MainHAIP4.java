package me.aflyerz.hai;

import me.aflyerz.puissance4.Puissance4;

import java.util.Scanner;

public class MainHAIP4 {

    public static Character runAi(Hai ai){
        ai.start();
        while (ai.isAlive()) { try { Thread.sleep(100);} catch (Exception e) { e.printStackTrace(); }}
        return ai.getAction();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Puissance4 p4 = new Puissance4();
        System.out.println(p4.toString());
        boolean joueur1 = true;
        while (!p4.getPartieGagner() && !p4.grillePleine()) {
            p4.jouer(Character.forDigit(sc.nextInt()-1,10));
            System.out.println(p4.toString());
            Hai ai = new Hai(p4,"Jaune", 100,1000,100);
            runAi(ai);
            p4.jouer(ai.getAction());
            System.out.println(p4.toString());
            joueur1 = !joueur1;
        }
        if(!joueur1) System.out.println("Le joueur 1 gagne");
        if(joueur1) System.out.println("Le joueur 2 gagne");

    }

}

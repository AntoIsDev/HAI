package me.aflyerz.puissance4;

import java.util.Scanner;

public class MainCUI {

	public static void main(String[] args) {
		Puissance4 p4 = new Puissance4();
		System.out.println(p4.toString());
		boolean joueur1 = true;
		while (!p4.getPartieGagner() && !p4.grillePleine()) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Veuillez indiquez la colonne que vous voulez jouez (1-7) :");
			p4.jouer(Character.forDigit(sc.nextInt()-1,10));
			System.out.println(p4.toString());
			joueur1 = !joueur1;
		}
		if(joueur1) System.out.println("Le joueur 1 gagne");
		if(!joueur1) System.out.println("Le joueur 2 gagne");
	}

}
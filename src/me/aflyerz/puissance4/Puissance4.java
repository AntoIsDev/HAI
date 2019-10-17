package me.aflyerz.puissance4;

import me.aflyerz.hai.Jeu;

import java.util.ArrayList;
import java.util.HashMap;

public class Puissance4 implements Jeu{
	
	char[][] grille;
	int nbLigne, nbColonne;
	char joueurCourant;
	private boolean partieGagne;
	
	public Puissance4(){
		
		grille = new char[6][7];
		nbLigne = nbColonne = 0;
		joueurCourant = 'R';
		partieGagne = false;

	}

	@Override
	public Jeu clone(){
		Puissance4 p4 = new Puissance4();
		char[][] p4Grille = this.getGrille();
		p4.grille = new char[p4Grille.length][p4Grille[0].length];
		for (int i = 0; i < p4Grille.length; i++) {
			for (int ii = 0; ii < p4Grille[0].length; ii++) p4.grille[i][ii] = p4Grille[i][ii];
		}
		p4.nbLigne = this.nbLigne;
		p4.joueurCourant = this.joueurCourant;
		p4.partieGagne = this.partieGagne;
		return p4;
	}

	public Puissance4(Puissance4 p4){
		
		char[][] p4Grille = p4.getGrille();
		this.grille = new char[p4Grille.length][p4Grille[0].length];
		for (int i = 0; i < p4Grille.length; i++) {
			for (int ii = 0; ii < p4Grille[0].length; ii++) {
				this.grille[i][ii] = p4Grille[i][ii];
			}
		}
		this.nbLigne = p4.nbLigne;
		this.joueurCourant = p4.joueurCourant;
		this.partieGagne = p4.partieGagne;
		
	}
	
	public char[][] getGrille(){
		
		return this.grille;
		
	}
	
	public char getJeton(int ligne, int colonne){ //retourne la valeur située en ligne, colonne.

		return grille[ligne][colonne];
		
	}
	
	public void jouer(char action){ //positionne le jeton du joueur courant dans la colonne.

		int colonne = Character.getNumericValue(action);

		if(colonne < 7 && colonne >= 0 && this.getPartieTerminer() == false){ //colonne valide
			
			int ligneVide = -1;
			
			for(int iLigne = 0; iLigne < 6; iLigne++){
				
				if(ligneVide == -1 && grille[5-iLigne][colonne] == '\u0000'){
					
					ligneVide = 5-iLigne;
					
				}
				
			}
			
			if(ligneVide != -1){ //Ligne vide dans la colonne

				grille[ligneVide][colonne] = joueurCourant;
				if(aGagne(ligneVide, colonne)){

					partieGagne = true;
					
				}else{
					
					changerJoueur();
					
				}
				
			}
			
		}
	}

	public ArrayList<Character> actionsJouable() {
		ArrayList<Character> actionsJouable = new ArrayList<Character>();
		for(int i = 0; i < 7; i++){ for(int ii = 0; ii < nbTimeColJouable(i); ii++) actionsJouable.add(Character.forDigit(i,10)); }
		return actionsJouable;
	}

	public int nbTimeColJouable(int colonne){

		int nbTime = 0;

		for(int iLigne = 0; iLigne < 6; iLigne++){
				
			if(grille[5-iLigne][colonne] == '\u0000') nbTime++;
					
		}

		return nbTime;
		
	}
	
	public boolean grillePleine(){
		
		boolean plein = true;
		
		for(int iLigne = 0; iLigne < 6; iLigne++){
			
			for(int iCol = 0; iCol < 7; iCol++){
				
				if(grille[iLigne][iCol] == '\u0000'){
					
					plein = false;
					break;
					
				}
				
			}
			
		}
		
		return plein;
		
	}
	
	public String toString(){ //retourne la grille sous forme de Chaîne de caractères, cette méthode sera utile pour l'IHM console
		
		String stringGrille = "";
		
		for(int iLigne = 0; iLigne < 6; iLigne++){
			
			stringGrille+="|";
			
			for(int iCol = 0; iCol < 7; iCol++){
				
				if(getJeton(iLigne, iCol) == 'R'){
					
					stringGrille+="R";
					
				}else if(getJeton(iLigne, iCol) == 'J'){
					
					stringGrille+="J";
					
				}else{
					
					stringGrille+=" ";
					
				}
				
				stringGrille+="|";
				
			}
			
			stringGrille+="\n";
			
		}
		
		return stringGrille;
		
	}
	
	private boolean aGagne(int ligne, int colonne){ //permettant de déterminer si le joueur venant de jouer à effectuer un alignement de quatre jetons
	
		int nbJeton;
		
		//Check bas
		nbJeton = 0;
		int j = 0;
		while(ligne+j < 6 && grille[ligne+j][colonne] == joueurCourant){
				
					nbJeton++;
					j++;
			
		}
		
		if(nbJeton >= 4){
			
			return true;
			
		}
		
		//Fin Check bas
		
		//Check horizontal
		nbJeton = 0;
		j = 0;
		while(colonne+j < 7 && grille[ligne][colonne+j] == joueurCourant){
				
					nbJeton++;
					j++;
				
		}
		j = 1;
		while(colonne-j >= 0 && grille[ligne][colonne-j] == joueurCourant){
				
					nbJeton++;
					j++;
				
		}
		
		if(nbJeton >= 4){

			return true;
			
		}
		
		//Fin Check horizontal
		
		//Check basDroite
		nbJeton = 0;
		j = 0;
		while(colonne-j >= 0 && ligne+j < 6 && grille[ligne+j][colonne-j] == joueurCourant){
				
				nbJeton++;
				j++;
				
		}
		j = 1;
		while(colonne+j < 7 && ligne-j >= 0 && grille[ligne-j][colonne+j] == joueurCourant){
				
				nbJeton++;
				j++;
			
		}
		
		if(nbJeton >= 4){
			
			return true;
			
		}
		
		//Fin Check basDroite
		
		//Check hautGauche
		nbJeton = 0;
		j = 0;
		while(colonne-j >= 0 && ligne-j >= 0 && grille[ligne-j][colonne-j] == joueurCourant){
				
				nbJeton++;
				j++;
				
		}
		j = 1;
		while(colonne+j < 7 && ligne+j < 6 && grille[ligne+j][colonne+j] == joueurCourant){
				
				nbJeton++;
				j++;
			
		}
		
		if(nbJeton >= 4){
			
			return true;
			
		}
		
		//Fin Check hautGauche
		
		return false;
			
	}
	
	private void changerJoueur(){ //méthode privée modifiant le joueur courant en le passant de 'R' à 'J' ou inversement.
		
		if(joueurCourant == 'R'){
			
			joueurCourant = 'J';
			
		}else{
			
			joueurCourant = 'R';
			
		}
		
	}

	public String getNomJoueurCourant(){
		if(joueurCourant == 'R') return "Rouge";
		return "Jaune";
	}

	@Override
	public boolean getPartieTerminer() {
		return (grillePleine() || getPartieGagner());
	}

	public boolean getPartieGagner(){ return this.partieGagne;}
	
	
}
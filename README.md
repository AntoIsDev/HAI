# HAI
Ai developped live rapidly x)

Hai is an AI framework using machine learning to beat you in your own game. 
It's easy to use, and I give in this repo an example with a Power 4 game.

Feel free to use this little machine learning process to easily implement a solo mode in your java games

How to use : 

Implement the Jeu interface in your game, it contains all the methods used by the AI

public void jouer(char action) give it a char to choose an action in the game

public ArrayList<Character> actionsJouable() give all the actions possible in the game
  
public boolean getPartieGagner() return if the game is win

public boolean getPartieTerminer() return if the game is over  (if it's over and not win, HAI will hate you, but it gonna be stronger :) )

public String getNomJoueurCourant() return the current player's name

public Jeu clone() return a copy of the current game, it's used to do some machine learning, HAI will try to win vs Math.random, take popcorn

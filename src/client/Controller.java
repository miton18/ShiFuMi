package client;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.*;
import socket.CSocket;
import socket.JSONObject;

public class Controller implements ActionListener {
   
   // Le bouton sur lequel a clique le joueur
   private String commande;
   
   // le choix de l'ordinateur
   // "papier", "pierre" ou "ciseaux"
   private String choix;
   
   /* 
   Toute l'intelligence du jeu est dans le tableau suivant
     Tableau donnant les gains du joueur
     Les indices des lignes correspondent aux choix du joueur, 
      respectivement : 0 pour papier, 1 pour pierre, 2 pour ciseaux
     Les indices des colonnes correspondent aux choix de l'ordinateur,
      respectivement : 0 pour papier, 1 pour pierre, 2 pour ciseaux
   */      
   private int gains[][] = {
      { 0,  1, -1},
      {-1,  0,  1},      
      { 1, -1,  0}
   };
   
   // le constructeur
   public Controller() {
      super();
      this.commande = new String("");
      this.choix = new String("");
   }
   
   // Le traitement des actions du joueur
   public void actionPerformed(ActionEvent e) {
      
      int jeuJoueur = 0;
      int jeuOrdi = 0;
      
      this.commande = e.getActionCommand();
   
      if (this.commande.equals("Quitter")) {
         System.exit(0);
      }
      else if (this.commande.equals("Réinitialiser")) {
         Interface.gainJoueur = 0;
         Interface.gainOrdi = 0;
         Interface.maReponse.setIcon(Interface.iconeVierge);
         Interface.ordiReponse.setIcon(Interface.iconeVierge);            
         Interface.monScore.setText(Interface.startHtml+new Integer(Interface.gainJoueur).toString()+Interface.endHtml);            
         Interface.ordiScore.setText(Interface.startHtml+new Integer(Interface.gainOrdi).toString()+Interface.endHtml); 
         Interface.maFenetre.getContentPane().setBackground (Color.WHITE);
      }
      // Le traitement des choix joueur et ordinateur...
      else {
         // Choix aléatoire coté ordinateur
         this.choix = choixAleatoire();
         // Modification dans l'interface selon les choix du joueur
         switch (this.commande) {
            case "Papier":
               jeuJoueur = 0;
               Interface.maReponse.setIcon(Interface.iconePapier);
               break;
            case "Pierre":
               jeuJoueur = 1;
               Interface.maReponse.setIcon(Interface.iconePierre);
               break;
            case "Ciseaux":
               jeuJoueur = 2;
               Interface.maReponse.setIcon(Interface.iconeCiseaux);      
         }
         // Modification dans l'interface selon les choix de l'ordinateur
         switch (this.choix) {            
            case "Papier":
               jeuOrdi = 0;
               Interface.ordiReponse.setIcon(Interface.iconePapier);
               break;
            case "Pierre":
               jeuOrdi = 1;
               Interface.ordiReponse.setIcon(Interface.iconePierre);
               break;
            case "Ciseaux":
               jeuOrdi = 2;
               Interface.ordiReponse.setIcon(Interface.iconeCiseaux);      
         }
         // Traitement du résultat
         // Joueur gagne
         if (this.gains[jeuJoueur][jeuOrdi]>0) {
            Interface.gainJoueur++;
            Interface.monScore.setText(Interface.startHtml+new Integer(Interface.gainJoueur).toString()+Interface.endHtml);
            Interface.maFenetre.getContentPane().setBackground (Color.GREEN);
         }
         // Joueur perd
         else if (this.gains[jeuJoueur][jeuOrdi]<0) {
            Interface.gainOrdi++;
            Interface.ordiScore.setText(Interface.startHtml+new Integer(Interface.gainOrdi).toString()+Interface.endHtml);         
            Interface.maFenetre.getContentPane().setBackground (Color.RED);
         }
         // Match nul
         else {
            Interface.maFenetre.getContentPane().setBackground (Color.WHITE);
         }
      }
      // pour vérifier, debug...
      System.out.println("Choix joueur       : "+jeuJoueur+" "+this.commande);
      System.out.println("Choix ordinateur   : "+jeuOrdi+" "+this.choix+"\n");
      
   }
   
   // Méthode pour tirage au hasard du choix de l'orinateur
   // Retourne une chaine de caractères : "papier", "pierre" ou "ciseaux"
   public String choixAleatoire() {

      int min = 1;
      int max = 3;
      String choix = null;
      Random rand = new Random();
      int nombreAleatoire = rand.nextInt(max - min + 1) + min;
      switch (nombreAleatoire) {
         case 1:
            choix = new String("Pierre");
            break;
         case 2:
            choix = new String("Papier");
            break;
         case 3:
            choix = new String("Ciseaux");      
      }
      return(choix);
   }

   public boolean getResponse( String choixJoueur ) {

      try {
         CSocket socket = new CSocket( InetAddress.getByName("127.0.0.1"), 443 );

         JSONObject req = new JSONObject();

         JSONObject res = socket.emit("It's your turn", "");
      }
      catch (Exception e) {

         System.out.println("");
      }
   }

}
package client;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import socket.CSocket;
import socket.JSONObject;

public class Controller implements ActionListener {
   
   // Le bouton sur lequel a clique le joueur
   private String commande;
   
   // le choix de l'ordinateur
   // "papier", "pierre" ou "ciseaux"
   private String choix;
   private int serverChoice;
   private int hasWin;
   private int jeuJoueur;
   
   // le constructeur
   public Controller() {
      super();
      this.commande = new String("");
      this.choix = new String("");
   }
   
   // Le traitement des actions du joueur
   public void actionPerformed(ActionEvent e) {
      
      this.jeuJoueur = 0;
      
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

         // Modification dans l'interface selon les choix du joueur
         switch (this.commande) {
            case "Papier":
               this.jeuJoueur = 0;
               Interface.maReponse.setIcon(Interface.iconePapier);
               break;
            case "Pierre":
               this.jeuJoueur = 1;
               Interface.maReponse.setIcon(Interface.iconePierre);
               break;
            case "Ciseaux":
               this.jeuJoueur = 2;
               Interface.maReponse.setIcon(Interface.iconeCiseaux);      
         }

         // Récupère la réponse su serveur
         choixServeur();

         // Modification dans l'interface selon les choix de l'ordinateur
         switch (this.serverChoice ) {
            case 0:
               Interface.ordiReponse.setIcon(Interface.iconePapier);
               break;
            case 1:
               Interface.ordiReponse.setIcon(Interface.iconePierre);
               break;
            case 2:
               Interface.ordiReponse.setIcon(Interface.iconeCiseaux);      
         }

         // Traitement du résultat
         // Joueur gagne
         if( this.hasWin == 1  ) {
         //if (this.gains[this.jeuJoueur][jeuOrdi]>0) {
            Interface.gainJoueur++;
            Interface.monScore.setText(Interface.startHtml+new Integer(Interface.gainJoueur).toString()+Interface.endHtml);
            Interface.maFenetre.getContentPane().setBackground (Color.GREEN);
         }
         // Joueur perd
         else if ( this.hasWin == -1 ) {
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
      Logger.getGlobal().log( Level.INFO, "Choix joueur       : " + jeuJoueur + " " + this.commande );
      Logger.getGlobal().log( Level.INFO, "Choix ordinateur   : " + this.serverChoice + " " + this.choix + "\n" );
      
   }

   /**
    * Implémente serverChoice et has win en fonction de la réponse du serveur,
    * au coup jourer par le joueur.
    */
   public void choixServeur() {

      try {
         CSocket socket = new CSocket( InetAddress.getByName("127.0.0.1"), 4430 );

         JSONObject req = new JSONObject();
         req.put("choix", this.jeuJoueur);

         JSONObject res = socket.emit("It's your turn", req);

         this.serverChoice = res.getInt("server");
         this.hasWin       = res.getInt("win");
         return;
      }
      catch (Exception e) {

         Logger.getGlobal().log( Level.SEVERE, e.getMessage() );
      }
   }
}
package client;

public class Jeu {

    // Point d'entré de l'application
   public static void main (String args[]) {
      
      // Création d'un objet instance de la classe "Controller"
      Controller control = new Controller();
      
      // Création de l'interface du joueur
      // On passe en argument la référece à l'objet "Controller"
      Interface jeu = new Interface(control);
   }

}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import layout.*;

public class Interface {
   
   // Les attributs sont "static" pour simplifier...
   // pas besoin de référence à un objet "Interface" coté Controller"
   public static JFrame maFenetre;
   public static JLabel maReponse;
   public static JLabel ordiReponse;
   public static JLabel monScore;
   public static JLabel ordiScore;   
   public static ImageIcon iconePapier;
   public static ImageIcon iconePierre;         
   public static ImageIcon iconeCiseaux;
   public static ImageIcon iconeVierge;   
   public static int gainJoueur = 0;
   public static int gainOrdi = 0;      

   public static String startHtml = new String("<html><font size=\"8\"><b>");
   public static String endHtml = new String("</b></font></html>");   
   
   Interface(Controller control) {
               
      // Création de maFenetre (Fenêtre principale)
      maFenetre = new JFrame("Papier-Pierre-Ciseaux");
      maFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      maFenetre.setBounds (100, 100, 1000, 560);
      maFenetre.getContentPane().setBackground (Color.WHITE);
      maFenetre.setResizable(false);
      
      // Création d'un TableLayout pour maFenetre
      double border = 5;
      double size[ ][ ] = { 
         {border, 300, TableLayout.FILL, 300, border},  // Columns
         {border, 30, 100, 100, 100, 100, TableLayout.FILL, border} // Rows
         }; 
      maFenetre.setLayout (new TableLayout(size)); 
       
      JLabel messageChoix = new JLabel("Faites votre choix ci-dessous :");
       
      iconePapier = new ImageIcon("images/papier.jpg");
      iconePierre = new ImageIcon("images/pierre.jpg");         
      iconeCiseaux = new ImageIcon("images/ciseaux.jpg");
      iconeVierge = new ImageIcon("images/vierge.jpg");            
      
      JButton papier = new JButton("Papier", iconePapier);
      JButton pierre = new JButton("Pierre", iconePierre);        
      JButton ciseaux = new JButton("Ciseaux", iconeCiseaux);
      JButton reset = new JButton("Réinitialiser");      
      JButton quitter = new JButton("Quitter");
       
      // C'est la classe "Controller" qui implémente l'interface "ActionListener"
      papier.addActionListener(control);
      pierre.addActionListener(control);
      ciseaux.addActionListener(control);
      reset.addActionListener(control);      
      quitter.addActionListener(control);
      
      // Colonne #1 de l'interface
      maFenetre.add(messageChoix, "1, 1, c, c");       
      maFenetre.add(papier, "1, 2");         
      maFenetre.add(pierre, "1, 3");          
      maFenetre.add(ciseaux, "1, 4");          
      maFenetre.add(reset, "1, 5");         
      maFenetre.add(quitter, "1, 6");
            
      JLabel messageJoueur = new JLabel(startHtml+"Joueur : "+endHtml);
      JLabel messageOrdi = new JLabel(startHtml+"Ordinateur : "+endHtml);
      JLabel messageMonScore = new JLabel(startHtml+"Score joueur : "+endHtml);
      JLabel messageOrdiScore = new JLabel(startHtml+"Score ordinateur : "+endHtml);      
      
      // Colonne #2 de l'interface
      maFenetre.add(messageJoueur, "2, 2, r, c");       
      maFenetre.add(messageOrdi, "2, 3, r, c");
      maFenetre.add(messageMonScore, "2, 4, r, c");
      maFenetre.add(messageOrdiScore, "2, 5, r, c");                  
      
      maReponse = new JLabel(iconeVierge);   
      ordiReponse = new JLabel(iconeVierge);
      monScore = new JLabel(startHtml+new Integer(gainJoueur).toString()+endHtml);
      ordiScore = new JLabel(startHtml+new Integer(gainOrdi).toString()+endHtml);
           
      // Colonne #3 de l'interface
      maFenetre.add(maReponse, "3, 2");
      maFenetre.add(ordiReponse, "3, 3");         
      maFenetre.add(monScore, "3, 4, l, c");
      maFenetre.add(ordiScore, "3, 5, l, c");      
     
      // Afficher maFenetre
      maFenetre.setVisible(true);
   }

}
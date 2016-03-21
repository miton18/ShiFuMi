import socket.*;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main( String[] arguments ) {
        try {
            SSocket sSocket = new SSocket( 443 );
            Random rand = new Random();
            int matrix[][] = {
                { 0, 1, -1 },
                { -1, 0, 1 },
                { 1, -1, 0 }
            };

            sSocket.on( "it's your turn", req -> {
                JSONObject res = new JSONObject();

                int choice = req.getInt( "choix" );
                int my     = Math.abs( rand.nextInt()) % 3;

                res.put( "win", matrix[choice][my] );

                return res;
            });

            sSocket.start();
        } catch( Exception e ) {
            Logger.getGlobal().log( Level.SEVERE, e.getMessage());
        }
    }
}
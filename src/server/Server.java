import socket.*;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main( String[] arguments ) {
        try {
            SSocket sSocket = new SSocket( 4430 );
            Random rand     = new Random();

            int matrix[][]  = {
                { 0, 1, -1 },
                { -1, 0, 1 },
                { 1, -1, 0 }
            };

            sSocket.on( "It's your turn", req -> {
                Logger.getGlobal().log( Level.INFO, "Receive : " + req.toString());

                JSONObject res = new JSONObject();

                int choice = req.getInt( "choix" );
                int my     = Math.abs( rand.nextInt()) % 3;

                res.put( "win", matrix[choice][my] );
                res.put( "server", my );

                Logger.getGlobal().log( Level.INFO, "Send : " + res.toString());

                return res;
            });

            sSocket.start();
        } catch( Exception e ) {
            Logger.getGlobal().log( Level.SEVERE, e.getMessage());
        }
    }
}
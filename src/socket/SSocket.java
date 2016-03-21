package socket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by florentin on 19/03/16.
 * This is Socket Server for handle CSocket event
 */
public class SSocket extends Thread {

    /**
     * Constructor of this object
     * @param int port
     * @throws IOException
     */
    public SSocket( int port ) throws IOException {
        serverSocket = new ServerSocket( port );
        hashMap      = new HashMap<String, Function<JSONObject, JSONObject>>();
    }

    /**
     * Handle an event and bind a function to it
     * @param String event
     * @param Function<JSONObject, JSONObject> fn
     */
    public void on( String event, Function<JSONObject, JSONObject> fn ) {
        hashMap.put( event, fn );
    }

    /**
     * Thread event loop | this part wait for handle an event and apply a spcific function bind by on method
     */
    public void run() {
        try {
            Socket socket         = null;
            BufferedReader in     = null;
            DataOutputStream out  = null;
            JSONObject jsonObject = null;
            JSONObject data       = null;

            while(( socket = serverSocket.accept()) != null ) {
                in         = new BufferedReader( new InputStreamReader( socket.getInputStream()));
                out        = new DataOutputStream( new BufferedOutputStream( socket.getOutputStream()));
                jsonObject = new JSONObject( in.readLine());

                if( hashMap.get( jsonObject.getString( "event" )) != null ) {
                    data = hashMap.get( jsonObject.getString( "event" )).apply( jsonObject.getJSONObject( "data" ));

                    out.writeBytes( data.toString() + "\n" );
                    out.flush();
                }

                out.close();
                in.close();
                socket.close();
            }
        } catch( Exception e ) {
            Logger.getGlobal().log( Level.SEVERE, e.getMessage());
        }
    }

    private HashMap<String, Function<JSONObject, JSONObject>> hashMap = null;
    private ServerSocket serverSocket = null;
}

package socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by florentin on 19/03/16.
 * it is a client socket object that allow to emit some event for communicate with server socket
 */
public class CSocket {

    /**
     * Constructor of this object get an InetAddress and a port
     * @param InetAddress addr
     * @param int port
     */
    public CSocket( InetAddress addr, int port ) {
        this.addr = addr;
        this.port = port;
    }

    /**
     * emit a event | open a socket for browse some data or treatment on a specific event
     * @param String event
     * @param JSONObject data
     * @return JSONObject
     * @throws IOException
     */
    public JSONObject emit( String event, JSONObject data ) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "event", event );
        jsonObject.put( "data", data );

        Socket socket        = new Socket( addr, port );
        BufferedReader in    = new BufferedReader( new InputStreamReader( socket.getInputStream()));
        DataOutputStream out = new DataOutputStream( new BufferedOutputStream( socket.getOutputStream()));

        out.writeBytes( jsonObject.toString() + "\n" );
        out.flush();

        String json = in.readLine();

        in.close();
        out.close();
        socket.close();

        return new JSONObject( json );
    }

    private InetAddress addr = null;
    private int port = 0;
}

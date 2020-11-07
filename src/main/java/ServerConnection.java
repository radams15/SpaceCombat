import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {

    private final Socket sock;
    private final BufferedReader reader;
    private final PrintWriter writer;

    private Gson gson = new Gson();

    public ServerConnection(String server, int port) throws IOException { // let the method initialising this handle errors
        sock = new Socket(server, port);

        writer = new PrintWriter(sock.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    public void sendState(State state){
        String toSend = gson.toJson(state);

        System.out.println(toSend);

        writer.write(toSend+"\n");

        writer.flush();
    }
}

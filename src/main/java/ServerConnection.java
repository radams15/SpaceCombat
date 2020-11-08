import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection extends Thread{

    private final Socket sock;
    private final BufferedReader reader;
    private final PrintWriter writer;

    private Message currentMessage;

    private Gson gson = new Gson();

    public ServerConnection(String server, int port) throws IOException { // let the method initialising this handle errors
        sock = new Socket(server, port);

        writer = new PrintWriter(sock.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    public void run(){
        while(true){
            String raw = receive();

            currentMessage = gson.fromJson(raw, Message.class);
        }
    }

    public ArrayList<Ship> getShips(){
        if(currentMessage == null || currentMessage.ships == null){
            return new ArrayList<>();
        }
        return currentMessage.ships;
    }

    public ArrayList<Torpedo> getTorpedoes(){
        if(currentMessage == null || currentMessage.ships == null){
            return new ArrayList<>();
        }
        return currentMessage.torpedoes;
    }

    public void sendMessage(Message message){
        String toSend = gson.toJson(message);

        send(toSend);
    }

    private void send(String message) {
        writer.write(message+"\n");
        writer.flush();
    }

    private String receive() {
        try{
            String data = reader.readLine();
            return data;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}

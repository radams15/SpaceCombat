import java.util.ArrayList;

public class Message {
    ArrayList<Ship> ships;

    ArrayList<Torpedo> torpedoes;

    public Message(ArrayList<Ship> ships, ArrayList<Torpedo> torpedoes){
        this.ships = ships;
        this.torpedoes = torpedoes;
    }
}

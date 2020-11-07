public class State {
    int[] coords = new int[]{0,0};
    int angle = 0;

    public String toString(){
        return String.format("(Ship at [%d, %d] at %d')\n", coords[0], coords[1], angle);
    }
}

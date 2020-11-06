import org.lwjgl.opengl.GL11;

public class Torpedo {

    private float angle;
    private float[] location;

    public Torpedo(int x, int y, float angle){
        location = new float[]{x,y};
        this.angle = angle;
    }

    public void tick(){
        double[] disp = Physics.get_displacement(angle, Constants.torpedoSpeed);

        location[0] -= disp[0];
        location[1] += disp[1];
    }

    public boolean exists(){
        if(location[0] < 0 || location[0] > Constants.windowSize[0] || location[1] < 0 || location[1] > Constants.windowSize[1]){
            return false;
        }
        return true;
    }

    public void draw(){
        GL11.glLoadIdentity();

        GL11.glColor3f(Constants.missileColour[0], Constants.missileColour[1], Constants.missileColour[2]);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(location[0],location[1]);
        GL11.glVertex2f(location[0]+5,location[1]);
        GL11.glVertex2f(location[0]+5,location[1]+5);
        GL11.glVertex2f(location[0],location[1]+5);
        GL11.glEnd();
    }
}

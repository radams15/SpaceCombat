import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Ship {
    private int angle = 0;
    private int[] size;
    private int[] location = new int[] {0,0};
    private double[] speed = new double[] {0,0};

    ArrayList<Torpedo> torpedoes;

    public Ship(int width, int height){
        size = new int[] {width, height};
        torpedoes = new ArrayList<>();
    }

    public void incAngle(int amount){
        angle += amount;

        if(amount > 360) angle -= 360;
        else if(amount < 0) angle += 360;
    }

    public void decAngle(int amount){
        incAngle(-amount);
    }

    public void draw(){
        GL11.glColor3f(Constants.shipColour[0], Constants.shipColour[1], Constants.shipColour[2]);

        GL11.glLoadIdentity();
            GL11.glTranslated(location[0], location[1], 0); // move to correct x and y coords
            GL11.glRotated(angle, 0, 0, 1); // rotate to angle

            GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2d(0, 0);
                GL11.glVertex2d(size[0]/2d, size[1]);
                GL11.glVertex2d(size[0], 0);
            GL11.glEnd();
    }

    void tick(){
        speed[0] /= 1.2; // drag of 120%
        speed[1] /= 1.2;

        speed[0] = Math.round(speed[0]*100)/100.0f; // avoid small speeds that are not taken away, put to 2dp
        speed[1] = Math.round(speed[1]*100)/100.0f;

        location[0] -= speed[0];
        location[1] += speed[1];

        if(location[0] < 0) location[0] = 0;
        else if(location[0] > Constants.windowSize[0]) location[0] = Constants.windowSize[0];

        if(location[1] < 0) location[1] = 0;
        else if(location[1] > Constants.windowSize[1]) location[1] = Constants.windowSize[1];

        for(Torpedo t : (ArrayList<Torpedo>) torpedoes.clone()){
            t.tick();
            if(!t.exists()){
                torpedoes.remove(t);
            }
            t.draw();
        }
    }

    public void thrust(){
        double distance = Physics.get_acceleration(Constants.thrusterStrength, Constants.craftMass); // since we accelerate for 1 second, distance = acceleration*1

        double[] disp = Physics.get_displacement(angle, distance);

        speed[0] += disp[0];
        speed[1] += disp[1];
    }

    public void fireTorpedo(){
        Torpedo t = new Torpedo(location[0], location[1], angle);
        torpedoes.add(t);
    }
}

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Ship {
    private double[] speed = new double[] {0,0};

    private State state;

    ArrayList<Torpedo> torpedoes = new ArrayList<>();

    public Ship(State state){
        this.state = state;
    }

    public void incAngle(int amount){
        state.angle += amount;

        if(state.angle > 360) state.angle -= 360;
        else if(state.angle < 0) state.angle += 360;
    }

    public void decAngle(int amount){
        incAngle(-amount);
    }

    public void draw(){
        GL11.glColor3f(Constants.shipColour[0], Constants.shipColour[1], Constants.shipColour[2]);

        draw(state);
    }

    public void draw(State s){
        GL11.glColor3f(Constants.shipColour[0], Constants.shipColour[1], Constants.shipColour[2]);

        GL11.glLoadIdentity();
        GL11.glTranslated(s.coords[0], s.coords[1], 0); // move to correct x and y coords
        GL11.glRotated(s.angle, 0, 0, 1); // rotate to angle

        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2d(0, 0);
        GL11.glVertex2d(Constants.shipSize[0]/2d, Constants.shipSize[1]);
        GL11.glVertex2d(Constants.shipSize[0], 0);
        GL11.glEnd();
    }

    void tick(){
        speed[0] /= 1.2; // drag of 120%
        speed[1] /= 1.2;

        speed[0] = Math.round(speed[0]*100)/100.0f; // avoid small speeds that are not taken away, put to 2dp
        speed[1] = Math.round(speed[1]*100)/100.0f;

        state.coords[0] -= speed[0];
        state.coords[1] += speed[1];

        if(state.coords[0] < 0) state.coords[0] = 0;
        else if(state.coords[0] > Constants.windowSize[0]) state.coords[0] = Constants.windowSize[0];

        if(state.coords[1] < 0) state.coords[1] = 0;
        else if(state.coords[1] > Constants.windowSize[1]) state.coords[1] = Constants.windowSize[1];

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

        double[] disp = Physics.get_displacement(state.angle, distance);

        speed[0] += disp[0];
        speed[1] += disp[1];
    }

    public void fireTorpedo(){
        Torpedo t = new Torpedo(state.coords[0], state.coords[1], state.angle);
        torpedoes.add(t);
    }
}

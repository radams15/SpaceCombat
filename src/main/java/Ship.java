import org.lwjgl.opengl.GL11;

public class Ship {
    private double[] speed = new double[] {0,0};
    int[] coords = new int[]{0,0};
    int angle = 0;

    public Ship(){
        new Ship(0,0,0);
    }

    public Ship(int x, int y, int angle){
        setState(x, y, angle);
    }

    public void setState(int x, int y, int angle){
        this.coords = new int[]{x,y};
        this.angle = angle;
    }

    public void incAngle(int amount){
        angle += amount;

        if(angle > 360) angle -= 360;
        else if(angle < 0) angle += 360;
    }

    public void decAngle(int amount){
        incAngle(-amount);
    }

    public void draw(){
        GL11.glColor3f(Constants.shipColour[0], Constants.shipColour[1], Constants.shipColour[2]);

        GL11.glColor3f(Constants.shipColour[0], Constants.shipColour[1], Constants.shipColour[2]);

        GL11.glLoadIdentity();
        GL11.glTranslated(coords[0], coords[1], 0); // move to correct x and y coords
        GL11.glRotated(angle, 0, 0, 1); // rotate to angle

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

        coords[0] -= speed[0];
        coords[1] += speed[1];

        if(coords[0] < 0) coords[0] = 0;
        else if(coords[0] > Constants.windowSize[0]) coords[0] = Constants.windowSize[0];

        if(coords[1] < 0) coords[1] = 0;
        else if(coords[1] > Constants.windowSize[1]) coords[1] = Constants.windowSize[1];


    }

    public void thrust(){
        double distance = Physics.get_acceleration(Constants.thrusterStrength, Constants.craftMass); // since we accelerate for 1 second, distance = acceleration*1

        double[] disp = Physics.get_displacement(angle, distance);

        speed[0] += disp[0];
        speed[1] += disp[1];
    }
}

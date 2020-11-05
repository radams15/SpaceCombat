import org.lwjgl.opengl.GL11;

public class Ship {
    private int angle = 0;
    private int[] size;
    private int[] location = new int[] {0,0};
    private double[] speed = new double[] {0,0};

    private final double drag = 1; // speed deduction per second
    private final double thruster_strength = 10; // N
    private final double craft_mass = 20; // KG

    public Ship(int width, int height){
        size = new int[] {width, height};
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
        GL11.glColor3f(1.0f, 0.0f, 0.0f);

        GL11.glLoadIdentity();
            GL11.glTranslated(location[0], location[1], 0);
            GL11.glRotated(angle, 0, 0, 1);
            GL11.glBegin(GL11.GL_TRIANGLES);
            GL11.glVertex2d(0, 0);
            GL11.glVertex2d(size[0]/2d, size[1]);
            GL11.glVertex2d(size[0], 0);
        GL11.glEnd();
    }

    double get_acceleration(double force, double mass){
        return force / mass;
    }

    double[] get_displacement(double angle, double distance){
        double[] disp = new double[2];

        disp[0] = distance * Math.sin(Math.toRadians(angle))*10;
        disp[1] = distance * Math.cos(Math.toRadians(angle))*10;

        return disp;
    }

    void tick(){
        if(speed[0] > 0) speed[0] -= drag;
        if(speed[1] > 0) speed[1] -= drag;
        if(speed[0] < 0) speed[0] += drag;
        if(speed[1] < 0) speed[1] += drag;

        location[0] -= speed[0];
        location[1] += speed[1];

        if(location[0] < 0) location[0] = 0;
        else if(location[0] > Constants.windowSize[0]) location[0] = Constants.windowSize[0];

        if(location[1] < 0) location[1] = 0;
        else if(location[1] > Constants.windowSize[1]) location[1] = Constants.windowSize[1];
    }

    public void thrust(){
        double acc = get_acceleration(thruster_strength, craft_mass);

        double distance = acc * 1 * 1;

        double[] disp = get_displacement(angle, distance);

        /*speed[0] += disp[0];
        speed[1] += disp[1];*/
        location[0] -= disp[0];
        location[1] += disp[1];
    }

    public void shoot(){

    }
}

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

public class MainWindow {
    Ship ship;

    public MainWindow(){
        ship = new Ship(40, 50);
        run();
    }

    public void run(){
        try {
            Display.setDisplayMode(new DisplayMode(Constants.windowSize[0], Constants.windowSize[1]));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // init OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Constants.windowSize[0], 0, Constants.windowSize[1], 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        do {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            loop();
            Display.update();
        }while (!Display.isCloseRequested());

        Display.destroy();
    }

    private boolean overlap(int[] p1, int[] p2){
        if ((p1[2] > p2[2] || p2[0] > p1[2]) || (p1[3] < p2[1] || p2[3] < p1[1])) {
            return false;
        }
        return true;
    }

    private void loop(){
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            ship.incAngle(10);
        }else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            ship.decAngle(10);
        }else if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            ship.thrust();
        }else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            ship.shoot();
        }

        ship.tick();

        ship.draw();
    }
}

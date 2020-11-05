import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class MainWindow {
    private final int loopDelay = 0;

    private final int[] size = new int[]{800, 600};

    public void run(){
        try {
            Display.setDisplayMode(new DisplayMode(size[0], size[1]));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // init OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, size[0], 0, size[1], 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        while (!Display.isCloseRequested()) {
            if(loop()){
                break;
            }

            try {
                Thread.sleep(loopDelay);
            }catch (InterruptedException e){e.printStackTrace();}
        }

        Display.destroy();
    }

    private boolean overlap(int[] p1, int[] p2){
        if ((p1[2] > p2[2] || p2[0] > p1[2]) || (p1[3] < p2[1] || p2[3] < p1[1])) {
            return false;
        }
        return true;
    }

    private boolean loop(){

        return false;
    }
}

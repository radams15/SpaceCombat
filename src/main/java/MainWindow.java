import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class MainWindow {

    private long window;

    Ship ship;

    public MainWindow(){
        ship = new Ship(50, 60);
        init();
        run();
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set(); // print errors to System.err buffer

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(Constants.windowSize[0], Constants.windowSize[1], "Hello World!", NULL, NULL);
        if (window == NULL){
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, this::keyCallback);

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Make the window visible
        glfwShowWindow(window);
    }

    public void run(){
        GL.createCapabilities();

        glMatrixMode(GL11.GL_PROJECTION); // view from the top
        glLoadIdentity(); // load the identity matrix for transformation to start
        glOrtho(0, Constants.windowSize[0], 0, Constants.windowSize[1], 1, -1);
        // set the coordinate system so that the bottom left is [0,0] and the top right is [win length, win height]
        glMatrixMode(GL11.GL_MODELVIEW); // view from the side

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set bg colour to black


        while(!glfwWindowShouldClose(window)){ // run while window is not requested to close
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the display

            loop(); // run our display code

            glfwSwapBuffers(window); // push the altered data to the window
            glfwPollEvents(); // check if keys pressed
        }

        glfwFreeCallbacks(window); // clean up the callback and the window
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate(); // end display
        glfwSetErrorCallback(null).free(); // free error calllback
    }

    private void keyCallback(long window, long key, long scancode, long action, long mods){
        if(key == GLFW_KEY_W){
            ship.thrust();
        }else if(key == GLFW_KEY_D){
            ship.decAngle(5);
        }else if(key == GLFW_KEY_A){
            ship.incAngle(5);
        }else if(key == GLFW_KEY_SPACE){
            ship.fireTorpedo();
        }
    }

    private void loop(){
        ship.tick();
        ship.draw();
    }
}

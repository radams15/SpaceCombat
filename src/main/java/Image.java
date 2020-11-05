import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Image {

    ByteBuffer buf;
    public int width;
    public int height;

    public Image(String path) {
        InputStream in = null;
        try {
            in = new FileInputStream(path);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();

            buf = ByteBuffer.allocateDirect(4 * width * height);

            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();
        } catch (IOException e){
            width = 0;
            height = 0;
        }finally {
            try {
                if(in != null) in.close();
            }catch (IOException ignored){} // really doesn't matter if this breaks
        }
    }
}

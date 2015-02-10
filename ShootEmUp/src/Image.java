import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Image {
	private ByteBuffer buf;
	private int texWidth;
	private int texHeight;
	private File image;
	
	public Image(String file){
		loadImage(file);
	}
	
	private void loadImage(String file){
		image = new File(file);			
	}
	
	public ByteBuffer byteBuffer() {
		try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream(image);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);
             
            // Get the width and height of the texture
            texWidth = decoder.getWidth();
            texHeight = decoder.getHeight();
             
             
            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
            buf.flip();

    
            in.close();
    		return buf;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
		return null;

	}
	
	public int getHeight(){
		return texHeight;
	}
	
	public int getWidth(){
		return texWidth;
	}
}

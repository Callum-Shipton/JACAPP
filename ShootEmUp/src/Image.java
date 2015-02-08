import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.*;

public class Image {
	private static BufferedImage img = null;
	
	private static File grass = new File("res/grass.png");
	
	private static void loadImage(File file) throws IOException{
		try {
		    img = ImageIO.read(file);
		} catch (IOException e) {
		}				
	}
	
	public static ByteBuffer byteBuffer(File file) throws IOException{
		loadImage(file);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( img, "png", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		return ByteBuffer.wrap(imageInByte);
	}
	
	public int getHeight(File file) throws IOException{
		loadImage(file);
		return img.getHeight();
	}
	
	public int getWidth(File file) throws IOException{
		loadImage(file);
		return img.getWidth();
	}
}

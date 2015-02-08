import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.*;

public class Image {
	private BufferedImage img = null;
	private File image;
	
	public Image(String file){
		loadImage(file);
	}
	
	private void loadImage(String file){
		try {
			image = new File(file);
		    img = ImageIO.read(image);
		} catch (IOException e) {
		}			
	}
	
	public ByteBuffer byteBuffer() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( img, "png", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return ByteBuffer.wrap(imageInByte);
		} catch (IOException e) {
		}
		return null;
	}
	
	public int getHeight(){
		return img.getHeight();
	}
	
	public int getWidth(){
		return img.getWidth();
	}
}

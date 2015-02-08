import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.*;

public class Image {
	private BufferedImage img = null;
	private File image;
	
	public Image(String file) throws IOException{
		image = new File(file);
		loadImage(image);
	}
	
	private void loadImage(File file){
		try {
		    img = ImageIO.read(file);
		} catch (IOException e) {
		}			
	}
	
	public ByteBuffer byteBuffer() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( img, "png", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		return ByteBuffer.wrap(imageInByte);
	}
	
	public int getHeight()throws IOException{
		return img.getHeight();
	}
	
	public int getWidth() throws IOException{
		return img.getWidth();
	}
}

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

public class Level {
	private BufferedImage img = null;
	
	public Level(String file){
		loadLevel(new File(file));
	}
	
	private void loadLevel(File file){
		try {
		    img = ImageIO.read(file);
		} catch (IOException e) {
		}
		
		for(int x = 0; x < img.getWidth(); x++){
			for(int y = 0; y < img.getHeight(); y++){
				//to-do adding images 
			}
		}				
	}
}
package display;

import java.util.HashMap;
import java.util.Map;

public abstract class ArtLoader {

	private static Map<String, Image> artFiles = new HashMap<>();

	public abstract void loadArt();

	public static Image getImage(String filename) {
		return artFiles.get(filename);
	}

	public static void addArt(String key, Image image) {
		artFiles.put(key, image);
	}
}

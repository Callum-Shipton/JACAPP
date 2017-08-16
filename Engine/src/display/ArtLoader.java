package display;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.FileManager;
import logging.Logger;

public abstract class ArtLoader {

	private static final String FILE_LOCATION = "res/Images/images.art";

	private static Map<String, Image> artFiles = new HashMap<>();

	public static void loadArt() {
		List<String> images = FileManager.getProperties(FILE_LOCATION);
		for (String imageInfo : images) {
			if (!"".equals(imageInfo)) {
				int commaIndex = imageInfo.indexOf(',');
				String file = imageInfo.substring(0, commaIndex);
				int x = Integer.parseInt(imageInfo.substring(commaIndex + 2, imageInfo.lastIndexOf(',')));
				int y = Integer.parseInt(imageInfo.substring(imageInfo.lastIndexOf(',') + 2, imageInfo.length()));
				Logger.info(file + ": " + x + ", " + y);
				Image image = new Image(file, x, y);
				String key = imageInfo.substring(imageInfo.lastIndexOf('/') + 1, imageInfo.indexOf(".png"));
				artFiles.put(key, image);
			}
		}
	}

	public static Image getImage(String filename) {
		Image image = artFiles.get(filename);
		if (image == null) {
			Logger.error("Art file missing: " + filename);
			return new Image("/Images/defaultTexture.png", 1, 1);
		}
		return image;
	}
}

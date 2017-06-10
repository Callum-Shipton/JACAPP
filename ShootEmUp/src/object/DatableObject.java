package object;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

public interface DatableObject<I> {

	static Gson g = (new GsonBuilder()).setPrettyPrinting().create();

	default Map<String, JsonArray> findFiles(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		Map<String, JsonArray> fileObjects = new HashMap<>();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				fileObjects.put(file.getName(), readJSON(file.getPath(), file.getName()));
			}
			if (file.isDirectory()) {
				findFiles(file.getPath());
			}
		}
		return fileObjects;
	}

	JsonArray readJSON(String path, String fileName);

	Map<String, Map<String, I>> getSystem();

}

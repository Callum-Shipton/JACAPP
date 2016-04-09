package object;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface DatableObject {

	static Gson g = (new GsonBuilder()).setPrettyPrinting().create();

	default void findFiles(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				readJSON(file.getPath(), file.getName());
			}
			if (file.isDirectory()) {
				findFiles(file.getPath());
			}
		}
	}

	void initSystem();

	void readJSON(String path, String fileName);
}

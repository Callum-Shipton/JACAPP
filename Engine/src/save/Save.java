package save;

import java.io.Serializable;

import io.FileManager;

@SuppressWarnings("serial")
public abstract class Save implements Serializable {

	private static byte[] key;

	public static void init(String key) {
		Save.key = key.getBytes();
	}

	public void saveToSystem(int num) {
		FileManager.saveEncryptedFile("save" + num + ".ser", key, this);
	}

	public Save load(int num) throws Exception {

		return (Save) FileManager.loadEncryptedFile("save" + num + ".ser", key);
	}

}

package io;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

import static org.lwjgl.BufferUtils.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

import org.lwjgl.BufferUtils;

import logging.Logger;

public final class FileManager {

	private static final String TRANSFORMATION = "AES";

	public static void saveEncryptedFile(String fileLocation, byte[] key, Serializable object) {
		try (FileOutputStream fileOut = new FileOutputStream(fileLocation)) {
			// Length is 16 byte
			SecretKeySpec sks = new SecretKeySpec(key, TRANSFORMATION);

			// Create cipher
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, sks);
			SealedObject sealedObject = new SealedObject(object, cipher);

			// Wrap the output stream
			CipherOutputStream cos = new CipherOutputStream(fileOut, cipher);
			ObjectOutputStream outputStream = new ObjectOutputStream(cos);
			outputStream.writeObject(sealedObject);
			outputStream.close();
		} catch (IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| IOException e) {
			Logger.error(e);
		}
	}

	public static Object loadEncryptedFile(String fileLocation, byte[] key) throws Exception {
		Object o = null;

		try (FileInputStream fileIn = new FileInputStream(fileLocation)) {

			SecretKeySpec sks = new SecretKeySpec(key, TRANSFORMATION);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, sks);

			CipherInputStream cipherInputStream = new CipherInputStream(fileIn, cipher);
			ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
			SealedObject sealedObject;

			sealedObject = (SealedObject) inputStream.readObject();
			o = sealedObject.getObject(cipher);
			inputStream.close();
		} catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException | IOException e) {
			throw e;
		}

		return o;
	}

	public static List<String> getProperties(String propertiesLocation) {
		List<String> properties = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(propertiesLocation))) {
			String line = br.readLine();

			while (line != null) {
				properties.add(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			Logger.error(e);
		}
		return properties;
	}
	
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                InputStream source = FileManager.class.getResourceAsStream(resource);
                ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
    }
	
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
}

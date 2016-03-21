package display;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Image{
	private ByteBuffer buf;
	private int texWidth;
	private int texHeight;
	private int frameWidth;
	private int frameHeight;
	private String file;
	private int texID;

	public Image(String file, int fw, int fh) {
		frameWidth = fw;
		frameHeight = fh;
		this.file = file;
		this.setID(glGenTextures());
		this.buf = byteBuffer();
		bindTexture();
	}

	public ByteBuffer byteBuffer() {
		try {
			// Open the PNG file as an InputStream
			InputStream in = getClass().getResourceAsStream(file);
			// Link the PNG decoder to this stream
			PNGDecoder decoder = new PNGDecoder(in);

			// Get the width and height of the texture
			texWidth = decoder.getWidth();
			texHeight = decoder.getHeight();

			// Decode the PNG file in a ByteBuffer
			buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
			buf.flip();

			in.close();
			return buf;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;

	}
	
	private void bindTexture() {

		glBindTexture(GL_TEXTURE_2D, texID);

		// All RGB bytes are aligned to each other and each component is 1 byte
		// glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		// Setup the ST coordinate system
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		// Setup what to do when the texture has to be scaled
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		// Upload the texture data and generate mip maps (for scaling)
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texWidth, texHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);

	}

	public int getHeight() {
		return texHeight;
	}

	public int getWidth() {
		return texWidth;
	}
	public int getFHeight() {
		return frameHeight;
	}

	public int getFWidth() {
		return frameWidth;
	}
	
	public int getID(){
		return texID;
	}
	
	public void setID(int id){
		texID = id;
	}
}

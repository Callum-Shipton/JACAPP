package display;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Image {

	private ByteBuffer buf;
	private int texWidth;
	private int texHeight;
	private int frameWidth;
	private int frameHeight;
	private String file;
	private int texID;

	public Image(String file, int fw, int fh) {
		this.frameWidth = fw;
		this.frameHeight = fh;
		this.file = file;
		setID(glGenTextures());
		this.buf = byteBuffer();
		bindTexture();
	}

	private void bindTexture() {

		glBindTexture(GL_TEXTURE_2D, this.texID);

		// All RGB bytes are aligned to each other and each component is 1 byte
		// glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		// Setup the ST coordinate system
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		// Setup what to do when the texture has to be scaled
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		// Upload the texture data and generate mip maps (for scaling)
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.texWidth, this.texHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, this.buf);
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);

	}

	public ByteBuffer byteBuffer() {
		try {
			// Open the PNG file as an InputStream
			InputStream in = getClass().getResourceAsStream(this.file);
			// Link the PNG decoder to this stream
			PNGDecoder decoder = new PNGDecoder(in);

			// Get the width and height of the texture
			this.texWidth = decoder.getWidth();
			this.texHeight = decoder.getHeight();

			// Decode the PNG file in a ByteBuffer
			this.buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(this.buf, decoder.getWidth() * 4, Format.RGBA);
			this.buf.flip();

			in.close();
			return this.buf;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;

	}

	public int getFHeight() {
		return this.frameHeight;
	}

	public int getFWidth() {
		return this.frameWidth;
	}

	public int getHeight() {
		return this.texHeight;
	}

	public int getID() {
		return this.texID;
	}

	public int getWidth() {
		return this.texWidth;
	}

	public void setID(int id) {
		this.texID = id;
	}
}

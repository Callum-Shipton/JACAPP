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

import static org.lwjgl.stb.STBImage.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import logging.Logger;

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

        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Prepare image buffers */
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            /* Load image */
           // stbi_set_flip_vertically_on_load(true);
            buf = stbi_load("res/"+file, w, h, comp, 4);
            if (buf == null) {
                throw new RuntimeException("Failed to load a texture file!"
                                           + System.lineSeparator() + stbi_failure_reason());
            }

            /* Get width and height of image */
            texWidth = w.get();
            texHeight = h.get();
            
        }
        return buf;
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

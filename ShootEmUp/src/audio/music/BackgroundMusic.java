package audio.music;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_NO_ERROR;
import static org.lwjgl.openal.AL10.AL_ORIENTATION;
import static org.lwjgl.openal.AL10.AL_PITCH;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alGetError;
import static org.lwjgl.openal.AL10.alListener;
import static org.lwjgl.openal.AL10.alSource;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;

import audio.libraries.WaveData;

/**
 * $Id$
 * <p>
 * Lesson 3: Multiple Sources
 * </p>
 *
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 */
public class BackgroundMusic {

	public static ALDevice audioDevice;

	public static ALContext audioContext;

	/** Maximum data buffers we will need. */
	public static final int NUM_BUFFERS = 2;

	/** Maximum emissions we will need. */
	public static final int NUM_SOURCES = 2;

	/** Index of battle sound */
	public static final int MENU = 0;

	/** Index of gun 1 sound */
	public static final int MAIN = 1;

	/** Buffers hold sound data. */
	IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);

	/** Sources are points emitting sound. */
	IntBuffer source = BufferUtils.createIntBuffer(NUM_BUFFERS);

	/** Position of the source sound. */
	FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3 * NUM_BUFFERS);

	/*
	 * These are 3D cartesian vector coordinates. A structure or class would be
	 * a more flexible of handling these, but for the sake of simplicity we will
	 * just leave it as is.
	 */

	/** Velocity of the source sound. */
	FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3 * NUM_BUFFERS);

	/** Position of the listener. */
	FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });

	/** Velocity of the listener. */
	FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });

	/**
	 * Orientation of the listener. (first 3 elements are "at", second 3 are
	 * "up") Also note that these should be units of '1'.
	 */
	FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f });

	public BackgroundMusic() {
		// CRUCIAL!
		// any buffer that has data added, must be flipped to establish its
		// position and limits
		this.listenerPos.flip();
		this.listenerVel.flip();
		this.listenerOri.flip();
	}

	public void destoyAL() {
		audioContext.destroy();
		audioDevice.destroy();
		AL.destroy(audioContext);
	}

	public void initAL() {
		// Initialize OpenAL and clear the error bit.
		audioDevice = ALDevice.create();
		if (audioDevice == null) {
			throw new RuntimeException("Default audio device could not be opened");
		}
		// Unused
		// ALCCapabilities caps = audioDevice.getCapabilities();
		// String defaultDeviceSpecifier = alcGetString(0L,
		// ALC_DEFAULT_DEVICE_SPECIFIER);
		audioContext = ALContext.create();
		if (audioContext == null) {
			audioDevice.destroy();
			throw new RuntimeException("context could not be made/current");
		}

		alGetError();

		// Load the wav data.
		if (loadALData() == AL_FALSE) {
			System.out.println("Error loading data.");
			return;
		}

		setListenerValues();
	}

	/**
	 * void killALData()
	 *
	 * We have allocated memory for our buffers and sources which needs to be
	 * returned to the system. This function frees that memory.
	 */
	void killALData() {
		alDeleteSources(this.source);
		alDeleteBuffers(this.buffer);
	}

	/**
	 * boolean LoadALData()
	 *
	 * This function will load our sample data from the disk using the Alut
	 * utility and send the data into OpenAL as a buffer. A source is then also
	 * created to play that buffer.
	 */
	int loadALData() {
		// Load wav data into a buffers.
		alGenBuffers(this.buffer);

		if (alGetError() != AL_NO_ERROR) {
			return AL_FALSE;
		}

		WaveData waveFile = WaveData.create("Music/Menu.wav");
		alBufferData(this.buffer.get(MENU), waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();

		waveFile = WaveData.create("Music/Main.wav");
		alBufferData(this.buffer.get(MAIN), waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();

		// Bind buffers into audio sources.
		alGenSources(this.source);

		if (alGetError() != AL_NO_ERROR) {
			return AL_FALSE;
		}

		alSourcei(this.source.get(MENU), AL_BUFFER, this.buffer.get(MENU));
		alSourcef(this.source.get(MENU), AL_PITCH, 1.0f);
		alSourcef(this.source.get(MENU), AL_GAIN, 1.0f);
		alSource(this.source.get(MENU), AL_POSITION, (FloatBuffer) this.sourcePos.position(MENU * 3));
		alSource(this.source.get(MENU), AL_VELOCITY, (FloatBuffer) this.sourceVel.position(MENU * 3));
		alSourcei(this.source.get(MENU), AL_LOOPING, AL_TRUE);

		alSourcei(this.source.get(MAIN), AL_BUFFER, this.buffer.get(MAIN));
		alSourcef(this.source.get(MAIN), AL_PITCH, 1.0f);
		alSourcef(this.source.get(MAIN), AL_GAIN, 1.0f);
		alSource(this.source.get(MAIN), AL_POSITION, (FloatBuffer) this.sourcePos.position(MAIN * 3));
		alSource(this.source.get(MAIN), AL_VELOCITY, (FloatBuffer) this.sourceVel.position(MAIN * 3));
		alSourcei(this.source.get(MAIN), AL_LOOPING, AL_TRUE);

		// Do another error check and return.
		if (alGetError() == AL_NO_ERROR) {
			return AL_TRUE;
		}

		return AL_FALSE;
	}

	public void pause(int musicId) {
		alSourcePause(this.source.get(musicId));
	}

	public void play(int musicId) {
		alSourcePlay(this.source.get(musicId));
	}

	/**
	 * void setListenerValues()
	 *
	 * We already defined certain values for the Listener, but we need to tell
	 * OpenAL to use that data. This function does just that.
	 */
	void setListenerValues() {
		alListener(AL_POSITION, this.listenerPos);
		alListener(AL_VELOCITY, this.listenerVel);
		alListener(AL_ORIENTATION, this.listenerOri);
	}

	public void stop(int musicId) {
		alSourceStop(this.source.get(musicId));
	}

}

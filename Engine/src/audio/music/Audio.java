package audio.music;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
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
import static org.lwjgl.openal.AL10.alGetString;
import static org.lwjgl.openal.AL10.alListenerfv;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_memory;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;

import io.FileManager;
import logging.Logger;

/**
 * $Id$
 * <p>
 * Lesson 3: Multiple Sources
 * </p>
 *
 * @author Brian Matzon <brian@matzon.dk>
 * @version $Revision$
 */
public class Audio {

	public static long device;

	public static long context;

	/** Maximum data buffers we will need. */
	public static final int NUM_BUFFERS = 5;

	private final Map<String, Integer> bufferMap = new HashMap<>();
	private final Set<Integer> sourcesSet = new HashSet<>();

	/** Buffers hold sound data. */
	// TODO: change to allocated
	IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);

	/** Position of the source sound. */
	FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3 * NUM_BUFFERS);

	/*
	 * These are 3D cartesian vector coordinates. A structure or class would be a
	 * more flexible of handling these, but for the sake of simplicity we will just
	 * leave it as is.
	 */

	/** Velocity of the source sound. */
	FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3 * NUM_BUFFERS);

	/** Position of the listener. */
	FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });

	/** Velocity of the listener. */
	FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });

	/**
	 * Orientation of the listener. (first 3 elements are "at", second 3 are "up")
	 * Also note that these should be units of '1'.
	 */
	FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f });

	public Audio() {
		// CRUCIAL!
		// any buffer that has data added, must be flipped to establish its
		// position and limits
		listenerPos.flip();
		listenerVel.flip();
		listenerOri.flip();
	}

	public void destoyAL() {
		int[] array = sourcesSet.stream().mapToInt(i -> i).toArray();
		alDeleteSources(IntBuffer.wrap(array));
		alDeleteBuffers(buffer);
		alcDestroyContext(context);
		alcCloseDevice(device);
	}

	public void initAL() {
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device = alcOpenDevice(defaultDeviceName);

		int[] attributes = { 0 };
		context = alcCreateContext(device, attributes);
		alcMakeContextCurrent(context);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

		alGenBuffers(buffer);
		alGetError();

		// Load the wav data.
		if (loadALData() == AL_FALSE) {
			Logger.error("Error loading audio data.");
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
		alDeleteBuffers(buffer);
	}

	/**
	 * boolean LoadALData()
	 *
	 * This function will load our sample data from the disk using the Alut utility
	 * and send the data into OpenAL as a buffer. A source is then also created to
	 * play that buffer.
	 */
	int loadALData() {

		findFiles("res/Music");

		// Do another error check and return.
		if (alGetError() == AL_NO_ERROR) {
			return AL_TRUE;
		}

		return AL_FALSE;
	}

	private void loadAudioFile(File file) {
		// Allocate space to store return information from the function
		try (MemoryStack stack = stackPush()) {
			IntBuffer channelsBuffer = stackMallocInt(1);
			IntBuffer sampleRateBuffer = stackMallocInt(1);

			ByteBuffer buf;
			try {
				buf = FileManager.ioResourceToByteBuffer(file.getPath(), 4 * 1024 * 1024);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			ShortBuffer rawAudioBuffer = stb_vorbis_decode_memory(buf, channelsBuffer, sampleRateBuffer);

			if (rawAudioBuffer == null) {
				throw new RuntimeException("Failed to load a music file!");
			}

			// Retreive the extra information that was stored in the buffers by the function
			int channels = channelsBuffer.get();
			int sampleRate = sampleRateBuffer.get();

			// Find the correct OpenAL format
			int format = -1;
			if (channels == 1) {
				format = AL_FORMAT_MONO16;
			} else if (channels == 2) {
				format = AL_FORMAT_STEREO16;
			}

			// Send the data to OpenAL
			alBufferData(buffer.get(bufferMap.size()), format, rawAudioBuffer, sampleRate);

			bufferMap.put(file.getName(), bufferMap.size());
		}
	}

	public void pause(int musicId) {
		checkALError();
		alSourcePause(musicId);
		checkALError();

	}

	public void play(int musicId) {
		checkALError();
		alSourcePlay(musicId);
		checkALError();
	}

	public int createSourceFromFile(String audioFile, Boolean looping) {
		Integer buf = bufferMap.get(audioFile);
		if (buf == null) {
			Logger.warn("Nonexistant sound file: " + audioFile);
		}
		return createSourceFromBuffer(buf, looping);
	}

	private int createSourceFromBuffer(int i, Boolean looping) {
		IntBuffer sourceBuf = BufferUtils.createIntBuffer(1);
		alGenSources(sourceBuf);
		int sourceId = sourceBuf.get(0);
		alSourcei(sourceId, AL_BUFFER, buffer.get(i));
		alSourcef(sourceId, AL_PITCH, 1.0f);
		alSourcef(sourceId, AL_GAIN, 1.0f);
		alSourcei(sourceId, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
		return sourceId;
	}

	/**
	 * void setListenerValues()
	 *
	 * We already defined certain values for the Listener, but we need to tell
	 * OpenAL to use that data. This function does just that.
	 */
	void setListenerValues() {
		alListenerfv(AL_POSITION, listenerPos);
		alListenerfv(AL_VELOCITY, listenerVel);
		alListenerfv(AL_ORIENTATION, listenerOri);
	}

	public void stop(int musicId) {
		checkALError();
		alSourceStop(musicId);
		checkALError();
	}

	static void checkALError() {
		int err = alGetError();
		if (err != AL_NO_ERROR) {
			throw new RuntimeException(alGetString(err));
		}
	}

	private void findFiles(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				loadAudioFile(file);
			}
			if (file.isDirectory()) {
				findFiles(file.getPath());
			}
		}
	}

}

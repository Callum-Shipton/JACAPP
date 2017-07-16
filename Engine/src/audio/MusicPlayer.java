package audio;

import audio.music.Audio;
import input.Keyboard;
import loop.Loop;

public class MusicPlayer {

	private Audio audio;
	private int backgroundMusic = Audio.MENU;
	private boolean pause = false;

	public MusicPlayer() {
		audio = new Audio();
		audio.initAL();
	}

	public void changeCurrentMusic(int index) {
		audio.stop(backgroundMusic);
		backgroundMusic = index;
		if (!pause) {
			playBackgroundMusic();
		}
	}

	public void destroy() {
		audio.destoyAL();
	}

	public void pause() {
		if (pause) {
			audio.play(backgroundMusic);
		} else {
			audio.pause(backgroundMusic);
		}
		pause = !pause;
	}

	public void playBackgroundMusic() {
		audio.play(backgroundMusic);
	}

	public void play(String sound) {
		audio.play(Audio.SHOOT);
	}

	public void stopBackgroundMusic() {
		audio.stop(backgroundMusic);
	}

	public void update() {
		if (Keyboard.getKey(Loop.getKeys().mute) == 1) {
			Keyboard.setKey(Loop.getKeys().mute);
			pause();
		}
	}
}

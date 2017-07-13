package audio;

import audio.music.BackgroundMusic;
import input.Keyboard;
import loop.Loop;

public class MusicPlayer {

	private BackgroundMusic backgroundMusic;
	private int currentMusic = BackgroundMusic.MENU;
	private boolean pause = false;

	public MusicPlayer() {
		backgroundMusic = new BackgroundMusic();
		backgroundMusic.initAL();
	}

	public void changeCurrentMusic(int index) {
		backgroundMusic.stop(currentMusic);
		currentMusic = index;
		if (!pause) {
			play();
		}
	}

	public void destroy() {
		backgroundMusic.destoyAL();
	}

	public void pause() {
		if (pause) {
			backgroundMusic.play(currentMusic);
		} else {
			backgroundMusic.pause(currentMusic);
		}
		pause = !pause;
	}

	public void play() {
		backgroundMusic.play(currentMusic);
	}

	public void stop() {
		backgroundMusic.stop(currentMusic);
	}

	public void update() {
		if (Keyboard.getKey(Loop.getKeys().mute) == 1) {
			Keyboard.setKey(Loop.getKeys().mute);
			pause();
		}
	}
}

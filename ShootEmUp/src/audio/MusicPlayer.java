package audio;

import audio.music.BackgroundMusic;
import input.Keyboard;
import main.ShootEmUp;

public class MusicPlayer {

	private BackgroundMusic backgroundMusic;
	private int currentMusic = BackgroundMusic.MENU;
	private boolean pause = false;

	public MusicPlayer() {
		this.backgroundMusic = new BackgroundMusic();
		this.backgroundMusic.initAL();
	}

	public void changeCurrentMusic(int index) {
		this.backgroundMusic.stop(this.currentMusic);
		this.currentMusic = index;
		if (!this.pause) {
			play();
		}
	}

	public void destroy() {
		this.backgroundMusic.destoyAL();
	}

	public void pause() {
		if (this.pause) {
			this.backgroundMusic.play(this.currentMusic);
		} else {
			this.backgroundMusic.pause(this.currentMusic);
		}
		this.pause = !this.pause;
	}

	public void play() {
		this.backgroundMusic.play(this.currentMusic);
	}

	public void stop() {
		this.backgroundMusic.stop(this.currentMusic);
	}

	public void update() {
		if (Keyboard.getKey(ShootEmUp.getKeys().mute) == 1) {
			Keyboard.setKey(ShootEmUp.getKeys().mute);
			pause();
		}
	}
}

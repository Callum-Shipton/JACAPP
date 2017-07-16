package audio;

import audio.music.Audio;
import input.Keyboard;
import loop.Loop;

public class MusicPlayer {

	private final Audio audio;
	private int backgroundMusic;
	private boolean pause = false;
	public final int menu;
	public final int main;

	public MusicPlayer() {
		audio = new Audio();
		audio.initAL();
		menu = audio.createSourceFromFile("Menu.ogg", true);
		main = audio.createSourceFromFile("Main.ogg", true);
		backgroundMusic = menu;
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

	public int createSource(String sound, Boolean looping) {
		return audio.createSourceFromFile(sound, looping);
	}

	public void playSource(int source) {
		audio.play(source);
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

package audio;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;

import audio.music.BackgroundMusic;
import input.Keyboard;

public class MusicPlayer {
	
	private BackgroundMusic backgroundMusic;
	private int currentMusic = BackgroundMusic.MENU;
	private boolean pause = false;
	
	public MusicPlayer(){
		backgroundMusic = new BackgroundMusic();
		backgroundMusic.initAL();
	}
	
	public void update(){
		if (Keyboard.getKey(GLFW_KEY_M) == 1) {
			Keyboard.setKey(GLFW_KEY_M);
			pause();
		}
	}
	
	public void play(){
		backgroundMusic.play(currentMusic);
	}
	
	public void stop(){
		backgroundMusic.stop(currentMusic);
	}
	
	public void destroy(){
		backgroundMusic.destoyAL();
	}
	
	public void pause(){
		if (pause) {
			backgroundMusic.play(currentMusic);
		} else {
			backgroundMusic.pause(currentMusic);
		}
		pause = !pause;
	}
	
	public void changeCurrentMusic(int index){
		backgroundMusic.stop(currentMusic);
		currentMusic = index;
		if (!pause) {
			play();
		}
	}
}

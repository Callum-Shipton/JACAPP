package components.audio;

import java.util.HashSet;
import java.util.Set;

import audio.MusicPlayer;
import components.Component;
import components.TypeComponent;
import entity.Entity;
import main.ShootEmUp;

public abstract class BaseAudio extends Component implements AudioComponent {
	Set<Integer> audioPlaying = new HashSet<>();
	MusicPlayer audio = ShootEmUp.getMusicPlayer();
	
	@Override
	public void destroy(Entity e) {
	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.AUDIO;
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}

}

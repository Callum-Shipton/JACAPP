package components.audio;

import java.util.HashSet;
import java.util.Set;

import audio.MusicPlayer;
import components.Component;
import components.TypeComponent;
import main.ShootEmUp;

public abstract class BaseAudio extends Component implements AudioComponent {
	transient Set<Integer> audioPlaying = new HashSet<>();
	transient MusicPlayer audio = ShootEmUp.getMusicPlayer();

	public BaseAudio() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.AUDIO;
	}

	@Override
	public void update() {
	}

}

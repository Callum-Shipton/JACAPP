package components.audio;

import java.util.HashSet;
import java.util.Set;

import components.Component;
import components.TypeComponent;
import entity.Entity;

public abstract class BaseAudio extends Component implements AudioComponent {
	Set<Integer> audioPlaying = new HashSet<>();

	@Override
	public void destroy(Entity e) {
		// TODO Auto-generated method stub

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

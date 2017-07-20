package components.audio;

import java.util.EnumMap;
import java.util.Map;

import components.Message;
import components.MessageId;

public class EventAudio extends BaseAudio {
	Map<MessageId, String> sounds = new EnumMap<>(MessageId.class);

	public EventAudio(Map<MessageId, String> sounds) {
		this.sounds = sounds;
	}

	public EventAudio(EventAudio eventAudio) {
		this(new EnumMap<MessageId, String>(eventAudio.sounds));
	}

	@Override
	public void receive(Message m) {
		String sound = sounds.get(m.getId());
		if (sound != null) {
			int soundId = audio.createSource(sound, false);
			if (soundId != -1) {
				audio.playSource(soundId);
			}
		}
	}
}

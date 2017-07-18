package components.audio;

import java.util.EnumMap;
import java.util.Map;

import components.Message;
import components.MessageId;
import entity.Entity;
import logging.Logger;

public class EventAudio extends BaseAudio {
	Map<MessageId, String> sounds = new EnumMap<>(MessageId.class);

	public EventAudio(Map<MessageId, String> sounds) {
		this.sounds = sounds;
	}

	@Override
	public void receive(Message m, Entity e) {
		String sound = sounds.get(m);
		if (sound != null) {
			int soundId = audio.createSource(sound, false);
			if (soundId != -1) {
				audio.playSource(soundId);
			}
		} else {
			Logger.warn("No sound mapped to message: " + m.toString());
		}
	}
}

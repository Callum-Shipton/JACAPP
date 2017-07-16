package components.audio;

import java.util.HashMap;
import java.util.Map;

import audio.MusicPlayer;
import components.Message;
import entity.Entity;
import logging.Logger;
import main.ShootEmUp;

public class EventAudio extends BaseAudio {
	Map<Message, String> sounds = new HashMap<>();
	MusicPlayer audio = ShootEmUp.getMusicPlayer();

	public EventAudio(Map<Message, String> sounds) {
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

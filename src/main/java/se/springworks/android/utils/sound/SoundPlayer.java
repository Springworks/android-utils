package se.springworks.android.utils.sound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.HashMap;

public class SoundPlayer implements ISoundPlayer {

	@Inject
	private Context context;

	private SoundPool soundPool;

	private HashMap<Object, Integer> soundMap = new HashMap<Object, Integer>();
	private HashMap<Object, Float> volumeMap = new HashMap<Object, Float>();

	private float globalVolume = 1.0f;

	@Inject
	public SoundPlayer(@Assisted int maxStreams) {
		soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
	}

	@Override
	public void setVolume(float volume) {
		this.globalVolume = volume;
	}

	@Override
	public void add(AssetFileDescriptor afd, Object key) {
		if (soundMap.containsKey(key)) {
			return;
		}
		int soundId = soundPool.load(afd, 1);
		soundMap.put(key, soundId);
		volumeMap.put(key, 1.0f);
	}

	@Override
	public void add(String path, Object key) {
		if (soundMap.containsKey(key)) {
			return;
		}
		int soundId = soundPool.load(path, 1);
		soundMap.put(key, soundId);
		volumeMap.put(key, 1.0f);
	}

	@Override
	public void add(int resId, Object key) {
		if (soundMap.containsKey(key)) {
			return;
		}
		int soundId = soundPool.load(context, resId, 1);
		soundMap.put(key, soundId);
		volumeMap.put(key, 1.0f);
	}

	@Override
	public void add(int resId, Object key, float volume) {
		if (soundMap.containsKey(key)) {
			return;
		}
		int soundId = soundPool.load(context, resId, 1);
		soundMap.put(key, soundId);
		volumeMap.put(key, volume);
	}

	@Override
	public void replace(String path, Object key) {
		remove(key);
		add(path, key);
	}

	@Override
	public void replace(AssetFileDescriptor afd, Object key) {
		remove(key);
		add(afd, key);
	}

	@Override
	public void replace(int resId, Object key) {
		remove(key);
		add(resId, key);
	}


	@Override
	public void remove(Object key) {
		if (soundMap.containsKey(key)) {
			soundPool.unload(soundMap.get(key));
		}
		volumeMap.remove(key);
	}

	private float getVolume(Object key) {
		if (volumeMap.containsKey(key)) {
			return volumeMap.get(key);
		}
		return 1.0f;
	}

	@Override
	public void play(Object key) {
		play(key, getVolume(key), 0, 1.0f);
	}

	@Override
	public void play(Object key, float volume) {
		play(key, volume, 0, 1.0f);
	}

	@Override
	public void loop(Object key) {
		play(key, getVolume(key), -1, 1.0f);
	}

	@Override
	public void loop(Object key, int loops) {
		play(key, getVolume(key), loops, 1.0f);
	}

	@Override
	public void stop(Object key) {
		if (soundMap.containsKey(key)) {
			soundPool.stop(soundMap.get(key));
		}
	}

	/**
	 * Plays a sound
	 *
	 * @param key    Id of the sound to play
	 * @param volume Volume in percentage of current max volume to play sound
	 * @param loop   Number of times to loop (-1 forever)
	 * @param speed  Speed of playback (1.0 normal speed). Rnage 0.5 to 2.0
	 */
	private void play(Object key, float volume, int loop, float speed) {
		if (soundMap.containsKey(key)) {
			AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
			float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float finalVolume = globalVolume * volume * (streamVolumeCurrent / streamVolumeMax);
			soundPool.play(soundMap.get(key), finalVolume, finalVolume, 1, loop, speed);
		}
	}

	@Override
	public void pause() {
		soundPool.autoPause();
	}

	@Override
	public void resume() {
		soundPool.autoResume();
	}
}

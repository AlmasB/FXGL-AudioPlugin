package com.gluonhq.charm.down.plugins.android.audio;

import android.media.SoundPool;
import com.gluonhq.charm.down.plugins.audio.Audio;
import com.gluonhq.charm.down.plugins.audio.AudioType;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public final class AndroidSound extends Audio {

    private final SoundPool pool;
    private final int soundID;

    private boolean looping = false;
    private float volume = 1.0f;
    private int lastStreamID = 0;

    public AndroidSound(String fullName, SoundPool pool, int soundID) {
        super(AudioType.SOUND, fullName);

        this.pool = pool;
        this.soundID = soundID;
    }

    @Override
    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    @Override
    public void setVolume(double volume) {
        this.volume = (float) volume;
    }

    @Override
    public void setOnFinished(Runnable action) {

    }

    @Override
    public void play() {
        lastStreamID = pool.play(soundID, volume, volume, 1, looping ? -1 : 0, 1);
    }

    @Override
    public void pause() {
        pool.pause(lastStreamID);
    }

    @Override
    public void stop() {
        pool.stop(lastStreamID);
    }

    @Override
    public void dispose() {
        pool.unload(soundID);
    }
}

package com.gluonhq.charm.down.plugins.desktop;

import com.gluonhq.impl.charm.down.plugins.Audio;
import com.gluonhq.impl.charm.down.plugins.AudioType;
import javafx.scene.media.AudioClip;

public class DesktopSound extends Audio {

    private final AudioClip clip;

    public DesktopSound(String fullName, AudioClip clip) {
        super(AudioType.SOUND, fullName);

        this.clip = clip;
    }

    @Override
    public void setLooping(boolean looping) {
        clip.setCycleCount(looping ? Integer.MAX_VALUE : 1);
    }

    @Override
    public void setVolume(double volume) {
        clip.setVolume(volume);
    }

    @Override
    public void setOnFinished(Runnable action) {

    }

    @Override
    public void play() {
        clip.play();
    }

    @Override
    public void pause() {
        clip.stop();
    }

    @Override
    public void stop() {
        clip.stop();
    }

    @Override
    public void dispose() {

    }
}

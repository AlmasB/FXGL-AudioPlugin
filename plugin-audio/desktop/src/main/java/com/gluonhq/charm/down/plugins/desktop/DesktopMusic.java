package com.gluonhq.charm.down.plugins.desktop;

import com.gluonhq.impl.charm.down.plugins.Audio;
import com.gluonhq.impl.charm.down.plugins.AudioType;
import javafx.scene.media.MediaPlayer;

public class DesktopMusic extends Audio {

    private final MediaPlayer mediaPlayer;

    public DesktopMusic(String fullName, MediaPlayer mediaPlayer) {
        super(AudioType.MUSIC, fullName);

        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void setLooping(boolean looping) {
        mediaPlayer.setCycleCount(looping ? Integer.MAX_VALUE : 1);
    }

    @Override
    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    @Override
    public void setOnFinished(Runnable action) {
        mediaPlayer.setOnEndOfMedia(action);
    }

    @Override
    public void play() {
        mediaPlayer.play();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
    }

    @Override
    public void dispose() {
        mediaPlayer.dispose();
    }
}

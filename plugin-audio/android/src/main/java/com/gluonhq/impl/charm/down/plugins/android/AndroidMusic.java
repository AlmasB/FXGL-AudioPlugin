package com.gluonhq.impl.charm.down.plugins.android;

import android.media.AudioManager;
import android.media.MediaPlayer;
import com.gluonhq.impl.charm.down.plugins.Audio;

import java.io.FileInputStream;
import java.net.URL;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class AndroidMusic extends Audio {

    private MediaPlayer mediaPlayer;

    public AndroidMusic(FileInputStream stream, URL url) {
        super(url);

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(stream.getFD());
            stream.close();

            mediaPlayer.prepare();
            //mediaPlayer.start();

        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void play() {
        mediaPlayer.start();
    }
}

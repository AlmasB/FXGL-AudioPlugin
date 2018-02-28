package com.gluonhq.charm.down.plugins.android;

import com.gluonhq.charm.down.plugins.AudioService;
import android.util.Log;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class AndroidAudioService implements AudioService {
    @Override
    public void playMusic(String assetName) {
        Log.i("AndroidAudioService", "playMusic()");
        System.out.println("Playing music on android: " + assetName);
    }

    @Override
    public void playSound(String assetName) {
        Log.i("AndroidAudioService", "playSound()");
        System.out.println("Playing sound on android: " + assetName);
    }
}

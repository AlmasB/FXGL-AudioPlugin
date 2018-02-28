package com.gluonhq.charm.down.plugins.desktop;

import com.gluonhq.charm.down.plugins.AudioService;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class DesktopAudioService implements AudioService {

    @Override
    public void playMusic(String assetName) {
        System.out.println("Playing music on desktop: " + assetName);
    }

    @Override
    public void playSound(String assetName) {
        System.out.println("Playing sound on desktop: " + assetName);
    }
}

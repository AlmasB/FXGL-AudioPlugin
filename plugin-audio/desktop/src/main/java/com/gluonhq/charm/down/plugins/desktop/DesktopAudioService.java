package com.gluonhq.charm.down.plugins.desktop;

import com.gluonhq.impl.charm.down.plugins.Audio;
import com.gluonhq.charm.down.plugins.AudioService;
import com.gluonhq.impl.charm.down.plugins.AudioType;

import java.net.URL;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class DesktopAudioService implements AudioService {

    @Override
    public Audio createAudio(AudioType type, String resourceName) {
        return null;
    }

    @Override
    public void releaseAudio(Audio audio) {

    }

//    private Map<String, MediaPlayer> cachedPlayers = new HashMap<>();
//
//    @Override
//    public void setGlobalMusicVolume(double volume) {
//        cachedPlayers.forEach((name, player) -> player.setVolume(volume));
//    }
//
//    @Override
//    public void playMusic(String fileName, int numTimes, double leftVolume, double rightVolume) {
//        Media media = new Media(getClass().getResource(fileName).toExternalForm());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//
//        mediaPlayer.setCycleCount(numTimes);
//        mediaPlayer.setVolume(leftVolume);
//        mediaPlayer.play();
//    }
//
//    @Override
//    public void pauseMusic(String fileName) {
//        if (cachedPlayers.containsKey(fileName)) {
//            cachedPlayers.get(fileName).pause();
//        }
//    }
//
//    @Override
//    public void stopMusic(String fileName) {
//        if (cachedPlayers.containsKey(fileName)) {
//            cachedPlayers.get(fileName).stop();
//        }
//    }
//
//    @Override
//    public void playSound(String fileName, int numTimes, double leftVolume, double rightVolume) {
//
//    }
}

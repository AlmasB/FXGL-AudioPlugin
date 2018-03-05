package com.gluonhq.charm.down.plugins.desktop;

import com.gluonhq.impl.charm.down.plugins.Audio;
import com.gluonhq.charm.down.plugins.AudioService;
import com.gluonhq.impl.charm.down.plugins.AudioType;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class DesktopAudioService implements AudioService {

    private Map<String, Audio> cache = new HashMap<>();

    @Override
    public Audio createAudio(AudioType type, String resourceName) {
        if (cache.containsKey(resourceName)) {
            return cache.get(resourceName);
        }

        Audio audio;

        if (type == AudioType.MUSIC) {
            Media media = new Media(getClass().getResource(resourceName).toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            audio = new DesktopMusic(resourceName, mediaPlayer);
        } else {
            audio = new DesktopSound(resourceName, new AudioClip(getClass().getResource(resourceName).toExternalForm()));
        }

        cache.put(resourceName, audio);

        return audio;
    }

    @Override
    public void releaseAudio(Audio audio) {
        cache.remove(audio.getFullName());
        audio.dispose();
    }
}

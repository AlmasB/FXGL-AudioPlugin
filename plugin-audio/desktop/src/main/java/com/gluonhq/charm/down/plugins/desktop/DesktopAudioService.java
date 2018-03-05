package com.gluonhq.charm.down.plugins.desktop;

import com.gluonhq.charm.down.plugins.audio.Audio;
import com.gluonhq.charm.down.plugins.audio.AudioType;
import com.gluonhq.charm.down.plugins.desktop.audio.DesktopMusic;
import com.gluonhq.charm.down.plugins.desktop.audio.DesktopSound;
import com.gluonhq.impl.charm.down.plugins.DefaultAudioService;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public final class DesktopAudioService extends DefaultAudioService {

    @Override
    protected Audio loadAudioImpl(AudioType type, String resourceName) {
        String url = getClass().getResource(resourceName).toExternalForm();

        if (type == AudioType.MUSIC) {
            return new DesktopMusic(resourceName, new MediaPlayer(new Media(url)));
        } else {
            return new DesktopSound(resourceName, new AudioClip(url));
        }
    }
}

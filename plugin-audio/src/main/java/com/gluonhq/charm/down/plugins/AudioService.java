package com.gluonhq.charm.down.plugins;

import com.gluonhq.charm.down.plugins.audio.Audio;
import com.gluonhq.charm.down.plugins.audio.AudioType;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public interface AudioService {

    Audio loadAudio(AudioType type, String resourceName);

    void unloadAudio(Audio audio);
}

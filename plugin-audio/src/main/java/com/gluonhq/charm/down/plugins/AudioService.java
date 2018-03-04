package com.gluonhq.charm.down.plugins;

import com.gluonhq.impl.charm.down.plugins.Audio;
import com.gluonhq.impl.charm.down.plugins.AudioType;

import java.net.URL;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public interface AudioService {

    Audio createAudio(AudioType type, String resourceName);

    void releaseAudio(Audio audio);
}

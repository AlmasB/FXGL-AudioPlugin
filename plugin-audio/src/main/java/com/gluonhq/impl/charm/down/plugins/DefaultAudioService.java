package com.gluonhq.impl.charm.down.plugins;

import com.gluonhq.charm.down.plugins.AudioService;
import com.gluonhq.charm.down.plugins.audio.Audio;
import com.gluonhq.charm.down.plugins.audio.AudioType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public abstract class DefaultAudioService implements AudioService {

    // TODO: use Charm Down cache?
    private Map<String, Audio> cache = new HashMap<>();

    @Override
    public final Audio loadAudio(AudioType type, String resourceName) {
        Audio audio = cache.get(resourceName);

        if (audio == null) {
            try {
                audio = loadAudioImpl(type, resourceName);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load audio", e);
            }

            cache.put(resourceName, audio);
        }

        return audio;
    }

    @Override
    public final void unloadAudio(Audio audio) {
        cache.remove(audio.getResourceName());
        audio.dispose();
    }

    protected abstract Audio loadAudioImpl(AudioType type, String resourceName) throws Exception;
}

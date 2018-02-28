package com.gluonhq.charm.down.plugins;

import com.gluonhq.charm.down.DefaultServiceFactory;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class AudioServiceFactory extends DefaultServiceFactory<AudioService> {

    public AudioServiceFactory() {
        super(AudioService.class);
    }
}

package com.gluonhq.charm.down.plugins.audio;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public abstract class Audio {

    private final AudioType type;
    private final String resourceName;

    public Audio(AudioType type, String resourceName) {
        this.type = type;
        this.resourceName = resourceName;
    }

    public AudioType getType() {
        return type;
    }

    public String getResourceName() {
        return resourceName;
    }

    public abstract void setLooping(boolean looping);

    public abstract void setVolume(double volume);

    public abstract void setOnFinished(Runnable action);

    public abstract void play();

    public abstract void pause();

    public abstract void stop();

    /**
     * Do NOT call directly.
     * This is called automatically by the service managing this audio.
     */
    public abstract void dispose();
}

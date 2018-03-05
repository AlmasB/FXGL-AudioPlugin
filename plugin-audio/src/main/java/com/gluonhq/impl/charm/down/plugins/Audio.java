package com.gluonhq.impl.charm.down.plugins;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public abstract class Audio {

    private final AudioType type;
    private final String fullName;

    public Audio(AudioType type, String fullName) {
        this.type = type;
        this.fullName = fullName;
    }

    public AudioType getType() {
        return type;
    }

    public String getFullName() {
        return fullName;
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

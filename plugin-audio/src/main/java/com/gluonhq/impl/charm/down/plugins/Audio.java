package com.gluonhq.impl.charm.down.plugins;

import java.net.URL;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public abstract class Audio {

    private static final Runnable NO_OP = () -> {};

    private int cycleCount = 1;
    private Runnable onFinished = NO_OP;
    private double volume = 1.0;
    //private String fileName;
    private final URL url;

    public Audio(URL url) {
        this.url = url;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }

    public Runnable getOnFinished() {
        return onFinished;
    }

    public void setOnFinished(Runnable onFinished) {
        this.onFinished = onFinished;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }

    public URL getURL() {
        return url;
    }

    public abstract void play();
}

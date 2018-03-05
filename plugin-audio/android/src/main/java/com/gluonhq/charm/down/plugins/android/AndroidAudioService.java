package com.gluonhq.charm.down.plugins.android;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.android.audio.AndroidMusic;
import com.gluonhq.charm.down.plugins.android.audio.AndroidSound;
import com.gluonhq.charm.down.plugins.audio.Audio;
import com.gluonhq.charm.down.plugins.audio.AudioType;
import com.gluonhq.charm.down.plugins.StorageService;
import com.gluonhq.impl.charm.down.plugins.DefaultAudioService;

import java.io.*;
import java.net.URL;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public final class AndroidAudioService extends DefaultAudioService {

    private SoundPool pool;

    private File privateStorage;

    // TODO: web resource
    @Override
    protected Audio loadAudioImpl(AudioType type, String resourceName) throws Exception {
        if (privateStorage == null) {
            privateStorage = setUpDirectories();
        }

        String subDirName = type == AudioType.MUSIC ? "music/" : "sounds/";
        String fileName = resourceName.substring(resourceName.lastIndexOf("/")+1, resourceName.length());
        String fullName = privateStorage.getAbsolutePath() + "/assets/" + subDirName + fileName;

        File outputFile = new File(fullName);

        if (!outputFile.exists()) {
            copyFile(getClass().getResource(resourceName), outputFile);
        }

        if (type == AudioType.MUSIC) {
            return loadMusic(resourceName, outputFile);
        } else {
            return loadSound(resourceName, outputFile);
        }
    }

    private File setUpDirectories() {
        File storage = Services.get(StorageService.class)
                .flatMap(service -> service.getPrivateStorage())
                .orElseThrow(() -> new RuntimeException("Error accessing Private Storage folder"));

        File assetsDir = new File(storage, "assets");
        if (!assetsDir.exists()) {
            assetsDir.mkdir();
        }

        File musicDir = new File(assetsDir, "music");
        if (!musicDir.exists()) {
            musicDir.mkdir();
        }

        File soundsDir = new File(assetsDir, "sounds");
        if (!soundsDir.exists()) {
            soundsDir.mkdir();
        }

        return storage;
    }

    private Audio loadMusic(String resourceName, File file) throws Exception {
        FileInputStream stream = new FileInputStream(file);

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(stream.getFD());
        mediaPlayer.prepare();

        stream.close();

        return new AndroidMusic(resourceName, mediaPlayer);
    }

    private Audio loadSound(String resourceName, File file) throws Exception {
        if (pool == null)
            pool = createPool();

        FileInputStream stream = new FileInputStream(file);

        int soundID = pool.load(stream.getFD(), 0, file.length(), 1);

        stream.close();

        return new AndroidSound(resourceName, pool, soundID);
    }

    private SoundPool createPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        return new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // this is arbitrary, but it should be a reasonable amount
                .setMaxStreams(5)
                .build();
    }

    private void copyFile(URL url, File outputFile) throws Exception {
        try (InputStream input = url.openStream()) {
            if (input == null) {
                throw new RuntimeException("Internal copy failed: input stream for " + url + " is null");
            }

            try (OutputStream output = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                output.flush();
            }
        }
    }
}

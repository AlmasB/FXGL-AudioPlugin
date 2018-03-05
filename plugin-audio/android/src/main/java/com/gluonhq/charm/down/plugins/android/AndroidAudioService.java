package com.gluonhq.charm.down.plugins.android;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import com.gluonhq.charm.down.Services;
import com.gluonhq.impl.charm.down.plugins.Audio;
import com.gluonhq.charm.down.plugins.AudioService;
import com.gluonhq.impl.charm.down.plugins.AudioType;
import com.gluonhq.charm.down.plugins.StorageService;
import com.gluonhq.impl.charm.down.plugins.android.AndroidMusic;
import com.gluonhq.impl.charm.down.plugins.android.AndroidSound;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class AndroidAudioService implements AudioService {

    private SoundPool pool;

    // TODO: use Charm Down cache?
    private Map<String, Audio> cache = new HashMap<>();
    private File privateStorage;

    // TODO: web resource
    // TODO: handle exceptions
    @Override
    public Audio createAudio(AudioType type, String resourceName) {
        if (privateStorage == null) {
            setUpDirectories();
        }

        String subDirName = type == AudioType.MUSIC ? "music/" : "sounds/";

        String fileName = resourceName.substring(resourceName.lastIndexOf("/")+1, resourceName.length());

        // assume this is unique, is it?
        String fullName = privateStorage.getAbsolutePath() + "/assets/" + subDirName + fileName;

        if (cache.containsKey(fullName)) {
            return cache.get(fullName);
        }

        File outputFile = new File(fullName);

        if (!outputFile.exists()) {
            copyFile(getClass().getResource(resourceName), outputFile);
        }

        try {
            Audio audio;

            FileInputStream stream = new FileInputStream(outputFile);

            if (type == AudioType.MUSIC) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(stream.getFD());

                mediaPlayer.prepare();

                audio = new AndroidMusic(fullName, mediaPlayer);
            } else {

                if (pool == null) {
                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build();

                    pool = new SoundPool.Builder()
                            .setAudioAttributes(audioAttributes)
                            .setMaxStreams(5)
                            .build();
                }

                audio = new AndroidSound(fullName, pool, pool.load(stream.getFD(), 0, outputFile.length(), 1));
            }

            stream.close();

            cache.put(fullName, audio);

            return audio;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpDirectories() {
        privateStorage = Services.get(StorageService.class)
                .flatMap(service -> service.getPrivateStorage())
                .orElseThrow(() -> new RuntimeException("Error accessing Private Storage folder"));

        File assetsDir = new File(privateStorage, "assets");
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
    }

    @Override
    public void releaseAudio(Audio audio) {
        cache.remove(audio.getFullName());
        audio.dispose();
    }

    private boolean copyFile(URL fileURL, File outputFile)  {
        try (InputStream myInput = fileURL.openStream()) {
            if (myInput == null) {
                return false;
            }
            try (OutputStream myOutput = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                return true;
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return false;
    }
}

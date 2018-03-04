package com.gluonhq.charm.down.plugins.android;

import com.gluonhq.charm.down.Services;
import com.gluonhq.impl.charm.down.plugins.Audio;
import com.gluonhq.charm.down.plugins.AudioService;
import com.gluonhq.impl.charm.down.plugins.AudioType;
import com.gluonhq.charm.down.plugins.StorageService;
import com.gluonhq.impl.charm.down.plugins.android.AndroidMusic;
import com.gluonhq.impl.charm.down.plugins.android.AndroidSound;

import java.io.*;
import java.net.URL;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class AndroidAudioService implements AudioService {

    private File privateStorage;

    // TODO: web resource
    @Override
    public Audio createAudio(AudioType type, String resourceName) {
        if (privateStorage == null) {
            setUpDirectories();
        }

        String subDirName = type == AudioType.MUSIC ? "music/" : "sounds/";

//        System.out.println(url.toString());
//        System.out.println(url.getFile());
//        System.out.println(url.toExternalForm());

        String fileName = resourceName.substring(resourceName.lastIndexOf("/")+1, resourceName.length());

        File outputFile = new File(privateStorage.getAbsolutePath() + "/assets/" + subDirName + fileName);

        boolean result = copyFile(getClass().getResource(resourceName), outputFile);

        System.out.println("Result: " + result);

        if (type == AudioType.MUSIC) {
            try {
                return new AndroidMusic(new FileInputStream(outputFile), getClass().getResource(resourceName));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new AndroidSound(getClass().getResource(resourceName));
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
//
//    private SoundPool pool;
//
//    private Map<String, Integer> soundsCache = new HashMap<>();
//
//    @Override
//    public void playSound(String fileName, int numTimes, double leftVolume, double rightVolume) {
//        System.out.println("Play sound: " + fileName + " num times: " + numTimes);
//
//        if (pool == null) {
//            System.out.println("Building pool");
//            SoundPool.Builder builder = new SoundPool.Builder();
//
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_GAME)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build();
//
//            builder.setAudioAttributes(audioAttributes)
//                    .setMaxStreams(5);
//
//            pool = builder.build();
//        }
//
//        if (soundsCache.containsKey(fileName)) {
//            int id = soundsCache.get(fileName);
//
//            int streamID = pool.play(id, (float)leftVolume, (float)rightVolume, 1, numTimes - 1, 1.0f);
//
//            System.out.println("playing: " + streamID);
//        } else {
//
//            try {
//                if (privateStorage == null) {
//                    privateStorage = Services.get(StorageService.class)
//                            .flatMap(service -> service.getPrivateStorage())
//                            .orElseThrow(() -> new RuntimeException("Error accesing Private Storage folder"));
//                }
//
//                File assetsDir = new File(privateStorage, "assets");
//                if (!assetsDir.exists()) {
//                    System.out.println("CREATING MKDIR");
//                    assetsDir.mkdir();
//                }
//
//                File soundsDir = new File(assetsDir, "sounds");
//                if (!soundsDir.exists()) {
//                    System.out.println("CREATING SOUNDS DIR");
//                    soundsDir.mkdir();
//                }
//
//                File file = new File(privateStorage.getAbsolutePath() + fileName);
//
//                if (!file.exists()) {
//                    boolean result = copyFile(fileName, file);
//
//                    System.out.println("COPYING: " + result);
//                }
//
//                FileInputStream stream = new FileInputStream(file);
//
//                int soundID = pool.load(stream.getFD(), 0, file.length(), 1);
//
//                soundsCache.put(fileName, soundID);
//
//                stream.close();
//
//
//                int streamID = pool.play(soundID, (float)leftVolume, (float)rightVolume, 1, numTimes - 1, 1.0f);
//
//                System.out.println("playing: " + streamID);
//
//
//            } catch (Exception e) {
//                e.printStackTrace(System.out);
//            }
//
//
//        }
//    }
}

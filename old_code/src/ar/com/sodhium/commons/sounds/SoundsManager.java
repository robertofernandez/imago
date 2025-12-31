package ar.com.sodhium.commons.sounds;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import ar.com.sodhium.commons.concurrent.TaskExecutor;
import ar.com.sodhium.commons.concurrent.TaskHandler;
import ar.com.sodhium.commons.exceptions.ExceptionPrinter;
import ar.com.sodhium.commons.exceptions.ExceptionsListener;
import ar.com.sodhium.commons.logging.InternalLogger;

public class SoundsManager {
    private Boolean playSounds;
    private String soundResourcesFolderPath;
    private ConcurrentHashMap<String, Clip> clipsByName;
    private TaskExecutor taskExecutor;
    private InternalLogger logger;

    public SoundsManager(Boolean playSounds, String soundResourcesFolderPath) {
        this.playSounds = playSounds;
        this.soundResourcesFolderPath = soundResourcesFolderPath;
        taskExecutor = new TaskExecutor("sounds_player", new ExceptionsListener() {
            @Override
            public void onException(Exception e) {
                System.out.println(ExceptionPrinter.printException(e));
            }
        });
        clipsByName = new ConcurrentHashMap<>();
        taskExecutor.init();
    }

    public void playSound(String name) {
        if (playSounds) {
            taskExecutor.pushTask(new TaskHandler() {
                @Override
                public String getDescription() {
                    return "playing sound " + name;
                }

                @Override
                public void execute() {
                    try {
                        doLog("debug", "playing sound: " + name);
                        if (!clipsByName.containsKey(name)) {
                            Clip clip = AudioSystem.getClip();
                            File initialFile = new File(soundResourcesFolderPath + name + ".wav");
                            InputStream targetStream = new FileInputStream(initialFile);
                            InputStream bufferedIn = new BufferedInputStream(targetStream);
                            AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
                            clip.open(inputStream);
                            clipsByName.putIfAbsent(name, clip);
                        } else {
                            clipsByName.get(name).setMicrosecondPosition(0L);
                        }

                        CountDownLatch clipDone = new CountDownLatch(1);
                        clipsByName.get(name).addLineListener(new LineListener() {
                            @Override
                            public void update(LineEvent event) {
                                if (event.getType() == LineEvent.Type.STOP) {
                                    //event.getLine().close();
                                    clipDone.countDown();
                                }
                            }
                        });
                        clipsByName.get(name).start();
                        clipDone.await();
                    } catch (Exception e) {
                        if(logger != null) {
                            doLog("error", "Error playing sound", e);
                        }
                    }
                }
            });
        }
    }
    
    private void doLog(String level, String message) {
        if(logger != null) {
            logger.log(level, message);
        }
    }

    private void doLog(String level, String message, Throwable e) {
        if(logger != null) {
            logger.log(level, message, e);
        }
    }

    public void setPlaySounds(Boolean playSounds) {
        this.playSounds = playSounds;
    }
    
    public void setLogger(InternalLogger logger) {
        this.logger = logger;
    }
}

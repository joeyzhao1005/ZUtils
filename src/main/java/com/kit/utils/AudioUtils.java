package com.kit.utils;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

import com.kit.app.application.AppMaster;
import com.kit.utils.log.Zog;

/**
 * Created by Zhao on 16/7/27.
 * <p/>
 * <p/>
 * 注意:切换模式一定要在你播放音频前至少1500ms外,因为切换模式需要耗时。
 */
public class AudioUtils implements AudioManager.OnAudioFocusChangeListener {


    AudioState audioState;

    public void setAudioState(AudioState audioState) {
        this.audioState = audioState;
    }

    Context context;

    AudioManager audioManager;

    private static AudioUtils audioUtils;

    public static AudioUtils getInstance() {
        if (audioUtils == null) {
            audioUtils = new AudioUtils();
        }
        return audioUtils;
    }


    /**
     * 开启正常模式
     */
    public void setNormalMode() {
        release();
    }


    /**
     * 开启听筒模式
     */
    public void setReceiverMode() {
        Zog.i("set to receiver mode");
        context = AppMaster.getInstance().getAppContext();
        audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

//        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            if (audioManager.getMode() == AudioManager.MODE_IN_CALL) {
                return;
            }
        } else {
            if (audioManager.getMode() == AudioManager.MODE_IN_COMMUNICATION) {
                return;
            }
        }


        //播放音频流类型
//        if (context instanceof Activity)
//            ((Activity) context).setVolumeControlStream(AudioManager.STREAM_MUSIC);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                audioManager.setMode(AudioManager.MODE_IN_CALL);
            } else {
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            }
        } else {
            try {
                //获得当前类
//                Class audioSystemClass = Class.forName("android.media.AudioSystem");
//                得到这个方法
//                Method setForceUse = audioSystemClass.getMethod("setForceUse", int.class, int.class);

                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);

//                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

//                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC
//                            , audioManager.getStreamVolume(AudioManager.STREAM_MUSIC),
//                            AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }

//                    audioManager.setMode(AudioManager.MODE_NORMAL);
//                setForceUse.invoke(null, 0, 0);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启耳机发音模式
     */
    public void setHeadsetMode() {
        context = AppMaster.getInstance().getAppContext();
        audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

//        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (audioManager.getMode() == AudioManager.MODE_NORMAL) {
            return;
        }

        audioManager.setMode(AudioManager.MODE_NORMAL);

//        if (context instanceof Activity)
//            ((Activity) context).setVolumeControlStream(AudioManager.STREAM_MUSIC);


        if (audioManager.isSpeakerphoneOn()) {
            audioManager.setSpeakerphoneOn(false);
        }

//        setReceiverMode(context);
    }


    /**
     * 开启免提模式
     */
    public void setSpeakerMode() {
        Zog.i("set to speaker mode");
        context = AppMaster.getInstance().getAppContext();
        audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.getMode() == AudioManager.MODE_NORMAL) {
            return;
        }

//        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        //播放音频流类型
//        if (context instanceof Activity)
//            ((Activity) context).setVolumeControlStream(AudioManager.STREAM_MUSIC);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
        } else {
            try {


                //获得当前类
//                Class audioSystemClass = Class.forName("android.media.AudioSystem");
                //得到这个方法
//                Method setForceUse = audioSystemClass.getMethod("setForceUse", int.class, int.class);


//                    audioManager.setMicrophoneMute(false);

                boolean isWiredHeadsetOn = false;

                if (audioState != null) {
                    isWiredHeadsetOn = audioState.isWiredHeadsetOn(context);
                } else {
                    isWiredHeadsetOn = audioManager.isWiredHeadsetOn();
                }

                if (isWiredHeadsetOn) {
                    audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                    audioManager.setSpeakerphoneOn(false);
                } else {
                    Zog.i("set to mormal mode");

                    audioManager.setMode(AudioManager.MODE_NORMAL);
                    audioManager.setSpeakerphoneOn(true);
                }

                // setForceUse.invoke(null, 1, 1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public void release() {
        Zog.i("release release release");
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.abandonAudioFocus(this);
        }
        audioManager = null;
        audioUtils = null;
    }


    public interface AudioState {
        /**
         * 是否插入了耳机
         *
         * @param context
         * @return
         */
        boolean isWiredHeadsetOn(Context context);

    }


    @Override
    public void onAudioFocusChange(int i) {

    }

}

package com.kit.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

import com.kit.utils.log.ZogUtils;

/**
 * Created by Zhao on 16/7/27.
 *
 *
 * 注意:切换模式一定要在你播放音频前至少1500ms外,因为切换模式需要耗时。
 */
public class AudioUtils {


    AudioState audioState;


    public void setAudioState(AudioState audioState) {
        this.audioState = audioState;
    }


    private static AudioUtils audioUtils;

    public static AudioUtils getInstance() {
        if (audioUtils == null)
            audioUtils = new AudioUtils();

        return audioUtils;
    }


    /**
     * 开启耳机发音模式
     *
     * @param activity
     */

    public void setHeadsetMode(Activity activity) {
        setReceiverMode(activity);
    }


    /**
     * 开启正常模式
     *
     * @param activity
     */
    public void setNormalMode(Activity activity) {
        setSpeakerMode(activity);
    }


    /**
     * 设置听筒模式
     *
     * @param activity
     * @param on       是否开启
     */
    public void setReceiverMode(Activity activity, boolean on) {

        if (on) {
            setReceiverMode(activity);
        } else {
            setNormalMode(activity);
        }
    }


    /**
     * 开启听筒模式
     *
     * @param activity
     */
    public void setReceiverMode(Activity activity) {
        ZogUtils.i("set to receiver mode");

        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                audioManager.setMode(AudioManager.MODE_IN_CALL);
            } else {
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            }
        } else {
            try {
                //播放音频流类型
//                    activity.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                //获得当前类
//                    Class audioSystemClass = Class.forName("android.media.AudioSystem");
                //得到这个方法
//                    Method setForceUse = audioSystemClass.getMethod("setForceUse", int.class, int.class);

                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);

                    audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);

//                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL
//                            , audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL),
//                            AudioManager.STREAM_VOICE_CALL);
                }

//                    audioManager.setMode(AudioManager.MODE_NORMAL);
//                    setForceUse.invoke(null, 1, 1);;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 开启免提模式
     *
     * @param activity
     */
    public void setSpeakerMode(Activity activity) {
        ZogUtils.i("set to speaker mode");

        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
        } else {
            try {
                //播放音频流类型
//                activity.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

                //获得当前类
//                Class audioSystemClass = Class.forName("android.media.AudioSystem");
                //得到这个方法
//                Method setForceUse = audioSystemClass.getMethod("setForceUse", int.class, int.class);


//                    audioManager.setMicrophoneMute(false);

                boolean isWiredHeadsetOn = false;

                if (audioState != null)
                    isWiredHeadsetOn = audioState.isWiredHeadsetOn();
                else
                    isWiredHeadsetOn = audioManager.isWiredHeadsetOn();

                if (isWiredHeadsetOn) {
                    audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                    audioManager.setSpeakerphoneOn(false);
                } else {
                    ZogUtils.i("set to mormal mode");

                    audioManager.setMode(AudioManager.MODE_NORMAL);
                    audioManager.setSpeakerphoneOn(true);
                }

                // setForceUse.invoke(null, 1, 1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public interface AudioState {
        boolean isWiredHeadsetOn();

    }


}

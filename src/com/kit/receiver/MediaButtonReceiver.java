package com.kit.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.session.MediaSession;
import android.os.Build;
import android.view.KeyEvent;

import com.kit.utils.log.ZogUtils;

public class MediaButtonReceiver extends BroadcastReceiver {


    private static MediaButtonReceiver mediaButtonReceiver;


    AudioManager mAudioManager;
    ComponentName mReceiverComponent;


    protected MediaSession mediaSession;

    Context context;


    public static MediaButtonReceiver getInstance() {
        if (mediaButtonReceiver == null)
            mediaButtonReceiver = new MediaButtonReceiver();

        return mediaButtonReceiver;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (onHeadset(context, intent)) {
            ZogUtils.i("onHeadset");
        } else if (onMediaButton(context, intent)) {
            ZogUtils.i("onMediaButton");
        }
    }


    protected boolean onHeadset(Context context, Intent intent) {
        ZogUtils.i(intent.getAction());

        switch (intent.getAction()) {
            case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                ZogUtils.i("ACTION_AUDIO_BECOMING_NOISY");
                onHeadSetPlugOff(context);
                return true;

            case AudioManager.ACTION_HEADSET_PLUG:
            case AudioManager.ACTION_HDMI_AUDIO_PLUG:

                int state = intent.getIntExtra("state", -1);

                ZogUtils.i("ACTION_HEADSET_PLUG state:" + state);

                switch (state) {
                    case 0:
                        onHeadSetPlugOff(context);
                        return true;


                    case 1:
                        onHeadSetPlugIn(context);
                        return true;


                    default:
                        ZogUtils.i("未知状态");
                        return false;

                }

        }

        return false;
    }


    /**
     * 当耳机拔出的时候触发
     *
     * @param context
     */
    protected void onHeadSetPlugOff(Context context) {

    }

    /**
     * 当耳机插入的时候触发
     *
     * @param context
     */
    protected void onHeadSetPlugIn(Context context) {

    }


    /**
     * 当耳机上的按钮按下去的时候触发
     *
     * @param context
     * @param intent
     * @return
     */
    protected boolean onMediaButton(Context context, Intent intent) {
        return false;
    }


    public void register(Context context) {

        registerMediaButton(context);

        registerHeadset(context);


    }

    /**
     * 这里注册过之后,还必须要在manifest里面注册,否则不起作用,这个跟耳机插拔事件注册刚好相反。
     */
    public void registerMediaButton(final Context context) {

        ZogUtils.i("registerMediaButton:" + getClass().getName());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            mReceiverComponent = new ComponentName(context, getClass());
            mAudioManager.registerMediaButtonEventReceiver(mReceiverComponent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mReceiverComponent = new ComponentName(context, getClass());
            Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0
                    , mediaButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mediaButtonIntent.setComponent(mReceiverComponent);

            mediaSession = new MediaSession(context, context.getPackageName());

            mediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS
                    | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);


            /**
             * 感觉这块Android相当混乱,有的机型不设置callback,就收不到回调
             */
            mediaSession.setCallback(new MediaSession.Callback() {
                @Override
                public boolean onMediaButtonEvent(Intent intent) {
                    if (!Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
                        ZogUtils.i("xxxxxxxxxxxxx");
                        return super.onMediaButtonEvent(intent);
                    }

                    KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                    if (event == null || event.getAction() != KeyEvent.ACTION_UP) {
                        super.onMediaButtonEvent(intent);
                        ZogUtils.i("ooooooooooooooo");
                        return onMediaButton(context, intent);
                    }

                    return true;
                }
            });


//            PlaybackState state = new PlaybackState.Builder()
//                    .setActions(PlaybackStateCompat.ACTION_FAST_FORWARD
//                            | PlaybackStateCompat.ACTION_PAUSE
//                            | PlaybackStateCompat.ACTION_PLAY
//                            | PlaybackStateCompat.ACTION_PLAY_PAUSE
//                            | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
//                            | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
//                            | PlaybackStateCompat.ACTION_STOP)
//                    .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1, SystemClock.elapsedRealtime())
//                    .build();
//            mediaSession.setPlaybackState(state);

            mediaSession.setMediaButtonReceiver(pendingIntent);
            if (!mediaSession.isActive())
                mediaSession.setActive(true);
        }
    }


    /**
     * 这个必须用代码注册,manifest里面注册不起作用
     *
     * @param context
     */
    public void registerHeadset(Context context) {
        IntentFilter mediafilter = new IntentFilter();
        //拦截按键KeyEvent.KEYCODE_MEDIA_NEXT、KeyEvent.KEYCODE_MEDIA_PREVIOUS、KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE

        mediafilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediafilter.addAction(AudioManager.ACTION_HEADSET_PLUG);
            mediafilter.addAction(AudioManager.ACTION_HDMI_AUDIO_PLUG);
        }


        mediafilter.setPriority(2147483647);//设置优先级，优先级太低可能被拦截，收不到信息。一般默认优先级为0，通话优先级为1，该优先级的值域是-1000到1000。
        context.registerReceiver(getInstance(), mediafilter);

    }

    public void unregister(Context context) {
        unregisterMediaButton();
        context.unregisterReceiver(getInstance());
    }


    public void unregisterMediaButton() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (mAudioManager != null)
                mAudioManager.unregisterMediaButtonEventReceiver(mReceiverComponent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mediaSession != null)
                mediaSession.release();
        }


    }

}

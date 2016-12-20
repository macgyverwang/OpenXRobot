package org.xrobot.frameworks.robotmanager.reaction.accurate.speech;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class TTSManager extends Mouth implements TextToSpeech.OnInitListener {
    private static final String TAG = TTSManager.class.getSimpleName();
    private static final String UTTERANCE_ID = "utterance_id";

    private static TTSManager mInstance;
    private final TextToSpeech mTextToSpeech;
    private final HashSet<MouthListener> mMouthListeners = new HashSet<>();

    public static synchronized TTSManager getInstance(Context context) {
        if (mInstance == null) {
            context = context.getApplicationContext();
            mInstance = new TTSManager(context);
        }
        return mInstance;
    }

    private TTSManager(Context context) {
        final TTSProgressListener listener = new TTSProgressListener();
        mTextToSpeech = new TextToSpeech(context, this);
        mTextToSpeech.setOnUtteranceProgressListener(listener);
    }

    private void speak(String text, boolean queue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (queue) {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, UTTERANCE_ID);
            } else {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, UTTERANCE_ID);
            }
        } else {
            final HashMap<String, String> params = new HashMap<>();
            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UTTERANCE_ID);
            if (queue) {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD, params);

            } else {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params);
            }
        }
    }

    @Override
    public void onInit(int status) {
        final String text = String.format(Locale.ROOT, "init status: %d", status);
        Log.d(TAG, text);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mTextToSpeech.shutdown();
    }

    @Override
    public void say(String word, boolean queue) {
        final Locale locale = Resources.getSystem().getConfiguration().locale;
        mTextToSpeech.setLanguage(locale);
        speak(word, queue);
    }

    @Override
    public void say(String word, Locale locale, boolean queue) {
        mTextToSpeech.setLanguage(locale);
        speak(word, queue);
    }

    @Override
    public int stop() {
        return mTextToSpeech.stop();
    }

    @Override
    public void addListener(MouthListener listener) {
        mMouthListeners.add(listener);
    }

    @Override
    public void removeListener(MouthListener listener) {
        mMouthListeners.remove(listener);
    }

    private class TTSProgressListener extends UtteranceProgressListener {
        @Override
        public void onStart(String utteranceId) {
        }

        @Override
        public void onDone(String utteranceId) {
            for (final MouthListener listener : mMouthListeners) {
                listener.onMouthDone();
            }
        }

        @Override
        public void onError(String utteranceId) {
            final String text = String.format(Locale.ROOT, "error code: %d", TextToSpeech.ERROR);
            for (final MouthListener listener : mMouthListeners) {
                listener.onMouthError(TextToSpeech.ERROR, text);
            }
            Log.w(TAG, text);
        }

        @Override
        public void onError(String utteranceId, int errorCode) {
            final String text = String.format(Locale.ROOT, "error code: %d", errorCode);
            for (final MouthListener listener : mMouthListeners) {
                listener.onMouthError(errorCode, text);
            }
            Log.w(TAG, text);
        }
    }
}

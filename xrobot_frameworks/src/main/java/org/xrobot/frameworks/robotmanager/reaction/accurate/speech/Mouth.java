package org.xrobot.frameworks.robotmanager.reaction.accurate.speech;

import java.util.Locale;

/**
 * Created by develop on 2016/12/16.
 */

public abstract class Mouth {
    /**
     * say the words in system language
     * @param words What you want to say in system language.
     * @param queue Immediately or queue
     */
    public abstract void say(String words,boolean queue);

    /**
     * say the words in specific language
     * @param words What you want to say in system language.
     * @param locale say in which language you want to say.
     * @param queue Immediately or queue
     */
    public abstract void say(String words, Locale locale,boolean queue);

    /**
     * stop saying.
     */
    public abstract int stop();

    /**
     * add callback
     * @param listener the listener to call back
     */
    public abstract void addListener(MouthListener listener);

    /**
     * remove the callback
     * @param listener the listener to call back
     */
    public abstract void removeListener(MouthListener listener);

    public interface MouthListener {
        /**
         * callback when the speaking is done
         */
        void onMouthDone();

        /**
         * callback when got error
         * @param error   error number
         * @param message error message
         */
        void onMouthError(int error, String message);
    }
}

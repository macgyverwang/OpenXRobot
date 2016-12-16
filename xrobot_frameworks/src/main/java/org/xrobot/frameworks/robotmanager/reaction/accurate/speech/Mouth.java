package org.xrobot.frameworks.robotmanager.reaction.accurate.speech;

import java.util.Locale;

/**
 * Created by develop on 2016/12/16.
 */

public abstract class Mouth {

    /**
     * @param word What you want to say in system language.
     */
    public abstract void say(String word);

    /**
     * @param locale say in which language you want to say.
     */
    public abstract void say(String word, Locale locale);


    public interface MouthListener {
        /**
         * What you want to say is done
         */
        void onMouthDone();

        /**
         * What you want to say got error
         * @param error   error number
         * @param message error message
         */
        void onMouthError(int error, String message);
    }
}

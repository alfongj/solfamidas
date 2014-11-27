package es.solfamidas.elmundo.utils;

import android.os.Looper;

/**
 * Created by Carlos on 27/11/2014.
 */
public class ThreadUtils {

    public static boolean assertInUIThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}

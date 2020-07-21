package silladus.basic.util;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import timber.log.Timber;

/**
 * create by silladus 2020/7/16
 * github:https://github.com/silladus
 * des:
 */
public class DebugTree extends Timber.DebugTree {
    private static final int CALL_STACK_INDEX = 5;

    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t) {

        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= CALL_STACK_INDEX) {
            throw new IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }

        StackTraceElement element = stackTrace[CALL_STACK_INDEX];

        message = String.format(Locale.getDefault(), "(%s:%d) %s", element.getFileName(), element.getLineNumber(), message);

        super.log(priority, tag, message, t);
    }
}

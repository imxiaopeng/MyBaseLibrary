package com.cxp.mybaselibrary.utils;

import android.util.Log;

import static com.cxp.mybaselibrary.utils.Constant.ISDEBUG;


/**
 * 日志工具类
 */
public final class Logger {

    private static final String PROJECTNAME = Constant.PROJECTNAME;

    private static long previousNanoTime = 0;

    private static enum LogLevelEnum {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }


    private static StackTraceElement getCurrentExcuteTraceInfo(final int eleNum) {
        return (Thread.currentThread().getStackTrace())[eleNum];
    }

    protected static String getNameFromTrace(StackTraceElement[] traceElements, int place) {
        StringBuilder taskName = new StringBuilder();
        if (traceElements != null && traceElements.length > place) {
            StackTraceElement traceElement = traceElements[place];
            taskName.append(traceElement.getMethodName());
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(")");
        }
        return taskName.toString();
    }


    private static StringBuilder getCurrentClassInfo(final int eleNum) {
        StackTraceElement currentExcuteTraceInfo = getCurrentExcuteTraceInfo(eleNum);
        long nanoTime = /*System.nanoTime() - previousNanoTime ;*/System.currentTimeMillis();
        previousNanoTime = System.nanoTime();
        return new StringBuilder()
                .append("时间戳= " + nanoTime)
                .append(" ;Class= " + currentExcuteTraceInfo.getClassName())
                .append(" ; Method= ")
                .append(currentExcuteTraceInfo.getMethodName())
                .append(" ; Message= ");
    }

    private static void record(LogLevelEnum level, String... msgs) {
        if (!ISDEBUG) {
            return;
        }
        if (msgs == null) {
            return;
        }

        StringBuilder sb = getCurrentClassInfo(6);
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), 3);
            String msg = sourceLinks + "\n" + msgs[0];
            msg = msg + sb.toString();
            Log.e(PROJECTNAME, "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
            );
            switch (level) {
                case INFO:
                    Log.i(PROJECTNAME, msg);
                    break;
                case ERROR:
                    Log.e(PROJECTNAME, msg);
                    break;
                case WARN:
                    Log.w(PROJECTNAME, msg);
                    break;
                case DEBUG:
                    Log.d(PROJECTNAME, msg);
                    break;
                case VERBOSE:
                    Log.v(PROJECTNAME, msg);
                    break;
            }
            Log.e(PROJECTNAME, "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        } catch (Throwable throwable) {

        }
    }

    public static void i(String... msgs) {
        if (!ISDEBUG) {
            return;
        }
        if (msgs == null) {
            return;
        }
        StringBuilder sb = getCurrentClassInfo(6);
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), 3);
            String msg = sourceLinks + "\n" + msgs[0];
            msg = msg + sb.toString();
            Log.e(PROJECTNAME, "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
            );
            Log.i(PROJECTNAME, msg);
            Log.e(PROJECTNAME, "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        } catch (Throwable throwable) {

        }
    }

    public static void e(String... msgs) {
        if (!ISDEBUG) {
            return;
        }
        if (msgs == null) {
            return;
        }
        StringBuilder sb = getCurrentClassInfo(6);
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), 3);
            String msg = sourceLinks + "\n";
            msg = msg + sb.toString() + msgs[0] + "\n---------------------";
            Log.e(PROJECTNAME, msg);
        } catch (Throwable throwable) {

        }
    }

    public static void w(String... msgs) {
        if (!ISDEBUG) {
            return;
        }
        if (msgs == null) {
            return;
        }
        StringBuilder sb = getCurrentClassInfo(6);
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), 3);
            String msg = sourceLinks + "\n" + msgs[0];
            msg = msg + sb.toString();
            Log.e(PROJECTNAME, "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
            );
            Log.w(PROJECTNAME, msg);
            Log.e(PROJECTNAME, "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        } catch (Throwable throwable) {

        }
    }

    public static void d(String... msgs) {
        if (!ISDEBUG) {
            return;
        }
        if (msgs == null) {
            return;
        }
        StringBuilder sb = getCurrentClassInfo(6);
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), 3);
            String msg = sourceLinks + "\n" + msgs[0];
            msg = msg + sb.toString();
            Log.e(PROJECTNAME, "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
            );
            Log.d(PROJECTNAME, msg);
            Log.e(PROJECTNAME, "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        } catch (Throwable throwable) {

        }
    }

    public static void v(String... msgs) {
        if (!ISDEBUG) {
            return;
        }
        if (msgs == null) {
            return;
        }
        StringBuilder sb = getCurrentClassInfo(6);
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), 3);
            String msg = sourceLinks + "\n" + msgs[0];
            msg = msg + sb.toString();
            Log.e(PROJECTNAME, "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
            );
            Log.v(PROJECTNAME, msg);
            Log.e(PROJECTNAME, "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        } catch (Throwable throwable) {

        }
    }

}

package isma.games;

import com.badlogic.gdx.Gdx;

import java.util.HashSet;
import java.util.Set;


public class Log {
    public static final int LOG_TRACE = 1;
    public static final int LOG_DEBUG = 2;
    public static final int LOG_INFO = 3;
    public static final int LOG_WARNING = 4;
    public static final int LOG_ERROR = 5;
    public static final int LOG_FORCE = 6;
    public static final int LOG_NONE = 7;

    private static Set<String> forbiddenClasses = new HashSet<String>();
    private static Set<String> forbiddenMethods = new HashSet<String>();
    public static int logLevel = LOG_WARNING;

    static {

//        Gdx.app.setLogLevel(LOG_DEBUG);
//        forbiddenClasses.add("isma.games.pacman.core.tiled.Maze");
//        forbiddenClasses.add("isma.games.pacman.core.manager.MoveManager");
//        forbiddenClasses.add("isma.games.pacman.core.manager.DefaultMoveHandler");

//        forbiddenMethods.add("isma.games.TiledMapHelper.getCellAt()");
    }

    public static void trace(String message, Object... params) {
        new InnerLogger(LOG_TRACE).log(message, params);
    }

    public static void debug(String message, Object... params) {
        new InnerLogger(LOG_DEBUG).log(message, params);
    }

    public static void info(String message, Object... params) {
        new InnerLogger(LOG_INFO).log(message, params);
    }

    public static void warn(String message, Object... params) {
        new InnerLogger(LOG_WARNING).log(message, params);
    }

    public static void error(String message, Object... params) {
        new InnerLogger(LOG_ERROR).log(message, params);
    }

    public static void force(String message, Object... params) {
        new InnerLogger(LOG_FORCE).log(message, params);
    }

    private static class InnerLogger {
        private int logLevel;

        protected InnerLogger(int logLevel) {
            this.logLevel = logLevel;
        }

        void log(String message, Object... params) {
            if (this.logLevel < Log.logLevel) {
                return;
            }
            String tag = buildDefaultTag();
            for (String forbiddenClass : forbiddenClasses) {
                if (tag.startsWith(forbiddenClass + ".")) {
                    return;
                }
            }
            if (forbiddenMethods.contains(tag)) {
                return;
            }

            message = buildMessage(message, params);
            doLog(message, tag);
        }

        static int i = 0;

        void doLog(String message, String tag) {
            System.out.println(i++ + " : " + tag + ": " + message);
        }

    }

    private static String buildDefaultTag() {
        StackTraceElement[] cause = Thread.currentThread().getStackTrace();
        int depth = 5;
        String className = cause[depth].getClassName();
        String methodName = cause[depth].getMethodName();
        return className + "." + methodName + "()";
        //return format("%s.%s()", className, methodName);
    }

    private static String buildMessage(String message, Object... params) {
//        return "log desactives (a cause de String.format() non gere par gwt (?)";
        return params == null ? message : String.format(message, params);
    }


    public static void start(final int logLevel) {
        String message = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
        new InnerLogger(logLevel) {
            @Override
            void doLog(String message, String tag) {
                if (logLevel == LOG_DEBUG) {
                    Gdx.app.debug(tag, message);
                } else if (logLevel == LOG_INFO) {
                    Gdx.app.log(tag, message);
                }
            }
        }.log(message);
    }

    public static void end(final int logLevel) {
        String message = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
        new InnerLogger(logLevel) {
            @Override
            void doLog(String message, String tag) {
                if (logLevel == LOG_DEBUG) {
                    Gdx.app.debug(tag, message);
                } else if (logLevel == LOG_INFO) {
                    Gdx.app.log(tag, message);
                }
            }
        }.log(message);
    }

}

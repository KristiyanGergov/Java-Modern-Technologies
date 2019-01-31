package bg.sofia.uni.fmi.mjt.battleships.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ThreadConstants.MAX_THREADS;

public class ThreadExecutor {

    private static ExecutorService pool;

    public static void execute(Runnable runnable) {
        if (pool == null)
            new ThreadExecutor();
        pool.execute(runnable);
    }

    public static void shutdown() {
        pool.shutdown();
    }

    private ThreadExecutor() {
        pool = Executors.newFixedThreadPool(MAX_THREADS);
    }
}

package com.example.android;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Parallelized extends Parameterized
{
    private static class ThreadPoolScheduler implements RunnerScheduler
    {
        private ExecutorService executor;

        public ThreadPoolScheduler() {
            executor = Executors.newWorkStealingPool();
        }

        @Override
        public void finished() {
            executor.shutdown();
            try {
                executor.awaitTermination(40, TimeUnit.MINUTES);
            }
            catch (InterruptedException exc) {
                throw new RuntimeException(exc);
            }
        }

        @Override
        public void schedule(Runnable childStatement) {
            executor.submit(childStatement);
        }
    }

    public Parallelized(Class klass) throws Throwable {
        super(klass);
        setScheduler(new ThreadPoolScheduler());
    }
}
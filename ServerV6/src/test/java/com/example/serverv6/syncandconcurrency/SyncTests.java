package com.example.serverv6.syncandconcurrency;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SyncTests {

    @Before
    public void setup() {

    }

    @Test
    public void test_for_sync_method() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        SynchronizedMethods synchronizedMethods = new SynchronizedMethods();

        IntStream.range(0, 1000)
                .forEach(count -> executorService.submit(synchronizedMethods::calculate));
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);

        Assert.assertEquals(1000, synchronizedMethods.getSum());
    }

    @Test
    public void test_for_sync_block() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        SynchronizedMethods synchronizedMethods = new SynchronizedMethods();

        IntStream.range(0, 1000)
                .forEach(count -> executorService.submit(synchronizedMethods::performSyncTask));
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);

        Assert.assertEquals(1000, synchronizedMethods.getSum());
    }

}

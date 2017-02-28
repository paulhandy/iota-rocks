package org.iota.rocks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rocksdb.RocksDBException;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by paul on 2/28/17 for iota-rocks.
 */
public class DataAccessTest {
    @Before
    public void setUp() throws Exception {
        DataAccess.instance().init();
    }

    @After
    public void tearDown() throws Exception {
        DataAccess.instance().shutdown();
    }

    @Test
    public void init() throws Exception {
        assertNotNull(DataAccess.instance().db);
    }

    @Test
    public void shutdown() throws Exception {
    }


    @Test
    public void put() throws Exception {
        byte[] key, value;
        key = "SomeKey".getBytes();
        value = "MyFancyVal".getBytes();
        DataAccess.instance().put(key, value);
        DataAccess.instance().db.remove(key);
    }

    @Test
    public void multiThreadedPut() throws Exception {
        int numberOfWorkers = 8;
        Thread[] workers = new Thread[numberOfWorkers];
        while(numberOfWorkers-- > 0) {
            workers[numberOfWorkers] = new Thread(() -> {
                int i = 100000;
                while(i-- > 0) {
                    byte[] key, value;
                    key = UUID.randomUUID().toString().getBytes();
                    value = UUID.randomUUID().toString().getBytes();
                    DataAccess.instance().put(key, value);
                    try {
                        DataAccess.instance().db.remove(key);
                    } catch (RocksDBException e) {
                        e.printStackTrace();
                    }
                }
            });
            workers[numberOfWorkers].start();
        }

        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void find() throws Exception {
        byte[] key, value;
        key = "SomeKey".getBytes();
        value = "MyFancyVal".getBytes();
        DataAccess.instance().put(key, value);
        assertArrayEquals(DataAccess.instance().find(key), value);
        DataAccess.instance().db.remove(key);
    }

    @Test
    public void instance() throws Exception {
        assertNotNull(DataAccess.instance());
    }

}
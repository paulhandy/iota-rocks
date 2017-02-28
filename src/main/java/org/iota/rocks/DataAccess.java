package org.iota.rocks;

import org.rocksdb.RocksDB;
import org.rocksdb.Options;
import org.rocksdb.RocksDBException;
/**
 * Created by paul on 2/28/17.
 */
public class DataAccess {
    private static DataAccess instance = new DataAccess();
    public RocksDB db;
    private Options options;
    private static final String DB_NAME = "benchmark.rdb";

    public void init() throws Exception {
        RocksDB.loadLibrary();
        options = new Options().setCreateIfMissing(true);
        db = RocksDB.open(options, DB_NAME);
    }

    public void shutdown() {
        if (db != null) db.close();
        options.dispose();
    }

    public byte[] find(byte[] hash) {
        try {
            return db.get(hash);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void put(byte[] hash, byte[] val) {
        try {
            db.put(hash, val);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    public static DataAccess instance() { return instance;}
}

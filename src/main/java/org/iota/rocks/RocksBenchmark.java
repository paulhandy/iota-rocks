package org.iota.rocks;

/**
 * Created by paul on 2/28/17 for iota-rocks.
 */
public class RocksBenchmark {
    public static void main(final String[] args) {
        try {
            DataAccess.instance().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataAccess.instance().shutdown();
    }
}

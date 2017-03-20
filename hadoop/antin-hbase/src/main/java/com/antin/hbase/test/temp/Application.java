package com.antin.hbase.test.temp;

import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2017/3/17.
 */
public class Application {
    private static final Logger LOGGER = Logger.getLogger(Application.class);
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            LOGGER.info("Info log [" + i + "].");
            Thread.sleep(500);
        }
    }
}

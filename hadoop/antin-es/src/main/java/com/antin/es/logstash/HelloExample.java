package com.antin.es.logstash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/4/20.
 */
public class HelloExample {
    final static Logger logger = LoggerFactory.getLogger(HelloExample.class);

    public static void main(String[] args) {
        HelloExample obj = new HelloExample();
        try {
            obj.divide();
        } catch (ArithmeticException ex) {
            logger.error("Sorry, something wrong!", ex);
        }
    }

    private void divide() {
        int i = 10 / 0;
    }
}

package com.young.myaddemo.utils;

import java.util.Random;

public class RandomUtil {

    public static int getRandomNum(int start, int end) {
        if (end <= 0) {
            return 0;
        }
        Random random = new Random();
        int result = random.nextInt(end) % (end - start+1) + start;
        return result;
    }

}

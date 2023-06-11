package com.me.server;

import java.util.HashMap;
import java.util.Map;

public class SnakesAndLaddersMap {
    private static final Map<Integer, Integer> MAP = new HashMap<>();

    static {
        // ladders，例如（1，38）意思就是落子到1，可以直接升到38
        MAP.put(1, 38);
        MAP.put(4, 14);
        MAP.put(8, 30);
        MAP.put(21, 42);
        MAP.put(28, 76);
        MAP.put(50, 67);
        MAP.put(71, 92);
        MAP.put(80, 99);
        // snakes, 例如（32， 10）意思是落子到32，直接倒退到10
        MAP.put(32, 10);
        MAP.put(36, 6);
        MAP.put(48, 26);
        MAP.put(62, 18);
        MAP.put(88, 24);
        MAP.put(95, 56);
        MAP.put(97, 78);
    }

    public static int getPosition(int position) {
        return MAP.getOrDefault(position, position);
    }
}

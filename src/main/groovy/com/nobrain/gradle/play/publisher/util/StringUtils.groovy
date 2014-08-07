package com.nobrain.gradle.play.publisher.util

/**
 * Created by jsuch2362 on 2014. 8. 7..
 */
class StringUtils {

    static def isEmpty(String s) {

        if (s == null || s.isEmpty()) {
            return true
        } else {
            return false
        }
    }

    static def equals(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false
        }

        if (s1.equals(s2)) {
            return true
        }

        return false
    }


}

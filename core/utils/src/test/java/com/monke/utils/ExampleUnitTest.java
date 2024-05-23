package com.monke.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test_format_float() {
        float x = 4.53453f;

        assertEquals("4.53", StringsHelper.formatFloat(x));
    }
}
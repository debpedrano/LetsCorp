package me.shouheng.commons;

import org.junit.Test;

import java.util.Date;

import me.shouheng.commons.util.TimeUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void timeUtils_getLongDate() {
        assertEquals("", TimeUtils.getLongDate(new Date()));
    }
}
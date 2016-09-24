package com.sevenprinciples.dojo.bowling;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by joachim.kaesser on 17.09.2016.
 */
public class SimpleFrameTest {

    public static final int FRAME_NUMBER = 1;
    public static final int PINS_ROLLED = 9;
    private Frame frame = null;

    @Before
    public void setUp() throws Exception {
        frame = new Frame(FRAME_NUMBER);
    }

    @After
    public void tearDown() throws Exception {
        frame = null;
    }

    @Test
    public void testFrame() throws Exception {
        frame.setPinsRolled(PINS_ROLLED);
        Integer totalScore = frame.getFrameScore();
        frame.getFrameScore();

        Assert.assertEquals("Unexpected score", Integer.valueOf(PINS_ROLLED), totalScore);

        totalScore = frame.getFrameScore();
        Assert.assertEquals("Unexpected role result.", Integer.valueOf(PINS_ROLLED), totalScore);
    }

}
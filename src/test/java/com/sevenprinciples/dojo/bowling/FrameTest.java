package com.sevenprinciples.dojo.bowling;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by joachim.kaesser on 21.09.2016.
 */
@RunWith(Parameterized.class)
public class FrameTest {

    /**
     * The class under test.
     */
    private Frame frame = null;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    // test parameters
    private Integer frameNumber = null;
    private Integer role1 = null;
    private Integer role2 = null;
    private Integer role3 = null;

    public FrameTest(Integer frameNumber, Integer role1, Integer role2, Integer role3) {
        this.frameNumber = frameNumber;
        this.role1 = role1;
        this.role2 = role2;
        this.role3 = role3;
    }

    @Parameterized.Parameters(name = "Test {index}: frame: {0}, role1: {1}, role2: {2}, role3: {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // regular cases
                {1, 0, 0, null},
                {1, 0, 1, null},
                {1, 1, 1, null},
                {2, 3, 7, null},
                {2, 0, 10, null},
                {3, 10, null, null},
                {3, 1, 9, null},
                {Game.MAX_FRAMES, 0, 8, null},
                {Game.MAX_FRAMES, 2, 8, 0},
                {Game.MAX_FRAMES, 2, 8, 10},
                {Game.MAX_FRAMES, 10, 0, 2},
                {Game.MAX_FRAMES, 10, 10, 10},

                // faulty cases
                {4, 10, 2, null}, // after a Strike, no further role is possible
                {Game.MAX_FRAMES, 0, 8, 1}, // no third role allowed in final frame if no strike or spare
                {Game.MAX_FRAMES, 9, 0, 1}, // no third role allowed in final frame if no strike or spare
                {0, null, null, null},
                {-1, null, null, null},
                {Integer.valueOf(Game.MAX_FRAMES + 1), null, null, null},

        });
    }

    @Test
    public void testFrame() throws Exception {

        if (frameNumber < 1 || frameNumber > Game.MAX_FRAMES) {
            exception.expect(IllegalFrameNumberException.class);
        }

        frame = new Frame(frameNumber);

        Assert.assertFalse("Frame is NOT expected to be complete before the first role.", frame.isFrameComplete());

        frame.setPinsRolled(role1);
        Integer totalScore = frame.getFrameScore();
        Assert.assertEquals("Unexpected total score after role 1: ", role1, totalScore);

        if (Frame.MAX_PINS == role1) {
            Assert.assertTrue("Role 1 is expected to be a strike.", frame.isStrike());
            if (frameNumber < Game.MAX_FRAMES) {
                Assert.assertTrue("Frame is supposed to be completed after a strike at the first role, if it is not the last frame.", frame.isFrameComplete());
            }
        } else {
            Assert.assertFalse("Frame is NOT expected to be complete after the first role.", frame.isFrameComplete());
        }

        if (role2 != null) {
            if (frame.isStrike() && !frame.isLastFrame()) {
                exception.expect(IllegalRoleNumberException.class);
                frame.setPinsRolled(role2);
            }
            frame.setPinsRolled(role2);
            totalScore = frame.getFrameScore();
            Assert.assertEquals("Unexpected total score after role 2: ", Integer.valueOf(role1 + role2), totalScore);
        }

        if (role3 != null) {
            if (Game.MAX_FRAMES == frameNumber) {
                Assert.assertTrue("Frame is supposed to be the last frame.", frame.isLastFrame());

                if (frame.isStrike() || frame.isSpare()) {
                    Assert.assertFalse("Last frame is not expected to be complete before the third role, if first role is a strike or second role is a spare.", frame.isFrameComplete());

                    frame.setPinsRolled(role3);
                    totalScore = frame.getFrameScore();
                    Assert.assertEquals("Unexpected total score after role 3: ", Integer.valueOf(role1 + role2 + role3), totalScore);

                    Assert.assertTrue("Last frame is expected to be complete after the third role.", frame.isFrameComplete());
                } else {
                    exception.expect(IllegalRoleNumberException.class);
                    frame.setPinsRolled(role3);
                }

            } else {
                Assert.assertTrue("Frame is expected to be complete after the second role.", frame.isFrameComplete());
                Assert.assertFalse("Frame is NOT the last frame.", frame.isLastFrame());
                exception.expect(IllegalRoleNumberException.class);
                frame.setPinsRolled(role2);
            }
        }
    }
}
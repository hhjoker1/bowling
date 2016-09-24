package com.sevenprinciples.dojo.bowling;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by joachim.kaesser on 17.09.2016.
 */
public class GameTest {
    private Game game = null;

    private int round = 0;
    private List<Integer> rolls = new ArrayList<>(Arrays.asList(1,4,4,5,6,4,5,5,10,0,1,7,3,6,4,10,2,8,6));
    private List<Integer> expectedTotalScore = new ArrayList<>(Arrays.asList(1,5,9,14,20,24,34,39,59,59,61,68,71,83,87,107,111,127,133));
    private static final int TOTAL_ROLLS = 19;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void addRoll() throws Exception {
        rolls.stream().forEach(r -> {
            checkRole(r);
        });
    }

    private void checkRole(Integer role) {
        game.addRoll(role);
        Assert.assertEquals("Unexpected getTotalScore at round " + round, (Integer) expectedTotalScore.get(round++), (Integer) game.getTotalScore());
    }


}
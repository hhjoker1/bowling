package com.sevenprinciples.dojo.bowling;

import java.util.List;

/**
 * Created by joachim.kaesser on 17.09.2016.
 */
class Game {
    private int totalScore = 0;
    private int currentFrame = 1;
    private List<Frame> frames = null;

    public static int MAX_FRAMES = 10;

    public void addRoll(int pins) {
        totalScore += pins;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public int getTotalScore() {
        return  totalScore;
    }

    public boolean isOver() {
        return false;
    }
}

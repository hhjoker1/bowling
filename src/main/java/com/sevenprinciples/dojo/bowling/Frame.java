package com.sevenprinciples.dojo.bowling;

import java.util.ArrayList;
import java.util.List;


/**
 * A single {@link Frame} as part of a Bowling {@link Game}.
 * Created by joachim.kaesser on 17.09.2016.
 */
class Frame {
    private List<Integer> pinsRolled = null;
    private int roleNumber = 0;
    private int frameNumber = 0;

    private static final int INITIAL_ROLE_VALUE = -1;

    public static final int MAX_ROLES_PER_FRAME = 2;
    public static final int MAX_ROLES_IN_FINAL_FRAME = 3;
    public static final int MAX_PINS = 10;

    /**
     * The constructor taking the frame number as paramter.
     * @param frameNumber the number of this frame
     * @throws IllegalFrameNumberException if the framenumber is below zero or above MAX_ROLES_PER_FRAME or MAX_ROLES_IN_FINAL_FRAME if this is the last frame.
     */
    public Frame(int frameNumber) throws IllegalFrameNumberException {
        if (frameNumber < 1 || frameNumber > Game.MAX_FRAMES) {
            throw new IllegalFrameNumberException();
        }

        initFrame(frameNumber);
    }

    /**
     * Initialize a new {@link Frame}.
     * @param frameNumber the number of the {@link Frame}
     */
    private void initFrame(int frameNumber) {
        this.frameNumber = frameNumber;
        pinsRolled = new ArrayList<>(getMaxRoles());
        pinsRolled.stream().forEach(pr -> pr = INITIAL_ROLE_VALUE);
    }

    /**
     * Sets the number of pins of the current role.
     * Could be called only MAX_ROLES_PER_FRAME times (or MAX_ROLES_IN_FINAL_FRAME times, if this is the last {@link Frame})
     * @param pins the number of pins dropped in the current role
     * @throws IllegalPinNumberException thrown, if the number of pins is below zero or above MAX_PINS
     * @throws IllegalRoleNumberException thrown, if the method is called too often
     */
    public void setPinsRolled(int pins) throws IllegalPinNumberException, IllegalRoleNumberException {
        if (pins < 0 || pins > MAX_PINS) {
            throw new IllegalPinNumberException();
        }

        if (roleNumber < getMaxRoles()) {
            pinsRolled.add(roleNumber++, pins);
        } else {
            throw new IllegalRoleNumberException();
        }
    }

    /**
     * Returns the maximal number of roles this frame can have.
     * @return the maximal number of roles
     */
    private int getMaxRoles() {
        if (Game.MAX_FRAMES == frameNumber) {
            return MAX_ROLES_IN_FINAL_FRAME;
        }
        return MAX_ROLES_PER_FRAME;
    }

    /**
     * Computes the total score of this frame.
     * In case of a Strike or Spare, no additional scores of following frames will be considered.
     *
     * @return the total score of this frame
     */
    public Integer getFrameScore() {
        return pinsRolled.stream().reduce(0, (sum, p) -> sum += p, (sum1, sum2) -> sum1 + sum2);
    }

    /**
     * Predicate to check if this is the last frame of the game.
     * @return true, if this frame is the games last frame
     */
    public boolean isLastFrame() {
        return Game.MAX_FRAMES == frameNumber;
    }

    /**
     * Computes if this frame is already completed.
     * A frame is complete if either of the following case is true:
     * <p>
     *     <ol>
     *         <li>The frame is a Strike.</li>
     *         <li>The frame is a Spare.</li>
     *         <li>All roles of this frame has been played.</li>
     *     </ol>
     * </p>
     *
     * @return true, if this frame is completed
     */
    public boolean isFrameComplete() {
        return isStrike() || isSpare() || allRolesPlayed();
    }

    /**
     * Checks, if all roles have been played already.
     * @return true if all roles are played
     */
    private boolean allRolesPlayed() {
        if (pinsRolled.size() == getMaxRoles()) {
            return pinsRolled.stream().allMatch(pr -> INITIAL_ROLE_VALUE != pr);
        }
        return false;
    }

    /**
     * Checks, if this {@link Frame} is a Strike, if at least one of the roles has MAX_PIN pins.
     * @return true, if this {@link Frame} is a Strike
     */
    public boolean isStrike() {
        return pinsRolled.stream().anyMatch(pr -> pr == MAX_PINS);
    }

    /**
     * Checks, if this {@link Frame} is a Spare, if it is not a Strike and the total number of pins rolled is MAX_PINS.
     * @return true, if this {@link Frame} is a Spare
     */
    public boolean isSpare() {
        return !isStrike() && MAX_PINS == getFrameScore();
    }
}

package kochme.visualsorting.ui;

import javax.swing.*;

public class PlayPauseToggle extends JButton {

    public enum State {PLAY, PAUSE}

    private State state;

    private Icon playIcon, playRolloverIcon;
    private Icon pauseIcon, pauseRolloverIcon;

    private Icon playDisabledIcon;
    private Icon pauseDisabledIcon;

    public PlayPauseToggle() {
        this.setRolloverEnabled(true);
        //this.setBorder(BorderFactory.createEmptyBorder());

        setState(State.PLAY);
    }

    public void setPlayIcon(String iconResourcePath) {
        playIcon = new ImageIcon(this.getClass().getResource(iconResourcePath));
        playRolloverIcon = new RolloverIcon(playIcon);
        playDisabledIcon = new DisabledIcon(playIcon);
    }

    public void setPauseIcon(String iconResourcePath) {
        pauseIcon = new ImageIcon(this.getClass().getResource(iconResourcePath));
        pauseRolloverIcon = new RolloverIcon(pauseIcon);
        pauseDisabledIcon = new DisabledIcon(pauseIcon);
    }

    public void setState(State newState) {
        this.state = newState;

        if (state == State.PLAY) {
            this.setIcon(playIcon);
            this.setRolloverIcon(playRolloverIcon);
            this.setDisabledIcon(playDisabledIcon);
        } else {
            this.setIcon(pauseIcon);
            this.setRolloverIcon(pauseRolloverIcon);
            this.setDisabledIcon(pauseDisabledIcon);
        }
    }

    public void toggle() {
        if (state == State.PLAY) {
            this.setIcon(pauseIcon);
            this.setRolloverIcon(pauseRolloverIcon);
            state = State.PAUSE;
        } else {
            this.setIcon(playIcon);
            this.setRolloverIcon(playRolloverIcon);
            state = State.PLAY;
        }
    }

    public State getState() {
        return state;
    }
}

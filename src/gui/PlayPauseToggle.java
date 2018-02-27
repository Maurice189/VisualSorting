package gui;

import com.bulenkov.iconloader.IconLoader;

import javax.swing.*;

public class PlayPauseToggle extends JButton {

    public enum State {PLAY, PAUSE}

    private State state;

    private Icon playIcon, playRolloverIcon;
    private Icon pauseIcon, pauseRolloverIcon;

    public PlayPauseToggle() {
        this.state = State.PLAY;
        this.setRolloverEnabled(true);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public void setPlayIcon(String iconResourcePath) {
        playIcon = IconLoader.getIcon(iconResourcePath);
        playRolloverIcon = new RolloverIcon(playIcon);
    }

    public void setPauseIcon(String iconResourcePath) {
        pauseIcon = IconLoader.getIcon(iconResourcePath);
        pauseRolloverIcon = new RolloverIcon(pauseIcon);
    }

    public void setState(State newState) {
        this.state = newState;

        if (state == State.PLAY) {
            this.setIcon(playIcon);
            this.setRolloverIcon(playRolloverIcon);
        } else {
            this.setIcon(pauseIcon);
            this.setRolloverIcon(pauseRolloverIcon);
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

/**
 * TimeTrialPanel represents a JPanel that displays the current time and the best time achieved.
 * It listens for changes in the best time and updates the display accordingly.
 * It also listens for when the game starts and when it ends, to start and stop the timer.
 */
package com.final_project_csc308_winter_2024;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TimeTrialPanel extends JPanel implements PropertyChangeListener {
    private TimeTrial timeTrial;
    private JLabel timeLabel;
    private JLabel bestTimeLabel;
    private long time;

    /**
     * Constructs a TimeTrialPanel with the specified TimeTrial instance.
     *
     * @param timeTrial The TimeTrial instance to display and monitor for changes.
     */
    public TimeTrialPanel(TimeTrial timeTrial) {

        this.timeTrial = timeTrial;
        setLayout(new GridLayout(2, 1));

        JPanel curTimePanel = new JPanel();
        timeLabel = new JLabel("Time: 00:00.000");
        timeLabel.setFont(new Font("Monaco", Font.BOLD, 20));
        timeLabel.setForeground(Color.WHITE);
        curTimePanel.setLayout(new GridBagLayout());
        curTimePanel.setBackground(Color.decode("0x508991"));
        curTimePanel.add(timeLabel);

        JPanel bestTimePanel = new JPanel();
        bestTimeLabel = new JLabel("Best Time: 00:00.000");
        bestTimeLabel.setFont(new Font("Monaco", Font.BOLD, 20));
        bestTimeLabel.setForeground(Color.WHITE);
        bestTimePanel.setLayout(new GridBagLayout());
        bestTimePanel.setBackground(Color.decode("0x004346"));
        bestTimePanel.add(bestTimeLabel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;

        add(curTimePanel, constraints);
        add(bestTimePanel, constraints);

        Repository.getInstance().addPropertyChangeListener(this);

        Timer timer = new Timer(100, e -> {
            timeLabel.setText("Time: " + timeTrial.formatElapsedTime());
        });
        timer.start();
    }

    /**
     * Returns the TimeTrial instance associated with this panel.
     *
     * @return The TimeTrial instance.
     */
    public TimeTrial getTimeTrial() {
        return this.timeTrial;
    }

    /**
     * Invoked when a property change is detected. 
     * Updates the best time label when the best time changes.
     * Starts the timer when game starts.
     * Stops the timer when game ends.
     *
     * @param evt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("bestTime")) {
            long bestTime = (long) evt.getNewValue();
            bestTimeLabel.setText("Best Time: " + Repository.getInstance().formatElapsedTime(bestTime));
        }

        if (evt.getPropertyName().equals("counter")) {
            int counter = (int) evt.getNewValue();
            if (counter == 0) {
                timeTrial.setElapsedTime(0);
            }
        }

        if (evt.getPropertyName().equals("counter")) {
            int counter = (int) evt.getNewValue();
            if (counter == 1) {
                timeTrial.restart();
            }
        }

        if (evt.getPropertyName().equals("gameOver")) {
            int gameOver = (int) evt.getNewValue();
            if (gameOver == 1) {
                time = timeTrial.stop();
                Repository.getInstance().changeBestTime(time);
            }
        }
    }
}

import java.awt.event.*;
import java.util.*;


/**
 * Backend code
 * @author liam
 * @version 11/5
 */

public class User {
    public double waveH;
    public double waveP;
    public double windS;
    public boolean affects = false;
    public boolean waveHEntered = false;
    public boolean wavePEntered = false;
    public boolean windSEntered = false;
    public ArrayList<String> display = new ArrayList<>();
    public String displayString = "";

    public User() {
        // TODO Set up lists containing all the wave height limits and beach rules
    }

    // Restricts text fields to only allow numbers and decimal points
    // TODO disallow only decimal points.
    public void onlyDigits(KeyEvent evt) {
        char typed = evt.getKeyChar();
        if (!Character.isDigit(typed) && typed != '.') {
            evt.consume(); // Blocks invalids
        }
    }

    // Validates and sets wave height input
    public boolean setWaveHeight(String waveHeightStr) {
        try {
            if (waveHeightStr != null && !waveHeightStr.isEmpty()) {
                waveH = Double.parseDouble(waveHeightStr);
                waveHEntered = true;
            } else {
                waveHEntered = false;
            }
        } catch (NumberFormatException e) {
            waveHEntered = false;
        }
        return waveHEntered;
    }

    // Validates and sets wave period input
    public boolean setWavePeriod(String wavePeriodStr) {
        try {
            if (wavePeriodStr != null && !wavePeriodStr.isEmpty()) {
                waveP = Double.parseDouble(wavePeriodStr);
                wavePEntered = true;
            } else {
                wavePEntered = false;
            }
        } catch (NumberFormatException e) {
            wavePEntered = false;
        }
        return wavePEntered;
    }

    // Validates and sets windspeed input, checks if it affects waves
    public boolean setWindSpeed(String windSpeedStr) {
        try {
            if (windSpeedStr != null && !windSpeedStr.isEmpty()) {
                windS = Double.parseDouble(windSpeedStr);
                // Wind affects waves if speed is greater than something, 5 for now
                affects = (windS >= 5);
                windSEntered = true;
            } else {
                windSEntered = false;
            }
        } catch (NumberFormatException e) {
            windSEntered = false;
        }
        return windSEntered;
    }

    // Makes sure everything is entered
    public boolean allFieldsEntered() {
        return waveHEntered && wavePEntered && windSEntered;
    }
}
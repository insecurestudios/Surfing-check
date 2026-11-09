import org.jetbrains.annotations.NotNull;
import javax.swing.text.*;
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
    public ArrayList<String> display = new ArrayList<>(); // text to be displayed by GUI
    public String displayString = "";

    // Wave height thresholds
    public String[] waveQuality = {"too small", "small but ok", "great", "big but ok"};

    public double[] pegueraB = {0.1, 0.2, 0.5, 0.7};
    public double[] pegueraI = {0.2, 0.4, 1.0, 2.0};
    public double[] pegueraA = {0.5, 1.0, 2.0, 3.0};

    public double[] sonSerraB = {0.1, 0.2, 0.4, 0.5};
    public double[] sonSerraI = {0.2, 0.3, 0.8, 1.5};
    public double[] sonSerraA = {0.3, 0.5, 1.5, 2.5};

    public double[] canPastillaB = {0.3, 0.4, 0.6, 0.9};
    public double[] canPastillaI = {0.4, 0.6, 1.0, 1.5};
    public double[] canPastillaA = {0.5, 1.5, 2.5, 4.0};

    public double[] saRapitaB = {0.2, 0.3, 0.6, 0.9};
    public double[] saRapitaI = {0.3, 0.5, 1.0, 1.5};
    public double[] saRapitaA = {0.5, 1.0, 2.0, 3.0};

    public double[] calaMesquidaB = {0.2, 0.3, 0.5, 0.7};
    public double[] calaMesquidaI = {0.3, 0.5, 1.0, 1.5};
    public double[] calaMesquidaA = {0.5, 1.0, 2.0, 3.0};

    public double[] canyamelB = {0.2, 0.3, 0.5, 0.7};
    public double[] canyamelI = {0.3, 0.7, 1.5, 2.0};
    public double[] canyamelA = {0.5, 1.0, 2.0, 3.0};

    public double[] calaMayorB = {0.1, 0.2, 0.5, 0.8};
    public double[] calaMayorI = {0.2, 0.5, 1.0, 2.0};
    public double[] calaMayorA = {0.5, 1.0, 2.0, 3.0};


    public User() {

    }

    // Restricts text fields to only allow numbers and decimal points
    public void onlyDigits(KeyEvent evt) {
        char typed = evt.getKeyChar();
        Object source = evt.getSource();

        if (!Character.isDigit(typed) && typed != '.') {
            evt.consume();
        } else if (source instanceof JTextComponent comp) if (typed == '.' && comp.getText().contains(".")) {
            evt.consume(); // Blocks multiple decimal points
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

    // Validates and sets wind speed input, checks if it affects waves
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

    /// @param search array being searched
    /// @param want string in array being found
    /// @return found string
    public int getIndex(String[] search, String want) {
        int temp = 0;
        for (int i = 0; i < search.length; i++) {
            if (search[i].equals(want)) {
                temp = i;
            }
        }
        return temp;
    }

    public double value(double @NotNull [] values, String[] search, String want) {
        return values[getIndex(search, want)];
    }


    public void heightAffect(double[] values) {
        if (waveH <= value(values, waveQuality, "too small")) {
            display.add("The waves are too small for the selected surf ability");
        } else if (waveH <= value(values, waveQuality, "small but ok")) {
            display.add("The wave height is a bit small for the selected surf ability, but OK");
        } else if (waveH <= value(values, waveQuality, "great")) {
            display.add("The wave height is great for the selected surf ability");
        } else if (waveH <= value(values, waveQuality, "big but ok")) {
            display.add("The wave height is a bit big for the selected surf ability, but OK");
        } else {
            display.add("The waves are too big for the selected surf ability");
        }
    }
}


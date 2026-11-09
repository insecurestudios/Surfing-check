import org.jetbrains.annotations.NotNull;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;


/**
 * Backend code
 * @author liam
 * @version 11/9
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

    // Direction quality and ideal direction arrays
    public String[] dirQuality = {"ideal", "good", "good", "ok", "ok", "bad", "bad", "very bad"};
    public String[] idealSW = {"SW", "S", "W", "NW", "SE", "E", "N", "NE"};
    public String[] idealNE = {"NE", "N", "E", "SE", "S", "SW", "W", "NW"};
    public String[] idealS = {"S", "SW", "SE", "E", "W", "NE", "N", "NW"};
    public String[] idealN = {"N", "NE", "NW", "W", "E", "SE", "S", "SW"};
    public String[] idealE = {"E", "NE", "SE", "N", "S", "SW", "W", "NW"};
    public String[] idealW = {"W", "SW", "NW", "S", "N", "NE", "SE", "E"};

    // Period arrays
    public String[] periodQuality = {"good", "ok", "unsurfable"};
    public double[] period = {18.0, 6.0, 3.0};

    // State variables
    public String beach;
    public String windDir;
    public String waveDir;


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

    // Methods to find values of desired thresholds
    public double value(double @NotNull [] values, String[] search, String want) {
        return values[getIndex(search, want)];
    }

    public String value(String[] values, String[] search, String want) {
        return values[getIndex(search, want)];
    }

    public String value2(String[] values, String[] search, String want) {
        return values[getIndex(search, want) + 1];
    }

    // Returns the ideal direction for wind/waves
    public String[] windOrWaveArray(String direction) {
        return switch (direction) {
            case "SW" -> idealSW;
            case "S" -> idealS;
            case "N" -> idealN;
            case "E" -> idealE;
            case "W" -> idealW;
            case "NE" -> idealNE;
            default -> null;
        };
    }

    // The below 4 methods split up the calculation logic
    public void periodAffect() {
        if (waveP < period[2]) {
            display.add(" Unfortunately the wave period is too short and will make the waves unsurfable");
        } else if (waveP <= period[1]) {
            display.add(" The wave period is ok");
        } else if (waveP < period[0]) {
            display.add(" The wave period is good");
        } else {
            display.add(" The wave period is fantastic and will make the waves more surfable");
        }
    }

    public void wavesAffect(String idealWaveDir) {
        String[] arr = windOrWaveArray(idealWaveDir);
        if (waveDir.equals(value(arr, dirQuality, "ideal"))) {
            display.add(" The wave direction is ideal");
            periodAffect();
        } else if (waveDir.equals(value(arr, dirQuality, "good")) || waveDir.equals(value2(arr, dirQuality, "good"))) {
            display.add(" The wave direction is good");
            periodAffect();
        } else if (waveDir.equals(value(arr, dirQuality, "ok")) || waveDir.equals(value2(arr, dirQuality, "ok"))) {
            display.add(" The wave direction is ok");
            periodAffect();
        } else if (waveDir.equals(value(arr, dirQuality, "bad")) || waveDir.equals(value2(arr, dirQuality, "bad"))) {
            display.add(" The wave direction is bad");
        } else {
            display.add(" The wave direction is very bad");
        }
    }

    public void windAffect(String idealWindDir) {
        if (affects) {
            String[] arr = windOrWaveArray(idealWindDir);
            if (windDir.equals(value(arr, dirQuality, "ideal"))) {
                display.add(" The wind direction is ideal and will significantly improve the quality of the waves");
                wavesAffect(idealWindDir);
            } else if (windDir.equals(value(arr, dirQuality, "good")) || windDir.equals(value2(arr, dirQuality, "good"))) {
                display.add(" The wind direction is good and will probably improve the waves");
                wavesAffect(idealWindDir);
            } else if (windDir.equals(value(arr, dirQuality, "ok")) || windDir.equals(value2(arr, dirQuality, "ok"))) {
                display.add(" The wind direction is ok and will not affect the waves");
                wavesAffect(idealWindDir);
            } else if (windDir.equals(value(arr, dirQuality, "bad")) || windDir.equals(value2(arr, dirQuality, "bad"))) {
                display.add(" The wind direction is bad and will probably worsen the quality of the waves");
            } else {
                display.add(" The wind direction is very bad and will significantly worsen the quality of the waves");
            }
        } else {
            wavesAffect(idealWindDir);
        }
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

    // Performs the main surfability calculation
    public void calculate(String beachInput, String ability, String windDirInput, String waveDirInput) {
        display.clear();
        beach = beachInput;
        windDir = windDirInput;
        waveDir = waveDirInput;

        switch (beach) {
            case "Peguera" -> {
                if (ability.equals("Beginner")) heightAffect(pegueraB);
                else if (ability.equals("Intermediate")) heightAffect(pegueraI);
                else if (ability.equals("Advanced")) heightAffect(pegueraA);
                windAffect("NE");
            }
            case "Son Serra de Marina" -> {
                if (ability.equals("Beginner")) heightAffect(sonSerraB);
                else if (ability.equals("Intermediate")) heightAffect(sonSerraI);
                else if (ability.equals("Advanced")) heightAffect(sonSerraA);
                windAffect("SW");
            }
            case "Can Pastilla" -> {
                if (ability.equals("Beginner")) heightAffect(canPastillaB);
                else if (ability.equals("Intermediate")) heightAffect(canPastillaI);
                else if (ability.equals("Advanced")) heightAffect(canPastillaA);
                windAffect("N");
            }
            case "Sa Rapita" -> {
                if (ability.equals("Beginner")) heightAffect(saRapitaB);
                else if (ability.equals("Intermediate")) heightAffect(saRapitaI);
                else if (ability.equals("Advanced")) heightAffect(saRapitaA);
                windAffect("NE");
            }
            case "Cala Mesquida" -> {
                if (ability.equals("Beginner")) heightAffect(calaMesquidaB);
                else if (ability.equals("Intermediate")) heightAffect(calaMesquidaI);
                else if (ability.equals("Advanced")) heightAffect(calaMesquidaA);
                windAffect("S");
            }
            case "Canyamel" -> {
                if (ability.equals("Beginner")) heightAffect(canyamelB);
                else if (ability.equals("Intermediate")) heightAffect(canyamelI);
                else if (ability.equals("Advanced")) heightAffect(canyamelA);
                windAffect("W");
            }
            case "Cala Mayor" -> {
                if (ability.equals("Beginner")) heightAffect(calaMayorB);
                else if (ability.equals("Intermediate")) heightAffect(calaMayorI);
                else if (ability.equals("Advanced")) heightAffect(calaMayorA);
                windAffect("SW");
            }
        }
    }

    // Creates the displayable string for the GUI
    public String displayText() {
        displayString = "In " + beach + ":";
        for (String s : display) {
            displayString = displayString.concat(s);
        }
        displayString = displayString.concat(".");
        return displayString;
    }
}


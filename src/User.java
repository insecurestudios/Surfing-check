import java.util.*;

/**
 * Backend code.
 * @author liam
 * @version 11/5
 */

public class User {
    // TODO: Create validation flags
    public boolean affects = false;
    public boolean waveHEntered = false;
    public boolean wavePEntered = false;
    public boolean windSEntered = false;
    public ArrayList<String> display = new ArrayList<>();
    public String displayString = "";

    public User() {
        // TODO Set up lists containing all the wave height limits and beach rules
    }
}
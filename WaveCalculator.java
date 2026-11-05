import models.User;
import javax.swing.*;
import java.awt.*;
// this comment is because intellij is broken second test
/**
 * GUI for the calculator
 * @author liam
 * @version 10/3
 */
public class WaveCalculator extends JFrame {
    private User newUser; //will be made later

    // swing items
    private JButton calculate;
    private JComboBox<String> beachChosen;
    private JTextArea display;
    private JTextArea introduction;
    private JComboBox<String> surfAbility;
    private JComboBox<String> waveDirection;
    private JFormattedTextField waveHeight;
    private JFormattedTextField wavePeriod;
    private JComboBox<String> windDirection;
    private JFormattedTextField windSpeed;

    public waveCalculator() {
        initComponents();
        newUser = new User();

        // Prevent interaction with non-editable text areas
        introduction.setFocusable(false);
        display.setFocusable(false);

        // TODO: Add interaction code
    }

    private void initComponents() {}
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // reccomended
        setTitle("Wave Quality Calculator");

        // display settings
        introduction = new JTextArea("This program will determine the quality of waves at a certain surf spot depending on the wind, wave height, period, and direction.");
        introduction.setLineWrap(true);
        introduction.setWrapStyleWord(true);
        display = new JTextArea();
        display.setLineWrap(true);
        display.setWrapStyleWord(true);

        calculate = new JButton("Calculate");

        // Combo boxes are dropdowns
        beachChosen = new JComboBox<>(new String[]{"Peguera", "Son Serra de Marina", "Can Pastilla", "Sa Rapita", "Cala Mesquida", "Canyamel", "Cala Mayor"});
        surfAbility = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        windDirection = new JComboBox<>(new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"});
        waveDirection = new JComboBox<>(new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"});

        waveHeight = new JFormattedTextField();
        wavePeriod = new JFormattedTextField();
        windSpeed = new JFormattedTextField();

        // creates the layout
        setLayout(new GridLayout(6, 2, 5, 5));
        add(new JLabel("Beach:"));
        add(beachChosen);
        add(new JLabel("Surf Ability:"));
        add(surfAbility);
        add(new JLabel("Wave Height:"));
        add(waveHeight);
        add(new JLabel("Wave Period:"));
        add(wavePeriod);
        add(new JLabel("Wind Speed:"));
        add(windSpeed);
        add(calculate);

        // resizes and centers window
        pack();
        setLocationRelativeTo(null);
    }

    void main() {}
        new waveCalculator().setVisible(true));
    }

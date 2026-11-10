import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * GUI for the calculator
 * @author liam
 * @version 11/10
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

    public WaveCalculator() {
        initComponents();
        newUser = new User();

        // Prevent interaction with noneditable text areas
        introduction.setFocusable(false);
        display.setFocusable(false);

        // TODO: Finish interaction code
        waveHeight.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                newUser.onlyDigits(evt);
            }
        });

        wavePeriod.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                newUser.onlyDigits(evt);
            }
        });

        windSpeed.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                newUser.onlyDigits(evt);
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // recommended
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

        // Calculate button code
        calculate.addActionListener(e -> {
            boolean hValid = newUser.setWaveHeight(waveHeight.getText());
            boolean pValid = newUser.setWavePeriod(wavePeriod.getText());
            boolean wValid = newUser.setWindSpeed(windSpeed.getText());

            if (newUser.allFieldsEntered()) {
                String selectedBeach = (String) beachChosen.getSelectedItem();
                String selectedAbility = (String) surfAbility.getSelectedItem();
                String selectedWindDir = (String) windDirection.getSelectedItem();
                String selectedWaveDir = (String) waveDirection.getSelectedItem();

                newUser.calculate(selectedBeach, selectedAbility, selectedWindDir, selectedWaveDir);
                display.setText(newUser.displayText());
            } else {
                display.setText("Please enter valid numeric values in all fields.");
            }
        });
    }

    void main() {
        new WaveCalculator().setVisible(true);
    }

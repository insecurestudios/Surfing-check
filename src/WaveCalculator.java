import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * GUI for the calculator
 * @author liam
 * @version 11/10
 */

public class WaveCalculator extends JFrame {
    public final User newUser;

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

        // Only allow numbers for the input fields
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

        // swing items
        JButton calculate = new JButton("Calculate");

        // Combo boxes are dropdowns
        beachChosen = new JComboBox<>(new String[]{"Peguera", "Son Serra de Marina", "Can Pastilla", "Sa Rapita", "Cala Mesquida", "Canyamel", "Cala Mayor"});
        surfAbility = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        windDirection = new JComboBox<>(new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"});
        waveDirection = new JComboBox<>(new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"});

        waveHeight = new JFormattedTextField();
        wavePeriod = new JFormattedTextField();
        windSpeed = new JFormattedTextField();

        // Calculate button code
        calculate.addActionListener(e -> {

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

        // Display settings
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(display);
        setLayout(new BorderLayout(10, 10));

        // Combining all the elements
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        inputPanel.add(new JLabel("Beach:"));
        inputPanel.add(beachChosen);
        inputPanel.add(new JLabel("Surf Ability:"));
        inputPanel.add(surfAbility);
        inputPanel.add(new JLabel("Wave Height:"));
        inputPanel.add(waveHeight);
        inputPanel.add(new JLabel("Wave Period:"));
        inputPanel.add(wavePeriod);
        inputPanel.add(new JLabel("Wind Speed:"));
        inputPanel.add(windSpeed);
        inputPanel.add(new JLabel("Wind Direction:"));
        inputPanel.add(windDirection);
        inputPanel.add(new JLabel("Wave Direction:"));
        inputPanel.add(waveDirection);

        // Create a bottom panel for the calculate button and scrollable results
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        scrollPane.setPreferredSize(new Dimension(400, 100));
        bottomPanel.add(calculate, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(450, 400);
        setLocationRelativeTo(null);
    }

    void main() {
        new WaveCalculator().setVisible(true);
    }
    }

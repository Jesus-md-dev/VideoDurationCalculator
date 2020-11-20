import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.NumberFormatter;

public class VideoDurationCalculator extends JFrame {

    private JFormattedTextField hour, min, sec;
    private JLabel result;
    JComboBox speedsSelection;
    boolean boolTimer = false;
    JButton calculate;
    LocalTime timeVideo, timeResult, timeBackup;
    Boolean backupSaved = false;
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            timeResult = timeResult.minusSeconds(1);
            result.setText(timeVideo + " => " + timeResult);
        }
    });

    public VideoDurationCalculator() {

        NumberFormatter numberFormatter = new NumberFormatter();
        numberFormatter.setValueClass(Long.class);
        timeVideo = LocalTime.of(0, 0, 0);
        timeResult = LocalTime.of(0, 0, 0);

        // TimePanel
        JPanel setTimePanel = new JPanel();
        setTimePanel.setBounds(0, 0, 420, 30);

        JLabel title, h, m, s;

        title = new JLabel("Duration: ");
        h = new JLabel("H");
        m = new JLabel("m");
        s = new JLabel("s");

        hour = new JFormattedTextField(numberFormatter);
        hour.setPreferredSize(new Dimension(50,20));
        hour.setText("0");
        hour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });

        min = new JFormattedTextField(numberFormatter);
        min.setPreferredSize(new Dimension(50,20));
        min.setText("0");
        min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });

        sec = new JFormattedTextField(numberFormatter);
        sec.setPreferredSize(new Dimension(50, 20));
        sec.setText("0");
        sec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });

        // SpeedPanel
        JPanel setSpeedPanel = new JPanel();
        setSpeedPanel.setBounds(0, 30, 420, 40);

        String[] speeds = {"x1", "x1.25", "x1.5", "x1.75", "x2"};
        speedsSelection = new JComboBox(speeds);

        speedsSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });

        calculate = new JButton("Start");
        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                    calculate.setText("Start");
                }
                else {
                    if(!backupSaved)
                        actualizar();
                    timer.start();
                    calculate.setText("Stop");
                }
            }
        });

        // ResultPanel
        JPanel resultPanel = new JPanel();
        resultPanel.setBounds(0, 70, 420, 40);

        result = new JLabel(timeVideo + " => " + timeResult);

        JPanel resultPanel2 = new JPanel();

        setTimePanel.add(title);
        setTimePanel.add(hour);
        setTimePanel.add(h);
        setTimePanel.add(min);
        setTimePanel.add(m);
        setTimePanel.add(sec);
        setTimePanel.add(s);
        setSpeedPanel.add(speedsSelection);
        setSpeedPanel.add(calculate);
        resultPanel.add(result);
        
        setTitle("Video Duration Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(420,150);
        setLocation(540, 360);

        add(setTimePanel);
        add(setSpeedPanel);
        add(resultPanel);
        add(resultPanel2);
    }

    public void actualizar(){
        String speed;
        int h = Integer.parseInt(hour.getText());
        int m = Integer.parseInt(min.getText());
        int s = Integer.parseInt(sec.getText());
        long seconds = h * 3600 + m * 60 + s;

        backupSaved = false;

        speed = String.valueOf(speedsSelection.getSelectedItem());
        speed = speed.replace("x", "");

        timeVideo = LocalTime.ofSecondOfDay(seconds);

        seconds /= Double.parseDouble(speed);

        timeResult = LocalTime.ofSecondOfDay(seconds);

        result.setText(timeVideo + " => " + timeResult);
    }

    public static int[] secondToTime(int seconds) {
        int[] time = new int[3];

        time[0] = seconds / 3600;
        seconds -= time[0] * 3600;
        time[1] = seconds / 60;
        time[2] = seconds - time[1] * 60;

        return time;
    }

    public static void main(String[] args){
        new VideoDurationCalculator();
    }
}
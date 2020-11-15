import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

public class VideoDurationCalculator extends JFrame implements ActionListener{

    private JFormattedTextField hour, min, sec;
    private JLabel result;
    JComboBox speedsSelection;

    public VideoDurationCalculator(){

        NumberFormatter numberFormatter = new NumberFormatter();
        numberFormatter.setValueClass(Long.class);

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

        min = new JFormattedTextField(numberFormatter);
        min.setPreferredSize(new Dimension(50,20));
        min.setText("0");

        sec = new JFormattedTextField(numberFormatter);
        sec.setPreferredSize(new Dimension(50, 20));
        sec.setText("0");

        setTimePanel.add(title);
        setTimePanel.add(hour);
        setTimePanel.add(h);
        setTimePanel.add(min);
        setTimePanel.add(m);
        setTimePanel.add(sec);
        setTimePanel.add(s);

        // SpeedPanel
        JPanel setSpeedPanel = new JPanel();
        setSpeedPanel.setBounds(0, 30, 420, 40);

        JButton calculate = new JButton("Calculate");
        calculate.addActionListener(this);

        String[] speeds = {"x1","x1.25", "x1.5" , "x2"};
        speedsSelection = new JComboBox(speeds);

        setSpeedPanel.add(speedsSelection);
        setSpeedPanel.add(calculate);

        // ResultPanel
        JPanel resultPanel = new JPanel();
        resultPanel.setBounds(0, 70, 420, 40);

        result = new JLabel("0H 0m 0s => 0H 0m 0s");
        
        resultPanel.add(result);

        JPanel resultPanel2 = new JPanel();

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

    @Override
    public void actionPerformed(ActionEvent e) {
        int h, m, s, hMod, mMod, sMod;
        double total;
        int[] time;
        String speed;
        h = 0; m = 0; s = 0; hMod = 0; mMod = 0; sMod = 0;

        h = Integer.parseInt(hour.getText());
        m = Integer.parseInt(min.getText());
        s = Integer.parseInt(sec.getText());

        total = h * 3600 + m * 60 + s;

        time = secondToTime((int)total);
        h = time[0];
        m = time[1];
        s = time[2];

        if(total > 0){
            speed = String.valueOf(speedsSelection.getSelectedItem());
            speed = speed.replace("x", "");
            total /= Double.parseDouble(speed);
            time = secondToTime((int)total);
            hMod = time[0];
            mMod = time[1];
            sMod = time[2];
        }

        result.setText(h + "H " + m + "m " + s + "s => " + hMod + "H " + mMod + "m " + sMod + "s");
    }

    public int[] secondToTime(int seconds) {
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
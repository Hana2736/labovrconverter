package ml.stickbit;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] vidNames = {"01_Flag", "02_AquariumCallorhinus_00", "03_Deer_00", "04_Magic", "010_Dog_00", "06_WaterTap_00", "07_EatSushi_00", "08_Domino", "09_Cherry_00", "10_Cherry_01", "11_AquariumSalamander_00", "12_VirtualBoy", "13_SoapBubble", "14_BallMouth_00", "110_Cup_00", "16_Cup_01", "17_DogBaby", "18_Paint", "19_EatItalian_00", "20_AquariumPenguin_00", "21_TableCloth", "22_Taketonbo", "23_AquariumMedusa_00", "24_Origami", "210_MusicHappyBirthday", "26_EatChinese_00", "27_Kusudama", "28_Cat_00", "29_Baton", "30_UpsideDown", "31_Piano", "32_AquariumSea_00", "33_MusicTheme"};
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Your OS is not supported.");
            return;
        }
        SpringLayout layout = new SpringLayout();
        JFrame window = new JFrame("Labo VR Video Converter 0.1");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container pane = window.getContentPane();
        pane.setBackground(Color.BLACK);
        window.setLayout(layout);
        JLabel desc = new JLabel("Convert videos to the format that Labo VR uses");
        desc.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.WEST, desc, 10, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, desc, 10, SpringLayout.NORTH, pane);
        pane.add(desc);
        JFileChooser inPathPick = new JFileChooser();
        JLabel inPathDesc = new JLabel("Input video file");
        inPathDesc.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.NORTH, inPathDesc, 35, SpringLayout.SOUTH, desc);
        layout.putConstraint(SpringLayout.WEST, inPathDesc, 0, SpringLayout.WEST, desc);
        pane.add(inPathDesc);
        JTextArea inPathSt = new JTextArea("<input path> ");
        inPathSt.setBackground(Color.BLACK);
        inPathSt.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.WEST, inPathSt, 0, SpringLayout.WEST, inPathDesc);
        layout.putConstraint(SpringLayout.NORTH, inPathSt, 10, SpringLayout.SOUTH, inPathDesc);
        pane.add(inPathSt);
        JButton inPathBrowse = new JButton("Browse...");
        inPathBrowse.setBackground(Color.BLACK);
        layout.putConstraint(SpringLayout.WEST, inPathBrowse, 10, SpringLayout.EAST, inPathSt);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, inPathBrowse, 0, SpringLayout.VERTICAL_CENTER, inPathSt);
        inPathBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inPathPick.showDialog(null, "Open");
                inPathSt.setText(inPathPick.getSelectedFile().toString());
            }
        });
        pane.add(inPathBrowse);
        JFileChooser outPathPick = new JFileChooser();
        outPathPick.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JLabel outPathDesc = new JLabel("Output video directory");
        outPathDesc.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.NORTH, outPathDesc, 10, SpringLayout.SOUTH, inPathSt);
        layout.putConstraint(SpringLayout.WEST, outPathDesc, 0, SpringLayout.WEST, desc);
        pane.add(outPathDesc);
        JTextArea outPathStr = new JTextArea("<output path>");
        outPathStr.setBackground(Color.BLACK);
        outPathStr.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.WEST, outPathStr, 0, SpringLayout.WEST, desc);
        layout.putConstraint(SpringLayout.NORTH, outPathStr, 10, SpringLayout.SOUTH, outPathDesc);
        pane.add(outPathStr);
        JButton outPathBrowse = new JButton("Browse...");
        outPathBrowse.setBackground(Color.BLACK);
        layout.putConstraint(SpringLayout.WEST, outPathBrowse, 10, SpringLayout.EAST, outPathStr);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, outPathBrowse, 0, SpringLayout.VERTICAL_CENTER, outPathStr);
        outPathBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outPathPick.showDialog(null, "Open");
                outPathStr.setText(outPathPick.getSelectedFile().toString());
            }
        });
        pane.add(outPathBrowse);
        JSpinner overwrite = new JSpinner(new SpinnerNumberModel(1, 1, 33, 1));
        JLabel overDesc = new JLabel("Labo video to replace");
        overDesc.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.NORTH, overDesc, 10, SpringLayout.SOUTH, outPathStr);
        layout.putConstraint(SpringLayout.WEST, overDesc, 0, SpringLayout.WEST, outPathStr);
        pane.add(overDesc);
        layout.putConstraint(SpringLayout.NORTH, overwrite, 10, SpringLayout.SOUTH, overDesc);
        layout.putConstraint(SpringLayout.WEST, overwrite, 0, SpringLayout.WEST, overDesc);
        pane.add(overwrite);
        JLabel ffmPathDesc = new JLabel("FFMPEG binary path");
        ffmPathDesc.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.NORTH, ffmPathDesc, 10, SpringLayout.SOUTH, overwrite);
        layout.putConstraint(SpringLayout.WEST, ffmPathDesc, 0, SpringLayout.WEST, desc);
        pane.add(ffmPathDesc);
        JTextArea ffmPath = new JTextArea("<ffmpeg path>");
        ffmPath.setBackground(Color.BLACK);
        ffmPath.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.WEST, ffmPath, 0, SpringLayout.WEST, inPathDesc);
        layout.putConstraint(SpringLayout.NORTH, ffmPath, 10, SpringLayout.SOUTH, ffmPathDesc);
        JButton ffmPathBrowse = new JButton("Browse...");
        ffmPathBrowse.setBackground(Color.BLACK);
        layout.putConstraint(SpringLayout.WEST, ffmPathBrowse, 10, SpringLayout.EAST, ffmPath);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, ffmPathBrowse, 0, SpringLayout.VERTICAL_CENTER, ffmPath);
        JSpinner qual = new JSpinner(new SpinnerNumberModel(5, .01, 500, .1));
        JLabel qualDesc = new JLabel("Quality of video in MB/s");
        qualDesc.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.NORTH, qualDesc, 10, SpringLayout.SOUTH, ffmPath);
        layout.putConstraint(SpringLayout.WEST, qualDesc, 0, SpringLayout.WEST, outPathStr);
        pane.add(qualDesc);
        layout.putConstraint(SpringLayout.NORTH, qual, 10, SpringLayout.SOUTH, qualDesc);
        layout.putConstraint(SpringLayout.WEST, qual, 0, SpringLayout.WEST, qualDesc);
        pane.add(qual);
        JButton start = new JButton("ffmpeg not found!");
        start.setBackground(Color.BLACK);
        start.setEnabled(false);
        JFileChooser ffmPathPick = new JFileChooser();
        if (Files.exists(Paths.get(System.getProperty("user.home") + "/Documents/ffmpath.bin"))) {
            try {
                List<String> ffm = Files.readAllLines(Paths.get(System.getProperty("user.home") + "/Documents/ffmpath.bin"));
                ffmPath.setText(ffm.get(0));
                start.setText("Go!");
                start.setEnabled(true);
            } catch (Exception aaaaa) {
                System.out.println("IO Error");
            }

        }
        ffmPathBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ffmPathPick.showDialog(null, "Open");
                ffmPath.setText(ffmPathPick.getSelectedFile().toString());
                if (Files.exists(Paths.get(ffmPath.getText()))) {
                    try {
                        Files.write(Paths.get(System.getProperty("user.home") + "/Documents/ffmpath.bin"), ffmPath.getText().getBytes());
                    } catch (Exception exc) {
                        System.out.println("Writing error");
                    }
                    start.setText("Go!");
                    start.setEnabled(true);
                }
            }
        });
        layout.putConstraint(SpringLayout.NORTH, start, 35, SpringLayout.SOUTH, qual);
        layout.putConstraint(SpringLayout.WEST, start, 0, SpringLayout.WEST, qual);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] ffmpegArgs = new String[]{ffmPath.getText(), "-loglevel", "quiet", "-y", "-i", inPathSt.getText(), "-c:v", "libvpx-vp9", "-c:a", "libvorbis", "-s", "2560:1440", "-b:v", qual.getValue().toString(), outPathStr.getText() + "/Cvr_Vr180_" + vidNames[(int) overwrite.getValue() - 1] + ".webm"};
                try {
                    for (int i = 0; i < ffmpegArgs.length; i++) {
                        System.out.print(ffmpegArgs[i] + " ");
                    }
                    window.setVisible(false);
                    JOptionPane.showConfirmDialog(null, "Currently converting. You'll be notified when it's done.\nThis may take a very long time, depending on the source video and your set quality.", "Labo VR Video Converter 0.1", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    Runtime.getRuntime().exec(ffmpegArgs).waitFor();
                    JOptionPane.showConfirmDialog(null, "Video conversion finished!\nRemember to put it in /atmosphere/titles/0100165003504000/RomFS/Learn/", "Labo VR Video Converter 0.1", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                } catch (Exception ex) {
                    System.out.println("FFMPEG failed!");
                }
            }
        });
        pane.add(ffmPathBrowse);
        pane.add(ffmPath);
        pane.add(start);
        layout.putConstraint(SpringLayout.SOUTH, pane, 10, SpringLayout.SOUTH, start);
        layout.putConstraint(SpringLayout.EAST, pane, 450, SpringLayout.EAST, desc);
        window.setResizable(true);
        window.pack();
        window.setVisible(true);
    }
}

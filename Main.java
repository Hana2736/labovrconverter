import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	public Main() {
		String[] vidNames = {"01_Flag", "02_AquariumCallorhinus_00", "03_Deer_00", "04_Magic", "010_Dog_00", "06_WaterTap_00", "07_EatSushi_00", "08_Domino", "09_Cherry_00", "10_Cherry_01", "11_AquariumSalamander_00", "12_VirtualBoy", "13_SoapBubble", "14_BallMouth_00", "110_Cup_00", "16_Cup_01", "17_DogBaby", "18_Paint", "19_EatItalian_00", "20_AquariumPenguin_00", "21_TableCloth", "22_Taketonbo", "23_AquariumMedusa_00", "24_Origami", "210_MusicHappyBirthday", "26_EatChinese_00", "27_Kusudama", "28_Cat_00", "29_Baton", "30_UpsideDown", "31_Piano", "32_AquariumSea_00", "33_MusicTheme"};
		UIManager.put("Panel.background", Color.black);
		UIManager.put("OptionPane.background", Color.black);
		UIManager.put("OptionPane.messageForeground", Color.white);
		UIManager.put("Label.foreground", Color.white);
		UIManager.put("TextArea.background", Color.black);
		UIManager.put("TextArea.foreground", Color.white);
		UIManager.put("TextField.background", Color.black);
		UIManager.put("TextField.foreground", Color.white);
		UIManager.put("FormattedTextField.background", Color.black);
		UIManager.put("FormattedTextField.foreground", Color.white);
		UIManager.put("Button.background", Color.darkGray);
		UIManager.put("Button.darkShadow", Color.gray);
		UIManager.put("Button.disabledText", Color.lightGray);
		UIManager.put("Button.foreground", Color.white);
		UIManager.put("FileChooser.background", Color.black);
		UIManager.put("List.background", Color.black);
		UIManager.put("ScrollPane.background", Color.black);
		UIManager.put("List.foreground", Color.white);
		UIManager.put("ComboBox.background", Color.black);
		UIManager.put("ComboBox.foreground", Color.white);
		UIManager.put("ProgressBar.background", Color.black);
		UIManager.put("ProgressBar.foreground", new Color(0,104,10));
		SpringLayout layout = new SpringLayout();
		JFrame window = new JFrame("Labo VR Video Converter 0.5");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container pane = window.getContentPane();
		window.setLayout(layout);
		JLabel desc = new JLabel("Convert videos to the format that Labo VR uses");
		layout.putConstraint(SpringLayout.WEST, desc, 10, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, desc, 10, SpringLayout.NORTH, pane);
		pane.add(desc);
		JFileChooser inPathPick = new JFileChooser();
		JLabel inPathDesc = new JLabel("Input video file");
		layout.putConstraint(SpringLayout.NORTH, inPathDesc, 35, SpringLayout.SOUTH, desc);
		layout.putConstraint(SpringLayout.WEST, inPathDesc, 0, SpringLayout.WEST, desc);
		inPathDesc.setFont(new Font("Arial", Font.BOLD, 15));
		pane.add(inPathDesc);
		JTextArea inPathSt = new JTextArea("<input path>");
		inPathSt.setFont(new Font("Arial", Font.PLAIN, 12));
		desc.setFont(new Font("Arial", Font.BOLD, 20));
		layout.putConstraint(SpringLayout.WEST, inPathSt, 0, SpringLayout.WEST, inPathDesc);
		layout.putConstraint(SpringLayout.NORTH, inPathSt, 10, SpringLayout.SOUTH, inPathDesc);
		pane.add(inPathSt);
		JButton inPathBrowse = new JButton("Browse...");
		layout.putConstraint(SpringLayout.WEST, inPathBrowse, 10, SpringLayout.EAST, inPathSt);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, inPathBrowse, 0, SpringLayout.VERTICAL_CENTER, inPathSt);
		inPathBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inPathPick.showDialog(pane, "Open");
				inPathSt.setText(inPathPick.getSelectedFile().toString());
			}
		});
		pane.add(inPathBrowse);
		JFileChooser outPathPick = new JFileChooser();
		outPathPick.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		JLabel outPathDesc = new JLabel("Output video directory");
		outPathDesc.setFont(inPathDesc.getFont());
		layout.putConstraint(SpringLayout.NORTH, outPathDesc, 10, SpringLayout.SOUTH, inPathSt);
		layout.putConstraint(SpringLayout.WEST, outPathDesc, 0, SpringLayout.WEST, desc);
		pane.add(outPathDesc);
		JTextArea outPathStr = new JTextArea("<output path>");
		outPathStr.setFont(inPathSt.getFont());
		layout.putConstraint(SpringLayout.WEST, outPathStr, 0, SpringLayout.WEST, desc);
		layout.putConstraint(SpringLayout.NORTH, outPathStr, 10, SpringLayout.SOUTH, outPathDesc);
		pane.add(outPathStr);
		JButton outPathBrowse = new JButton("Browse...");
		layout.putConstraint(SpringLayout.WEST, outPathBrowse, 10, SpringLayout.EAST, outPathStr);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, outPathBrowse, 0, SpringLayout.VERTICAL_CENTER, outPathStr);
		outPathBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				outPathPick.showDialog(pane, "Open");
				outPathStr.setText(outPathPick.getSelectedFile().toString());
			}
		});
		pane.add(outPathBrowse);
		JSpinner overwrite = new JSpinner(new SpinnerNumberModel(1, 1, 33, 1));
		JLabel overDesc = new JLabel("Labo video to replace");
		overDesc.setFont(inPathDesc.getFont());
		layout.putConstraint(SpringLayout.NORTH, overDesc, 10, SpringLayout.SOUTH, outPathStr);
		layout.putConstraint(SpringLayout.WEST, overDesc, 0, SpringLayout.WEST, outPathStr);
		pane.add(overDesc);
		layout.putConstraint(SpringLayout.NORTH, overwrite, 10, SpringLayout.SOUTH, overDesc);
		layout.putConstraint(SpringLayout.WEST, overwrite, 0, SpringLayout.WEST, overDesc);
		pane.add(overwrite);
		JLabel ffmPathDesc = new JLabel("FFMPEG binary path");
		ffmPathDesc.setFont(overDesc.getFont());
		layout.putConstraint(SpringLayout.NORTH, ffmPathDesc, 10, SpringLayout.SOUTH, overwrite);
		layout.putConstraint(SpringLayout.WEST, ffmPathDesc, 0, SpringLayout.WEST, desc);
		pane.add(ffmPathDesc);
		JTextArea ffmPath = new JTextArea("<ffmpeg path>");
		ffmPath.setFont(outPathStr.getFont());
		layout.putConstraint(SpringLayout.WEST, ffmPath, 0, SpringLayout.WEST, inPathDesc);
		layout.putConstraint(SpringLayout.NORTH, ffmPath, 10, SpringLayout.SOUTH, ffmPathDesc);
		JButton ffmPathBrowse = new JButton("Browse...");
		layout.putConstraint(SpringLayout.WEST, ffmPathBrowse, 10, SpringLayout.EAST, ffmPath);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, ffmPathBrowse, 0, SpringLayout.VERTICAL_CENTER, ffmPath);
		JSpinner qual = new JSpinner(new SpinnerNumberModel(15, .01, 500, .1));
		JLabel qualDesc = new JLabel("Quality of video in MB/s");
		qualDesc.setFont(ffmPathDesc.getFont());
		layout.putConstraint(SpringLayout.NORTH, qualDesc, 10, SpringLayout.SOUTH, ffmPath);
		layout.putConstraint(SpringLayout.WEST, qualDesc, 0, SpringLayout.WEST, outPathStr);
		pane.add(qualDesc);
		layout.putConstraint(SpringLayout.NORTH, qual, 10, SpringLayout.SOUTH, qualDesc);
		layout.putConstraint(SpringLayout.WEST, qual, 0, SpringLayout.WEST, qualDesc);
		pane.add(qual);
		JButton start = new JButton("ffmpeg not found!");
		start.setEnabled(false);
		JFileChooser ffmPathPick = new JFileChooser();
		if (Files.exists(Paths.get(System.getProperty("user.home") + "/Documents/ffmpath.bin"))) {
			try {
				List<String> ffm = Files.readAllLines(Paths.get(System.getProperty("user.home") + "/Documents/ffmpath.bin"));
				ffmPath.setText(ffm.get(0));
				start.setText("Go!");
				start.setEnabled(true);
			} catch (Exception pathReadEx) {
				System.out.println("IO Error! " + pathReadEx);
			}
		}
		ffmPathBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ffmPathPick.showDialog(pane, "Open");
				ffmPath.setText(ffmPathPick.getSelectedFile().toString());
				if (Files.exists(Paths.get(ffmPath.getText()))) {
					try {
						Files.write(Paths.get(System.getProperty("user.home") + "/Documents/ffmpath.bin"), ffmPath.getText().getBytes());
					} catch (Exception savePathEx) {
						System.out.println("Writing error! " + savePathEx);
					}
					start.setText("Go!");
					start.setEnabled(true);
				}
			}
		});
		layout.putConstraint(SpringLayout.NORTH, start, 35, SpringLayout.SOUTH, qual);
		layout.putConstraint(SpringLayout.WEST, start, 0, SpringLayout.WEST, qual);
		JFrame progressWindow = new JFrame("Progress");
		JProgressBar progressBar = new JProgressBar();
		progressWindow.add(progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] ffmpegArgs = new String[]{ffmPath.getText(), "-i", inPathSt.getText(), "-f", "null", "-"};
				try {
				    window.setVisible(false);
					JOptionPane.showConfirmDialog(pane, "The conversion is about to start. You'll be notified when it's done.\nThis may take a very long time, depending on the source video and your set quality.", "Labo VR Video Converter 0.5", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					long timeStart = System.currentTimeMillis();
					Process proc = Runtime.getRuntime().exec(ffmpegArgs);
					BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
					String lastLine = "";
					String frameCount = "";
					while ((lastLine = error.readLine()) != null) {
						if (lastLine.startsWith("frame=")) {
							frameCount = lastLine.substring(0, lastLine.indexOf("fps")).replaceAll("frame=", "").trim();
						}
					}
					double maxFrames = Double.parseDouble(frameCount);
					ffmpegArgs = new String[]{ffmPath.getText(), "-y", "-i", inPathSt.getText(), "-c:v", "libvpx-vp9", "-c:a", "libvorbis", "-s", "2560x1440", "-b:v", qual.getValue().toString() + 'M', outPathStr.getText() + "/Cvr_Vr180_" + vidNames[(int) overwrite.getValue() - 1] + ".webm"};
					proc = Runtime.getRuntime().exec(ffmpegArgs);
					error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
					String progress = "";
					int progBar = 0;
					progressBar.setStringPainted(true);
					progressWindow.setSize(400, 100);
					progressWindow.setResizable(false);
					progressWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					try {
						progressWindow.setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
					} catch (Exception imageFail) {
						System.out.println("Icon failed. " + imageFail.getMessage());
					}
					progressWindow.setVisible(true);
					new AsyncThreadRunner(lastLine, error, progress, maxFrames, progressBar, pane, timeStart, progressWindow).start();
				} catch (Exception ffmEx) {
					System.out.println("FFMPEG failed! " + ffmEx.getMessage());
				}
			}
		});
		pane.add(ffmPathBrowse);
		pane.add(ffmPath);
		pane.add(start);
		layout.putConstraint(SpringLayout.SOUTH, pane, 10, SpringLayout.SOUTH, start);
		layout.putConstraint(SpringLayout.EAST, pane, 450, SpringLayout.EAST, desc);
		window.setResizable(true);
		try {
			window.setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
		} catch (Exception imageFail) {
			System.out.println("Icon failed. " + imageFail);
		}
		window.pack();
		window.setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}

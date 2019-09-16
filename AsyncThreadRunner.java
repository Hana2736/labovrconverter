import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

public class AsyncThreadRunner extends Thread {
	String lastLine;
	BufferedReader error;
	JProgressBar progressBar;
	String progress;
	double maxFrames;
	Container pane;
	long timeStart;
	JFrame progressWindow;

	public AsyncThreadRunner(String lastLine, BufferedReader error, String progress, double maxFrames, JProgressBar progressBar, Container pane, long timeStart, JFrame progressWindow) {
		this.lastLine = lastLine;
		this.error = error;
		this.progress = progress;
		this.progressBar = progressBar;
		this.maxFrames = maxFrames;
		this.pane = pane;
		this.timeStart = timeStart;
		this.progressWindow = progressWindow;
	}

	public void run() {
		int update = 0;
		while (true) {
			long timePassed = (System.currentTimeMillis() - timeStart) / 1000L;
			try {
				lastLine = error.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			if (lastLine != null) {
				if (lastLine.startsWith("frame=")) {
					update++;
					progress = lastLine.substring(0, lastLine.indexOf("fps")).replaceAll("frame=", "").trim();
					progressBar.setValue((int) ((Double.parseDouble(progress) / maxFrames) * 100));
					if (update == 15) {
						progressWindow.setTitle("Progress - " + (int)(((timePassed / ((Double.parseDouble(progress) / maxFrames) * 100)) * (100 - (Double.parseDouble(progress) / maxFrames) * 100))/60) +" minutes left");
						update = 0;
					}
				}
			} else {
				break;
			}
		}
		progressWindow.setVisible(false);
		JOptionPane.showConfirmDialog(pane, "Video conversion finished!\nRemember to put it in /atmosphere/titles/0100165003504000/RomFS/Learn/", "Labo VR Video Converter 0.5", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
}

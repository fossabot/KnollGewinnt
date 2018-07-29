package errorMessages;

import javax.swing.JOptionPane;

public class AudioErrorMessage {

	public static void showError() {
		JOptionPane.showMessageDialog(null, "Audio Error. Can't play background music.", "Warning",
				JOptionPane.WARNING_MESSAGE);

	}
}

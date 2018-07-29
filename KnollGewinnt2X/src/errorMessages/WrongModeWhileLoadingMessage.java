package errorMessages;

import javax.swing.JOptionPane;

public class WrongModeWhileLoadingMessage {
	public static void ShowError() {
		JOptionPane.showMessageDialog(null,
				"You cant load a game in another Mode (single / multi). Please change mode.", "Warning",
				JOptionPane.WARNING_MESSAGE);
	}
}

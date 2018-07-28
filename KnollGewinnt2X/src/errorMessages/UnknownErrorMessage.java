package errorMessages;

import javax.swing.JOptionPane;

public class UnknownErrorMessage {

	public static void ShowError() {
		JOptionPane.showMessageDialog(null, "Unknown Error. Please contact us@knollDevTeam.", "Warning",
				JOptionPane.WARNING_MESSAGE);
	}
}

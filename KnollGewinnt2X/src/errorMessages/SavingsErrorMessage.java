package errorMessages;

import javax.swing.JOptionPane;

public class SavingsErrorMessage {

	public static void showError() {
		JOptionPane.showMessageDialog(null, "Corrupt File Error.", "Warning", JOptionPane.WARNING_MESSAGE);

	}

}

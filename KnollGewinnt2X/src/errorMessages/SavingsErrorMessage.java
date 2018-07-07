package errorMessages;

import javax.swing.JOptionPane;

public class SavingsErrorMessage {

	int reply = -1;

	public SavingsErrorMessage() {
		JOptionPane.showMessageDialog(null, "Corrupt File Error.", "Warning", JOptionPane.WARNING_MESSAGE);
	}


}

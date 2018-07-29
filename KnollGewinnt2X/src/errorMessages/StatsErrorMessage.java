package errorMessages;

import javax.swing.JOptionPane;

public class StatsErrorMessage {

	public static int showError() {
		JOptionPane.showMessageDialog(null, "Corrupt File Error.", "Warning", JOptionPane.WARNING_MESSAGE);
		return JOptionPane.showConfirmDialog(null, "Create new DATA.KG Files?", "Confirm Dialog",
				JOptionPane.YES_NO_OPTION);
	}

}

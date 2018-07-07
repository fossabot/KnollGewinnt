package errorMessages;

import javax.swing.JOptionPane;

public class StatsErrorMessage {

	int reply = -1;

	public StatsErrorMessage() {
		JOptionPane.showMessageDialog(null, "Corrupt File Error.", "Warning", JOptionPane.WARNING_MESSAGE);
		int reply = JOptionPane.showConfirmDialog(null, "Create new DATA.KG Files?", "Confirm Dialog",
				JOptionPane.YES_NO_OPTION);
	}
	
	public int getReply() {
		return reply;
	}

}

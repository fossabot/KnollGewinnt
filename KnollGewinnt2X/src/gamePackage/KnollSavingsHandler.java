package gamePackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLDecoder;

import javax.swing.JOptionPane;

import errorMessages.SavingsErrorMessage;

public class KnollSavingsHandler implements KnollFileHandler {

	public KnollSavingsHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeFile(Object object, Object object2) throws Exception {
		BasePanel basePanel = (BasePanel) object;
		int selectedMode = (int) object2;
		URL temp = MainFrame.class.getResource("save.kg");
		FileWriter fw = new FileWriter(URLDecoder.decode(temp.getPath()));
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("<HEAD>KNOLLGEWINNT SAVINGS<HEAD>");
		bw.newLine();
		bw.write("<GAMEINFO> TIME: '" + System.currentTimeMillis() + "' MODE: '" + selectedMode + "'<GAMEINFO>");
		bw.newLine();

		for (int i = 0; i < basePanel.getPlayBoard().length; i++) {
			for (int j = 0; j < basePanel.getPlayBoard()[i].length; j++) {
				if (basePanel.getPlayBoard()[i][j].isFilled() == true)
					bw.write("1.");
				if (basePanel.getPlayBoard()[i][j].isFilled() == false)
					bw.write("0.");
				if (basePanel.getPlayBoard()[i][j].getOwner() == -1)
					bw.write("NO");
				if (basePanel.getPlayBoard()[i][j].getOwner() == 1)
					bw.write("P1");
				if (basePanel.getPlayBoard()[i][j].getOwner() == 2)
					bw.write("P2");
				if (basePanel.getPlayBoard()[i][j].getOwner() == 3)
					bw.write("KI");
				bw.write("-");
			}
			bw.newLine();
		}
		bw.close();
		JOptionPane.showMessageDialog(null, "Game succesfully saved.");

	}

	@Override
	public Object[] readFile(Object object) throws Exception {
		BasePanel basePanel = (BasePanel) object;
		URL temp = MainFrame.class.getResource("save.kg");
		FileReader fr = new FileReader(URLDecoder.decode(temp.getPath()));
		BufferedReader br = new BufferedReader(fr);
		String[] rows = new String[basePanel.getPlayBoard().length];
		int readMode = 1;
		if (br.readLine().equals("<HEAD>KNOLLGEWINNT SAVINGS<HEAD>")) {

			try {
				readMode = Integer.parseInt(br.readLine().split("'")[3]);
			} catch (NumberFormatException e) {
				br.close();
				dataErrorMessage();
			}
			for (int i = 0; i < rows.length; i++) {
				rows[i] = br.readLine();
			}
		} else {
			br.close();
			dataErrorMessage();
		}
		br.close();
		Object[] returnValues = new Object[2];
		returnValues[0] = readMode;
		returnValues[1] = rows;
		return returnValues;
	}

	private void dataErrorMessage() {
		SavingsErrorMessage error = new SavingsErrorMessage();
	}

}

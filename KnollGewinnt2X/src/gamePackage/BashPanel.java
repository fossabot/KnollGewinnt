package gamePackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BashPanel extends JPanel{

	JButton enter;
	private JTextArea answer2;
	private JLabel answer;
	private JTextField input;
	public BashPanel() {
	
		input = new JTextField("");
		answer = new JLabel("[SERVER]");
		answer2 = new JTextArea("knollgewinnt@knollserver.westus.cloudapp.azure.com: ");
		answer2.setPreferredSize(new Dimension(600, 400));
		enter = new JButton("SEND");
		
		this.setLayout(new BorderLayout());
		this.add(enter, BorderLayout.SOUTH);
		this.add(input, BorderLayout.NORTH);
		//this.add(answer);
		this.add(answer2, BorderLayout.CENTER);
	}
	
	public void addActionListener(ActionListener e) {
		enter.addActionListener(e);
	}

	public String getText() {
		
		return input.getText();
	}
	
	public void setAnswer(String s) {
		System.out.println(s);
		answer2.setText(answer2.getText()+"\n[SERVER] "+s); 

		System.out.println("set answer label");
	}

}

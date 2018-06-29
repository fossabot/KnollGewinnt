package gamePackage;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import underConstruction.KnollClient;
import underConstruction.KnollServer;

public class NetworkPanel extends JPanel {

	public NetworkPanel() {
		super();
		this.setLayout(new FlowLayout());
		JButton server = new JButton("Server");
		JButton client = new JButton("Client");
		JTextField field = new JTextField("Put Text here");
		
		this.add(server);
		this.add(client);
		this.add(field);
		ActionListener buttonListen = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==server) {
					KnollServer server = new KnollServer(8000);
					server.startListening();
					
				}
				if(e.getSource()==client) {
					KnollClient client = new KnollClient("localhost", 8000);
					client.sendMessage(field.getText());
					
				}
				
			}
		};
		
		client.addActionListener(buttonListen);
		server.addActionListener(buttonListen);
	}

}

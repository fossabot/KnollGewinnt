package underConstruction;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class KnollClient {

	public static void main(String[] args) {
	
		KnollClient client = new KnollClient("knollserver.westus.cloudapp.azure.com", 8000);
		client.sendMessage("Hallo");
	}
	
	private InetSocketAddress adress;
	
	public KnollClient(String adress, int port) {
		this.adress = new InetSocketAddress(adress, port);
	}
	
	public void sendMessage(String msg) {
		Socket socket = new Socket();
		System.out.println("[Client] Startet...");
		try {
			System.out.println("[Client] Verbinde...");
			socket.connect(this.adress, 5000);
			System.out.println("[Client] Verbunden.");
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pw.println(msg);
			pw.flush();
			
			//wieder schließen
			
			pw.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

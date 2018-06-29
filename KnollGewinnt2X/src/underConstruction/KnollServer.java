package underConstruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class KnollServer {

	private int port;

	public KnollServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		KnollServer server = new KnollServer(8000);
		server.startListening();
	}

	public void startListening() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("[Server] Startet...");
						ServerSocket socket = new ServerSocket(port);
						//Repräsentation vom CLient
						System.out.println("[Server] Wartet auf Verbindung...");
						Socket client = socket.accept(); //alles was reinkommt
						System.out.println("[Server] Client verbunden: " + client.getRemoteSocketAddress());
						Scanner s = new Scanner(new BufferedReader(new InputStreamReader(client.getInputStream())));
						if (s.hasNextLine()) {
							System.out.println("[Server] Message from Client: " + s.nextLine());
						}
						//Das ganze wieder schließen
						s.close();
						client.close();
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}

			}
		}).start();
	}

}

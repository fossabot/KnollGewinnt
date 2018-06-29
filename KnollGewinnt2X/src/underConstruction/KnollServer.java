package underConstruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class KnollServer {

	private final ServerSocket server;

	public KnollServer(int port) throws IOException {
		server = new ServerSocket(port);
	}

	private void verbinde() {

		System.out.println("Verbinde");
		
		while (true) {
			Socket socket = null;
			try {
				socket = server.accept();
				reinRaus(socket);
			}

			catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (socket != null)
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

	private void reinRaus(Socket socket) throws IOException {
		System.out.println("ReinRaus");
		BufferedReader rein = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream raus = new PrintStream(socket.getOutputStream());
		String s;

		while (rein.ready()) {
			s = rein.readLine();
			raus.println(s);
		}
	}

	public static void main(String[] args) throws IOException {
		KnollServer server = new KnollServer(6131);
		server.verbinde();
	}

}

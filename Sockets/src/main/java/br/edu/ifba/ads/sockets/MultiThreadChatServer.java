package br.edu.ifba.ads.sockets;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import br.edu.ifba.ads.produtorconsumidor.Consumer;
import br.edu.ifba.ads.produtorconsumidor.Producer;
import br.edu.ifba.ads.produtorconsumidor.Programa;
import br.edu.ifba.ads.produtorconsumidor.Semaphore;

public class MultiThreadChatServer {

	// Declaration section:
	// declare a server socket and a client socket for the server
	// declare an input and an output stream

	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;
	static Programa programa = new Programa();

	// This chat server can accept up to 10 clients' connections

	static clientThread t[] = new clientThread[10];

	public static void main(String args[]) {

		// The default port

		int port_number = 2222;

		if (args.length < 1) {
			System.out.println("Usage: java MultiThreadChatServer \n" + "Now using port number=" + port_number);
		} else {
			port_number = Integer.valueOf(args[0]).intValue();
		}

		// Initialization section:
		// Try to open a server socket on port port_number (default 2222)
		// Note that we can't choose a port less than 1023 if we are not
		// privileged users (root)

		try {
			serverSocket = new ServerSocket(port_number);
		} catch (IOException e) {
			System.out.println(e);
		}

		// Create a socket object from the ServerSocket to listen and accept
		// connections.
		// Open input and output streams for this socket will be created in
		// client's thread since every client is served by the server in
		// an individual thread

		while (true) {
			try {
				clientSocket = serverSocket.accept();
				for (int i = 0; i <= 9; i++) {
					if (t[i] == null) {
						(t[i] = new clientThread(clientSocket, t)).start();
						break;
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	public static void setPrograma(Programa programa2) {
		programa = programa2;
	}

	public static Programa getPrograma() {
		return programa;
	}
}

// This client thread opens the input and the output streams for a particular client,
// ask the client's name, informs all the clients currently connected to the 
// server about the fact that a new client has joined the chat room, 
// and as long as it receive data, echos that data back to all other clients.
// When the client leaves the chat room this thread informs also all the
// clients about that and terminates. 

class clientThread extends Thread {

	DataInputStream is = null;
	PrintStream os = null;
	Socket clientSocket = null;
	clientThread t[];
	Programa programa = MultiThreadChatServer.getPrograma();

	public clientThread(Socket clientSocket, clientThread[] t) {
		this.clientSocket = clientSocket;
		this.t = t;
	}

	public void run() {
		String line;
		String comando;
		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());

			while (true) {
				os.println("Entre com o comando consumir(c) ou produzir(p).");
				comando = is.readLine();

				if (comando.equals("c") || comando.equals("C")) {
					Consumer c = new Consumer(programa);
					c.start();
					for (int i = 0; i <= 9; i++) {
						if (t[i] != null && t[i] == this) {
							if (programa.getItemCount() <= 0) {
								t[i].os.println("Sem itens em estoque, tente produzir ");
							} else {
								t[i].os.println("Quantidade de itens em estoque depois de consumir: "
										+ (programa.getItemCount() - 1));
							}
						}
					}
				} else if (comando.equals("p") || comando.equals("P")) {
					Producer p = new Producer(programa);
					p.start();
					for (int i = 0; i <= 9; i++) {
						if (t[i] != null && t[i] == this) {
							t[i].os.println("Quantidade de itens em estoque depois de produzir: "
									+ (programa.getItemCount() + 1));
						}
					}
				} else if (comando.equals("/quit") || comando.equals("P")) {
					break;
				} else {
					os.println("Comando incorreto, tente novamente");
				}
				MultiThreadChatServer.setPrograma(programa);
			}
			for (int i = 0; i <= 9; i++)
				if (t[i] != null && t[i] != this)
					t[i].os.println("*** The cliente is leaving the chat room !!! ***");

			// Clean up:
			// Set to null the current thread variable such that other client could
			// be accepted by the server

			for (int i = 0; i <= 9; i++)
				if (t[i] == this)
					t[i] = null;

			// close the output stream
			// close the input stream
			// close the socket

			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
		}
		;
	}
}

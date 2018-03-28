package br.com.dec.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

	private Socket socket;

	public DistribuirTarefas(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			System.out.println("Distribuindo tarefas para: " + socket);

			Scanner entradaCliente = new Scanner(socket.getInputStream());

			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

			while (entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println("Comando recebido " + comando);

				switch (comando) {
				
					case "C1": {
						saidaCliente.println("Confirma comando C1");
						break;
					}
	
					case "C2": {
						saidaCliente.println("Confirma comando C2");
						break;
					}
					default: {
						saidaCliente.println("Comando nao encontrado");
					}
					}
	
					System.out.println(comando);
			}

			saidaCliente.close();
			entradaCliente.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}

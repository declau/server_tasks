package br.com.dec.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

	private ServerSocket servidor;
	private ExecutorService threadPool;
	private boolean estaRodando;

	public ServidorTarefas() throws IOException {
		System.out.println("----- Iniciando servidor -----");
		this.servidor = new ServerSocket(12345);
		this.threadPool = Executors.newCachedThreadPool();
		this.estaRodando = true;
	}

	public void rodar() throws IOException {
		// TODO Auto-generated method stub
		while (this.estaRodando) {
			try {
				Socket socket = servidor.accept();
				System.out.println("aceitando novo cliente na porta: " + socket.getPort());

				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this);
				threadPool.execute(distribuirTarefas);
			} catch (SocketException e) {
				System.out.println("SocketException, esta rodando? " + this.estaRodando);
			}

		}

	}

	public void stop() throws IOException {
		estaRodando = false;
		servidor.close();
		threadPool.shutdown();
	}

	public static void main(String[] args) throws Exception {

		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.stop();

	}
}

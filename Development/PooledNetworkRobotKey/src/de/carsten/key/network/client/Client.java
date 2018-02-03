package de.carsten.key.network.client;

import java.io.IOException;
import java.net.UnknownHostException;

import de.carsten.key.network.server.Server;

public class Client {

	public static void main(String[] args) throws UnknownHostException,
			ClassNotFoundException, IOException {
		String ip = "127.0.0.1";
		if (args.length > 0) {
			ip = args[0];
		}
		de.tubs.carsten.sq2.pooling.distributed.Client c = new de.tubs.carsten.sq2.pooling.distributed.Client(
				ip, Server.PORT);

		if (c.isRestart()) {
			String separator = System.getProperty("file.separator");
			String classpath = System.getProperty("java.class.path");
			String path = System.getProperty("java.home") + separator + "bin"
					+ separator + "java";
			ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp",
					classpath, Client.class.getName(), ip);
			processBuilder.start();
		}
	}
}

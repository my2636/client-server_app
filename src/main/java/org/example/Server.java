package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static final Integer PORT = 8089;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String infoFromClient = bufferedReader.readLine();
                    System.out.printf("Новое подключение принято. Информация: %s, порт: %d%n", infoFromClient, clientSocket.getPort());
                    printWriter.printf("Привет! Твой порт %d%n", clientSocket.getPort());
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
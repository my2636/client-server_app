package com.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/*В серверной и клиентской части сделайте последовательность вызовов в рамках одной сессии таким образом, чтобы получился
 некий диалог между клиентом и сервером, содержащий от 3 до 5 (или больше) шагов. Например,
при подключении сервер спрашивает: Write your name. Клиент читает это сообщение и отправляет ему имя.
Сервер читает имя, запоминает его, и задает следующий вопрос: Are you child? (yes/no). Клиент отвечает yes или no.
На что сервер, если увидел yes, отвечает Welcome to the kids area, %username%! Let's play!, а если no, то Welcome to the
adult zone, %username%! Have a good rest, or a good working day! (где %username% - имя клиента из предыдущего шага)*/


public class Server {
    public static final Integer PORT = 8084;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String infoFromClient = reader.readLine();
                    System.out.printf("Новое подключение принято. Информация: %s, порт: %d%n", infoFromClient, clientSocket.getPort());
                    writer.printf("Привет! Твой порт %d%n", clientSocket.getPort());
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
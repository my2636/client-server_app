package com.my;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


/*В серверной и клиентской части сделайте последовательность вызовов в рамках одной сессии таким образом, чтобы получился
 некий диалог между клиентом и сервером, содержащий от 3 до 5 (или больше) шагов. Например,
при подключении сервер спрашивает: Write your name. Клиент читает это сообщение и отправляет ему имя.
Сервер читает имя, запоминает его, и задает следующий вопрос: Are you child? (yes/no). Клиент отвечает yes или no.
На что сервер, если увидел yes, отвечает Welcome to the kids area, %username%! Let's play!, а если no, то Welcome to the
adult zone, %username%! Have a good rest, or a good working day! (где %username% - имя клиента из предыдущего шага)*/


public class Server {
    public static final Integer PORT = 8083;
    static int catCount = 1;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Сервер запущен");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.println(reader.readLine());
                    writer.println("Приветствую! На сервере живет хороший кот. Я могу: 1. Показать кота" +
                            " 2. Принять нового кота 3. Отдать своего кота");
                    String clientAnswer;
                    while(!(clientAnswer = reader.readLine()).equalsIgnoreCase("выход")) {
                        String serverAnswer = getAction(clientAnswer);
                        writer.println(serverAnswer);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String getAction(String clientAnswer) {
        String serverAnswer;
        switch (clientAnswer) {
            case "1":
                serverAnswer = "Вот кот - он хранитель сервера. Какие коты еще интересуют?";
                break;
            case "2":
                catCount++;
                serverAnswer = "Давай кота. Теперь на сервере " + catCount + " котов, они подружились. Какие коты еще интересуют?";
                break;
            case "3":
                serverAnswer = catCount == 0 ? "Коты закончились, приходите позже. Сейчас сервер в режиме приема котов." :
                        "Вот тебе кот, он твой. Заботься о нем. Пока, кот! Что-то еще подсказать по котам?";
                if (catCount !=0) {
                    catCount--;
                }
                break;
            default: serverAnswer = "Ну как без котов-то.";
        }
        return serverAnswer;
    }
}
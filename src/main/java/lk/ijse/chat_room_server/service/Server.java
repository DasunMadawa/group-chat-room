package lk.ijse.chat_room_server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    ClientHandler clientHandler;

    public Server(){
        launchServerThread();

    }

    public void launchServerThread(){
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(3005);
                System.out.println("server started");

                while (true) {
                    socket = serverSocket.accept();
                    System.out.println("server accept client");
                    new Thread(new ClientHandler(socket)).start();

                }



            } catch (IOException e) {
                if (serverSocket != null){
                    try {
                        serverSocket.close();
                        System.out.println("sever closed");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();

    }



}

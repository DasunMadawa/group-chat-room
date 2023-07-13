package lk.ijse.chat_room_server.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> arrayList = new ArrayList<>();

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String userName;

    public ClientHandler(Socket socket) {
        System.out.println("constructor");
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());

            userName = this.dataInputStream.readUTF();
            System.out.println(userName);

            boolean isDuplicate = duplicateRemover(userName);
            if (isDuplicate){
                closeSocket();
                return;
            }

            this.arrayList.add(this);
            System.out.println("client added , clients : " + arrayList.size() );

        } catch (IOException e) {
            closeSocket();
        }


    }

    public boolean duplicateRemover(String userName){
        for (ClientHandler clientHandler : arrayList) {
            if ( clientHandler.userName.equals(userName) ){
                try {
                    dataOutputStream.writeUTF("$#-DuplicateUser-#$");
                    dataOutputStream.flush();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        return false;

    }


    public void sendMessagesToAllClients(String message) {
        System.out.println(this.arrayList.size());
        if (!message.equals("$#-Image-#$")) {
            for (ClientHandler clientHandler : arrayList) {
                try {
                    if (clientHandler.userName.equals(userName)) {
                        continue;
                    }

                    clientHandler.dataOutputStream.writeUTF(userName + " : " + message);
                    clientHandler.dataOutputStream.flush();

                } catch (IOException e) {

                }
            }
        }else{
            try {
                int length = dataInputStream.readInt();
                byte[] bytesAr = new byte[length];
                dataInputStream.readFully(bytesAr);

                for (ClientHandler clientHandler : arrayList) {
                    if (clientHandler.userName.equals(userName)) {
                        continue;
                    }

                    clientHandler.dataOutputStream.writeUTF("$#-Image-#$");
                    clientHandler.dataOutputStream.writeUTF(userName);
                    clientHandler.dataOutputStream.writeInt(bytesAr.length);
                    clientHandler.dataOutputStream.write(bytesAr);
                    clientHandler.dataOutputStream.flush();
                    System.out.println("photo sent");
                }

            } catch (IOException e) {

            }

        }

    }


    @Override
    public void run() {
        System.out.println("run");
        while (!this.socket.isClosed()) {
            try {
                String message = this.dataInputStream.readUTF();
                System.out.println(message);
                sendMessagesToAllClients(message);

            } catch (IOException e) {
                removeClientHandler(this);


            }

        }

    }

    public void removeClientHandler(ClientHandler clientHandler) {
        closeSocket();
        arrayList.remove(clientHandler);


    }

    public void closeSocket() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

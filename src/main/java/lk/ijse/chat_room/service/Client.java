package lk.ijse.chat_room.service;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.chat_room.controller.ClientChatFormController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String userName;
    private ClientChatFormController clientChatFormController;

    public Client(String ipAddress, int portNo, String userName) {
        try {
            this.socket = new Socket(ipAddress, portNo);
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.userName = userName;

            launchUI();
            setMgReceiver();
            sendMg(userName);

        } catch (IOException e) {
            System.out.println("Server Is Offline");
        }

    }

    public void setMgReceiver() {
        new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    String message = dataInputStream.readUTF();
                    if (message.equals("$#-Image-#$")) {
                        receiveImage();
                    }else if (message.equals("$#-DuplicateUser-#$")){
                        Platform.runLater(() -> {
                            duplicateUsers();
                        });
                    }else {
                        Platform.runLater(() -> {
                            clientChatFormController.displayMg(message);
                        });
                    }

                } catch (IOException e) {
                    closeSocket();
                }

            }

        }).start();

    }

    private void duplicateUsers() {
        new Alert(Alert.AlertType.ERROR , "Already Login This User !" , ButtonType.OK).show();
        Stage stage = (Stage) clientChatFormController.getRoot().getScene().getWindow();
        stage.close();

    }

    private void receiveImage() {
        try {
            System.out.println();
            String sender = dataInputStream.readUTF();
            int length = dataInputStream.readInt();
            byte[] bytesAr = new byte[length];
            dataInputStream.readFully(bytesAr);
            Platform.runLater(() -> {
                clientChatFormController.displayImage(sender , bytesAr);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchUI() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/client_chat_form.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setResizable(false);
            stage.setTitle(userName);
            stage.show();

            this.clientChatFormController = fxmlLoader.getController();
            clientChatFormController.setClient(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMg(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();

        } catch (IOException e) {
            closeSocket();
        }

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

    public void sendImage(byte[] bytesAr) {
        try {
            dataOutputStream.writeUTF("$#-Image-#$");
            dataOutputStream.writeInt(bytesAr.length);
            dataOutputStream.write(bytesAr);
            dataOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

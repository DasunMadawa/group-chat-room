package lk.ijse.chat_room.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import lk.ijse.chat_room.service.Client;
import java.io.*;
import java.nio.file.Files;
import java.util.Optional;

public class ClientChatFormController {

    @FXML
    public GridPane emojiBarGrid;

    @FXML
    private AnchorPane emojiBarPane;

    @FXML
    private VBox vBox;

    @FXML
    private JFXTextField mgTxt;

    @FXML
    private JFXButton sendBtn;

    @FXML
    private AnchorPane root;

    @FXML
    private Client client;

    private final String[] emojis = {
            "\uD83D\uDE03", // 🤣
            "\uD83D\uDE04", // 😄
            "\uD83D\uDE00", // 😀
            "\uD83D\uDE02", // 😂
            "\uD83D\uDE0C", // 😌
            "\uD83D\uDE0D", // 😍
            "\uD83D\uDE0E", // 😎
            "\uD83D\uDE0F", // 😏
            "\uD83D\uDE10", // 😐
            "\uD83D\uDE11", // 😑
            "\uD83D\uDE12", // 😒
            "\uD83D\uDE05", // 😅
            "\uD83D\uDE06", // 😆
            "\uD83D\uDE08", // 😈
            "\uD83D\uDE09", // 😉
            "\uD83D\uDE0A", // 😊
            "\uD83D\uDE13", // 😓
            "\uD83D\uDE0B", // 😋
            "\uD83D\uDE01", // 😁
            "\uD83D\uDE07"  // 😇
    };

    @FXML
    public void initialize() {
        emojiBarPane.setVisible(false);
        createEmojiBar();

    }

    public AnchorPane getRoot() {
        return root;
    }

    @FXML
    public void mgTxtOnAction(ActionEvent actionEvent) {
        sendBtnOnAction(actionEvent);
    }

    @FXML
    public void sendBtnOnAction(ActionEvent actionEvent) {
        this.client.sendMg(mgTxt.getText());

        HBox hBox = new HBox();
        setHBoxStyle(hBox , true);

        Label label = new Label(mgTxt.getText());
        label.setStyle(
                " -fx-alignment: center-left;" +
                        " -fx-background-color:  #3a86ff;" +
                        " -fx-background-radius:15px;" +
                        " -fx-font-size: 18px;" +
                        " -fx-text-fill: #ffffff;" +
                        " -fx-wrap-text: true;" +
                        " -fx-content-display: left;" +
                        " -fx-max-width: 350px;" +
                        " -fx-padding: 10px;"
        );

        hBox.getChildren().add(label);
        vBox.getChildren().add(hBox);


        mgTxt.clear();


    }

    public void displayMg(String message) {
        HBox hBox = new HBox();
        setHBoxStyle(hBox , false);

        Label label = new Label(message);
        label.setStyle(
                " -fx-alignment: center-left;" +
                        " -fx-background-color:  #cfdee7;" +
                        " -fx-background-radius:15px;" +
                        " -fx-font-size: 18px;" +
                        " -fx-text-fill: #000000;" +
                        " -fx-wrap-text: true;" +
                        " -fx-content-display: left;" +
                        " -fx-max-width: 350px;" +
                        " -fx-padding: 10px;"
        );
        hBox.getChildren().add(label);

        vBox.getChildren().add(hBox);

    }

    public void displayImage(String sender , byte[] bytesAr) {
        HBox hBox = new HBox();
        setHBoxStyle(hBox , false);

        Label label = new Label(sender+": ");
        label.setStyle(
                " -fx-alignment: center-left;" +
                        " -fx-background-color:  #cfdee7;" +
                        " -fx-background-radius:15px;" +
                        " -fx-font-size: 18px;" +
                        " -fx-text-fill: #000000;" +
                        " -fx-wrap-text: true;" +
                        " -fx-content-display: left;" +
                        " -fx-max-width: 350px;" +
                        " -fx-padding: 10px;"
        );
        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytesAr)));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        hBox.getChildren().addAll(label, imageView);
        vBox.getChildren().add(hBox);

    }

    public void setClient(Client client) {
        this.client = client;

    }

    public void setHBoxStyle(HBox hBox , boolean isRightSide){
        if (isRightSide){
            hBox.setStyle(
                    "-fx-alignment: center-right;" +
                            " -fx-fill-height: true;" +
                            " -fx-min-height: 50;" +
                            " -fx-pref-width: 520;" +
                            " -fx-max-width: 520;" +
                            " -fx-padding: 10"
            );
        }else {
            hBox.setStyle(
                    "-fx-alignment: center-left;" +
                            " -fx-fill-height: true;" +
                            " -fx-min-height: 50px;" +
                            " -fx-pref-width: 520px;" +
                            " -fx-max-width: 520px;" +
                            " -fx-padding: 10px"
            );
        }

    }

    @FXML
    public void photoSendImgViewOnMouseClicked(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                FileInputStream in = new FileInputStream(selectedFile);

                ImageView imageView = new ImageView(new Image(in));
                imageView.setFitHeight(200);
                imageView.setFitWidth(200);

                HBox hBox = new HBox();
                setHBoxStyle(hBox , true);

                //Ask user need to send image to the Chat Room
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you need to send this " + selectedFile.getName() + " image to chat room ?", ButtonType.OK, ButtonType.NO);
                alert.setTitle("Send Image");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    System.out.println("send image to Chat Room");

                    byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                    client.sendImage(bytes);
                    hBox.getChildren().add(imageView);
                    vBox.getChildren().add(hBox);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void createEmojiBar() {
        int btnIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 5; column++) {
                if (btnIndex < emojis.length) {
                    JFXButton button = new JFXButton(emojis[btnIndex]);
                    button.setStyle("-fx-background-radius:20px;-fx-text-alignment: center; -fx-background-color: #b7fffc;");
                    button.setAlignment(Pos.CENTER);
                    emojiBarGrid.add(button, column, row);
                    button.setOnAction(event -> {
                        mgTxt.appendText(button.getText());
                    });
                    btnIndex++;
                }
            }
        }
    }

    public void emojiImageViewOnMouseClicked(MouseEvent mouseEvent) {
        emojiBarPane.setVisible(!emojiBarPane.isVisible());

    }

}

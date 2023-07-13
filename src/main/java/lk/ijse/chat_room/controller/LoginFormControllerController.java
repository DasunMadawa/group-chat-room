package lk.ijse.chat_room.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import lk.ijse.chat_room.bo.BOFactory;
import lk.ijse.chat_room.bo.custom.UserBO;
import lk.ijse.chat_room.dto.UserDTO;
import lk.ijse.chat_room.service.Client;

public class LoginFormControllerController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField passwordTxt;

    @FXML
    private JFXTextField userNameTxt;

    @FXML
    private JFXButton loginBtn;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void loginBtnOnAction(ActionEvent event) {
        boolean isOk = userBO.checkUser(new UserDTO(userNameTxt.getText(), passwordTxt.getText()));

        if (isOk) {

            new Client("localhost", 3005, userNameTxt.getText());
            userNameTxt.clear();
            passwordTxt.clear();

        }else {
            new Alert(Alert.AlertType.ERROR , "Incorrect UserName Or Password !" , ButtonType.OK).show();
        }

    }

    @FXML
    public void userNameTxtOnAction(ActionEvent event) {
        loginBtnOnAction(event);
    }

    @FXML
    public void passwordTxtOnAction(ActionEvent actionEvent) {
        loginBtnOnAction(actionEvent);
    }

}

package Application;

import Management.Word;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class InfoController {
    Controller parent = new Controller();

    @FXML
    JFXButton confirmButton;

    @FXML
    TextField wordTextField;

    @FXML
    TextArea detailTextArea;

    @FXML
    JFXTextArea alert;

    private Word word;

    public void getOption(MouseEvent e) throws SQLException, ClassNotFoundException {
        Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
        s.close();
    }

    public void display() {
        String detail = "Bài tập từ điển của nhóm 7\n Bao gồm các thành viên:\n Dương Đức Duy" +
                "\n Lê Tuấn Anh\n Trần Ngọc Minh\n Nguyễn Minh Đức";
        detailTextArea.setText(detail);
    }
}

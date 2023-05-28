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

public class FixController {

    Controller parent = new Controller();

    @FXML
    JFXButton confirmButton;

    @FXML
    JFXButton cancleButton;

    @FXML
    TextField wordTextField;

    @FXML
    TextArea detailTextArea;

    @FXML
    JFXTextArea alert;

    private Word word;

    public void getOption(MouseEvent e) throws SQLException, ClassNotFoundException {
        Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
        if (e.getTarget() == confirmButton) {
            if (wordTextField != null && detailTextArea != null) {
                parent.fixWord(word, wordTextField.getText(), detailTextArea.getText());
                s.close();
            } else alert.setText("Cant leave blank space");
        }
        if (e.getTarget() == cancleButton) {
            s.close();
        }
    }

    public void display(Word selectedWord) {
        wordTextField.setText(selectedWord.getWord_target());
        detailTextArea.setText(selectedWord.getWord_explain());
    }

    public void setWord(Word word) {
        this.word = word;
    }
}

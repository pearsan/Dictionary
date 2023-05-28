package Application;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RemoveController {

    @FXML
    JFXButton YES;

    @FXML
    JFXButton NO;

    Controller parent = new Controller();

    public void getOption(MouseEvent e) throws SQLException, ClassNotFoundException {
        Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
        if (e.getTarget() == YES) {
            parent.removeWord();
            s.close();
        }
        if (e.getTarget() == NO) {
            s.close();
        }
    }

}

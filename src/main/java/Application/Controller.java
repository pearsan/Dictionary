package Application;

import DatabaseManagament.QueryingData;
import Management.DictionaryManagement;
import Management.Translator;
import Management.Word;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Translator translator = new Translator();
    DictionaryManagement NewDictionary = new DictionaryManagement();
    QueryingData newQueryingData = new QueryingData();
    ObservableList<Word> wordList = FXCollections.observableArrayList(); //list tu
    private static boolean modeTrans = false;
    private Word selectedWord;
    @FXML
    private TextField SearchBar;

    @FXML
    private ListView<Word> listView;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private TextArea WordTitle;

    @FXML
    ImageView close = new ImageView();

    @FXML
    ImageView minimize = new ImageView();

    @FXML
    ImageView maximize = new ImageView();

    @FXML
    private Button lookupButton;

    @FXML
    private JFXButton speaker;
    @FXML
    private JFXButton translateButton;
    @FXML
    private JFXButton remove;
    @FXML
    private JFXButton fix;
    @FXML
    private JFXButton addWord;
    @FXML
    private JFXButton infoButton;
    @FXML
    private AnchorPane lookup;
    @FXML
    private TextArea textToTrans;
    @FXML
    private AnchorPane transPane;
    @FXML
    private JFXToggleButton toggle;
    @FXML
    private TextArea transText;


    @FXML
    public void handleLookupButton(MouseEvent e) {
        lookup.setVisible(true);
        transPane.setVisible(false);
        SearchBar.clear();
        contentTextArea.clear();
        WordTitle.clear();
        lookupButton.getStylesheets().clear();
        translateButton.getStylesheets().clear();
        String css = this.getClass().getResource("LookupSelected.css").toExternalForm();
        lookupButton.getStylesheets().add(css);
        translateButton.getStylesheets().add(css);
    }

    public void handleTranslateButton(MouseEvent e) {
        lookup.setVisible(false);
        transPane.setVisible(true);
        textToTrans.clear();
        transText.clear();
        lookupButton.getStylesheets().clear();
        String css = this.getClass().getResource("TranslateSelected.css").toExternalForm();
        lookupButton.getStylesheets().clear();
        translateButton.getStylesheets().clear();
        lookupButton.getStylesheets().add(css);
        translateButton.getStylesheets().add(css);
    }

    public void translateAction(MouseEvent e) throws IOException {
        if (textToTrans != null) {
            if (modeTrans) {
                String translated = translator.translate("vi", "en", textToTrans.getText());
                transText.setText(translated);
            } else {
                String translated = translator.translate("en", "vi", textToTrans.getText());
                transText.setText(translated);
            }
        }
    }

    public void closeHandle(MouseEvent e) {
        Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
        s.close();
    }

    public void minHandle(MouseEvent e) {
        Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    public void maxHandle(MouseEvent e) {
        Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
        if (s.isFullScreen()) {
            s.setFullScreen(false);
        } else
        s.setFullScreen(true);
    }

    //xoa tu
    public void removeWord() throws SQLException, ClassNotFoundException {
        newQueryingData.removeInDB(selectedWord.getWord_target());
        wordList.remove(selectedWord);
        WordTitle.clear();
        contentTextArea.clear();
    }

    public void handleRemoveButton(ActionEvent e) throws IOException {
        if (selectedWord != null) {
            final double[] xOffset = {0};
            final double[] yOffset = {0};
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmRemove.fxml"));
            root = loader.load();
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset[0] = mouseEvent.getSceneX();
                    yOffset[0] = mouseEvent.getSceneY();
                }
            });

            RemoveController removeController = loader.getController();
            removeController.parent = this;
            Stage window = new Stage();
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    window.setX(mouseEvent.getScreenX() - xOffset[0]);
                    window.setY(mouseEvent.getScreenY() - yOffset[0]);
                }
            });
            scene = new Scene(root);
            window.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            window.setScene(scene);
            window.show();
        }
    }

    //sua tu
    public void handleFixButton(ActionEvent e) throws IOException {
        if (selectedWord != null) {
            final double[] xOffset = {0};
            final double[] yOffset = {0};
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmFix.fxml"));
            root = loader.load();
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset[0] = mouseEvent.getSceneX();
                    yOffset[0] = mouseEvent.getSceneY();
                }
            });

            FixController fixController = loader.getController();
            fixController.parent = this;
            fixController.display(selectedWord);
            fixController.setWord(selectedWord);
            Stage window = new Stage();
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    window.setX(mouseEvent.getScreenX() - xOffset[0]);
                    window.setY(mouseEvent.getScreenY() - yOffset[0]);
                }
            });
            scene = new Scene(root);
            window.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            window.setScene(scene);
            window.show();
        }
    }

    public void fixWord(Word word, String newWord, String newDetail) throws SQLException, ClassNotFoundException {
        //System.out.println(newDetail);
        // sua trong database
        newQueryingData.changeWord(selectedWord.getWord_target(), newWord);
        newQueryingData.changeDetail(selectedWord.getWord_explain(), newDetail);

        //sua trong app
        word.setWord_explain(newDetail);
        word.setWord_target(newWord);
        contentTextArea.setText(newDetail);
        SearchBar.setText(newWord);
    }

    //nút phát âm
    public void handlePronounButton() {
        if (selectedWord!=null) {
            String word = selectedWord.getWord_target();
            if (!word.equals("")) {
                NewDictionary.D_voice(word);
            }
        }
    }

    //chọn từ
    public void listViewSelectedWord() {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            contentTextArea.clear();
            selectedWord = listView.getSelectionModel().getSelectedItem();
            updateTextAre();
        }
    }

    public void handleAddButton(ActionEvent e) throws IOException {
            final double[] xOffset = {0};
            final double[] yOffset = {0};
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmAdd.fxml"));
            root = loader.load();
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset[0] = mouseEvent.getSceneX();
                    yOffset[0] = mouseEvent.getSceneY();
                }
            });

            AddController addController = loader.getController();
            addController.parent = this;
            Stage window = new Stage();
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    window.setX(mouseEvent.getScreenX() - xOffset[0]);
                    window.setY(mouseEvent.getScreenY() - yOffset[0]);
                }
            });
            scene = new Scene(root);
            window.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            window.setScene(scene);
            window.show();

    }

    public void addW(String word, String detail) throws SQLException, ClassNotFoundException {
        Word newWord = new Word(word, detail);
        int index = 0;
        for (Word i : wordList) {
            if (i.getWord_target().charAt(0) > word.charAt(0)) {
                break;
            }
            index++;
        }
        wordList.add(index, newWord);
        newQueryingData.insertIntoDB(word, detail);
    }

    public void handleInfoButton(ActionEvent e) throws IOException {

        final double[] xOffset = {0};
        final double[] yOffset = {0};
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoGroup.fxml"));
        root = loader.load();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset[0] = mouseEvent.getSceneX();
                yOffset[0] = mouseEvent.getSceneY();
            }
        });

        InfoController infoController = loader.getController();
        infoController.parent = this;
        infoController.display();
        Stage window = new Stage();
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                window.setX(mouseEvent.getScreenX() - xOffset[0]);
                window.setY(mouseEvent.getScreenY() - yOffset[0]);
            }
        });
        scene = new Scene(root);
        window.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.show();

    }

    //hien nghia cua tu
    private void updateTextAre() {
        contentTextArea.setText(selectedWord.getWord_explain());
        WordTitle.setText(selectedWord.getWord_target());
        SearchBar.setText(selectedWord.getWord_target());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            newQueryingData.pushAllWord(wordList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        FilteredList<Word> filteredData = new FilteredList<>(wordList, b -> true);

        //filter list
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(word -> {
                //if empty return list
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //so sanh xau nhap vao
                String lowerCaseFilter = newValue.toLowerCase();
                //tra ve tu bat dau tu index = 0
                if (word.getWord_target().toLowerCase().startsWith(lowerCaseFilter,0)) {
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Word> sortedData = new SortedList<>(filteredData);

        //sortedData.comparatorProperty().bind(listView.cellFactoryProperty());

        listView.setItems(sortedData);

        //dinh dang list view
        listView.setCellFactory(new Callback<ListView<Word>, ListCell<Word>>() {
            @Override
            public ListCell<Word> call(ListView<Word> wordListView) {
                final ListCell<Word> word = new ListCell<Word>() {
                    @Override
                    public void updateItem(Word item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getWord_target());
                        }
                        else setText("");
                    }
                };
                return word;
            }
        });

        //button change translator mode
        toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (toggle.isSelected() == true) {
                    toggle.setText("Vietnamese to English");
                    textToTrans.clear();
                    transText.clear();
                    modeTrans = true;
                } else {
                    toggle.setText("English to Vietnamese");
                    modeTrans = false;
                    textToTrans.clear();
                    transText.clear();
                }
            }
        });
    }


}
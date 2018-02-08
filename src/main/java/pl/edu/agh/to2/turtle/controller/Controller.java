package pl.edu.agh.to2.turtle.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import pl.edu.agh.to2.turtle.controller.commands.Command;
import pl.edu.agh.to2.turtle.model.Tortoise;
import pl.edu.agh.to2.turtle.view.Drawer;

import java.io.File;
import java.util.List;

public class Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextArea commandsField;
    @FXML
    private TextField consoleField;
    @FXML
    private Pane mapPane;
    @FXML
    private ImageView tortoiseIcon;
    private Drawer drawer;
    private Tortoise tortoise;

    private CommandHistory commandsHistory;
    private FileChooser fileChooser;

    private Parser parser;
    private Deserializer deserializer;


    public void initialize() {
        setStyles();
        tortoise = new Tortoise();
        initializeDrawer();
        tortoise.addObserver(drawer);
        tortoise.setCoordinates(drawer.getMinCoordinate(), drawer.getMinCoordinate(),
                drawer.getMaxXCoordinate(), drawer.getMaxYCoordinate());
        drawer.resetField();
        tortoiseIcon.setImage(drawer.getCurrentIcon());

        commandsHistory = new CommandHistory();
        fileChooser = new FileChooser();
        parser = new Parser();
        deserializer = new Deserializer();
    }


    private void setStyles() {
        borderPane.setStyle("-fx-background-color: dimgrey;");
    }

    private void initializeDrawer() {
        Canvas lineCanvas = new Canvas(mapPane.getPrefWidth(), mapPane.getPrefHeight());
        Canvas tortoiseCanvas = new Canvas(mapPane.getPrefWidth(), mapPane.getPrefHeight());

        mapPane.getChildren().add(lineCanvas);
        mapPane.getChildren().add(tortoiseCanvas);

        drawer = new Drawer(tortoise, lineCanvas, tortoiseCanvas);
    }

    public void onCommandEntered(ActionEvent actionEvent) {
        String commandString = consoleField.getText();
        parse(commandString);

    }

    @FXML
    private void handleUndoAction(ActionEvent event) {
        List<Command> commands = commandsHistory.undo();
        if (commands != null) {
            writeCommands(Mode.UNDO, commands);
            drawer.resetField();
            commandsHistory.rerun();
        }
    }

    private void writeCommands(Mode mode, List<Command> commands) {
        switch (mode) {
            case EXECUTE:
                commandsField.appendText(commands + "\n");
                break;
            case ADD_PROCEDURE:
                commandsField.appendText("Added procedure\n");
                break;
            case UNDO:
                commandsField.appendText("Undo: " + commands + "\n");
                break;
            case REDO:
                commandsField.appendText("Redo: " + commands + "\n");
                break;
            case INVALID:
                commandsField.appendText("Invalid expression\n");
            default:
                break;
        }
    }

    public void handleRedoAction(ActionEvent actionEvent) {
        List<Command> commands = commandsHistory.redo();

        if (commands != null) {
            writeCommands(Mode.UNDO, commands);
            drawer.resetField();
            commandsHistory.rerun();
        }

    }

    public void handleOpenAction(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
        if (file != null) {
            deserializer.deserialize(file).forEach(command -> parse((String) command));
        }

    }

    public void handlePreviousIconAction(ActionEvent actionEvent) {
        drawer.previousIcon();
        tortoiseIcon.setImage(drawer.getCurrentIcon());
    }

    public void handleNextIconAction(ActionEvent actionEvent) {
        drawer.nextIcon();
        tortoiseIcon.setImage(drawer.getCurrentIcon());
    }

    private void parse(String line) {
        List<Command> commands = parser.makeGroupCommand(line, tortoise);
        if (!commands.isEmpty()) {
            setClearCommandStyle();
            parser.getAndResetIfSetProcedure();

            commands.forEach(Command::execute);
            writeCommands(Mode.EXECUTE, commands);
            commandsHistory.addExecutedCommands(commands);

        } else if (parser.getAndResetIfSetProcedure()) {
            setClearCommandStyle();
            writeCommands(Mode.ADD_PROCEDURE, commands);
        } else {
            parser.getAndResetIfSetProcedure();
            setWrongCommandStyle();
            writeCommands(Mode.INVALID, commands);
        }
    }

    public void resetConsoleField(MouseEvent mouseEvent) {
        setClearCommandStyle();
    }

    private void setWrongCommandStyle() {
        consoleField.setStyle("-fx-border-color: red;");
    }

    private void setClearCommandStyle() {
        consoleField.setStyle("-fx-border-color: transparent;");
        consoleField.clear();
    }


}

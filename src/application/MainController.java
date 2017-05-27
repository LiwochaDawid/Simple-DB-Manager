package application;

import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class MainController {
	private CommandsInterpreter commandsInterpreter = new CommandsInterpreter();
	private DataKeeper dataKeeper = new DataKeeper();
	@FXML
	private TextArea messagesLabel;
	@FXML
	private Label dataBaseNameLabel;
	@FXML
	private TextArea commandLine;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab messages;
	@FXML
	private Tab selectResult;
	@FXML
	private TableView<ObservableList<String>> table = new TableView<>();
	@FXML
	private CheckMenuItem font14;
	@FXML
	private CheckMenuItem font16;
	@FXML
	private CheckMenuItem font18;
	int i;
	
	public void executeCommand(ActionEvent execute) {
		clearMessagesLabel();
		clearTable();
		executeCommand();
		updateTable();
		updateDataBaseNameLabel();
		updateMessagesLabel();
	}
	
	public void saveQuery(ActionEvent saveQuery) {
		dataKeeper.updateOpenedQueryContent(commandLine.getText());
		try {
			dataKeeper = new QueryFile().saveQuery(dataKeeper);
			messagesLabel.setText("Query saving success!");
		} catch (DBException e) {	
			messagesLabel.setText(e.getMessage());
		}
	}
	
	public void loadQuery(ActionEvent loadQuery) {
		try {
			dataKeeper = new QueryFile().loadQuery(dataKeeper);
			updateOpenedQueryContent();
			messagesLabel.setText("Query loading success!");
		} catch (DBException e) {
			messagesLabel.setText(e.getMessage());
		}
	}
	
	public void saveDataBase(ActionEvent saveDataBase) {
		tabPane.getSelectionModel().select(messages);
		try {
			dataKeeper = new DataBaseFile().saveDataBase(dataKeeper);
			messagesLabel.setText("Database saving success!");
		} catch (DBException e) {	
			messagesLabel.setText(e.getMessage());
		}
	}
	
	public void loadDataBase(ActionEvent loadDataBase) {
		tabPane.getSelectionModel().select(messages);
		try {
			dataKeeper = new DataBaseFile().loadDataBase(dataKeeper);
			messagesLabel.setText("Database loading success!");
			updateDataBaseNameLabel();
		} catch (DBException e) {
			messagesLabel.setText(e.getMessage());
		}
	}
	
	public void setFontSize14(ActionEvent setFontSize14) {
		font16.setSelected(false);
		font18.setSelected(false);
		commandLine.setStyle("-fx-font: 14 system;");
		messagesLabel.setStyle("-fx-font: 14 system;");
	}
	
	public void setFontSize16(ActionEvent setFontSize16) {
		font14.setSelected(false);
		font18.setSelected(false);
		commandLine.setStyle("-fx-font: 16 system;");
		messagesLabel.setStyle("-fx-font: 16 system;");
	}
	
	public void setFontSize18(ActionEvent setFontSize18) {
		font14.setSelected(false);
		font16.setSelected(false);
		commandLine.setStyle("-fx-font: 18 system;");
		messagesLabel.setStyle("-fx-font: 18 system;");
	}
	
	private void updateDataBaseNameLabel() {
		dataBaseNameLabel.setText(dataKeeper.getDataBaseNameLabelText());
	}
	
	private void updateOpenedQueryContent() {
		commandLine.setText(dataKeeper.getOpenedQueryContent());
	}
	
	private void updateMessagesLabel() {
		messagesLabel.setText(dataKeeper.getMessagesLabelText());
	}
	
	private void updateTable() {
		List<String> tableColumns = dataKeeper.getTableColumns();
		String[][] tableData = dataKeeper.getTableData();
		if (tableColumns == null || tableData == null)
			tabPane.getSelectionModel().select(messages);
		else {
			tabPane.getSelectionModel().select(selectResult);
			new PrintTableView(table, tableColumns, tableData);
			dataKeeper.clearTableData();
			dataKeeper.clearTableColumns();
		}
	}
	
	private void clearTable() {
		table.getItems().clear();
		table.getColumns().clear();
	}
	
	private void clearMessagesLabel() {
		dataKeeper.clearMessagesLabelText();
		messagesLabel.setText("");
	}
	
	private void executeCommand() {
		List<String> commands = getCommands();
			try {
				for (i=0; i<commands.size(); i++) {
					dataKeeper = commandsInterpreter.executeCommand(dataKeeper, commands.get(i));
				}
			} catch (DBException e) {
			dataKeeper.updateMessagesLabelText(e.getErrorMessage());
			dataKeeper.clearTableColumns();
			dataKeeper.clearTableData();
			}
	}
	
	private List<String> getCommands() {
		List<String> commands;
		if (commandLine.getSelectedText().isEmpty()) {
			commands = Arrays.asList(commandLine.getText().replaceAll("--.*\n", "").replaceAll(";\\s+", ";").split(";"));
		}
		else
			commands = Arrays.asList(commandLine.getSelectedText().replaceAll("--.*\n", "").replaceAll(";\\s+", ";").split(";"));
		return formatCommands(commands);
	}
	
	private List<String> formatCommands(List<String> commands) {
			for (int i=0; i<commands.size(); i++) {
				commands.set(i, commands.get(i).replaceAll("\\s+", " "));
				commands.set(i, commands.get(i).replaceAll("^ ", ""));
				commands.set(i, commands.get(i).replaceAll(" $", ""));
				commands.set(i, commands.get(i).replaceAll(", ", ","));
				commands.set(i, commands.get(i).replaceAll(" ,", ","));
				commands.set(i, commands.get(i).replaceAll(" \\(", "("));
				commands.set(i, commands.get(i).replaceAll("\\( ", "("));
				commands.set(i, commands.get(i).replaceAll(" \\)", ")"));
				commands.set(i, commands.get(i).replaceAll("\\) ", ")"));
			}
		return commands;
	}
}


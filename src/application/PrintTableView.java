package application;

import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

class PrintTableView {
	private List<String> columns;
	private String[][] data;
	
	
	PrintTableView(TableView<ObservableList<String>> table, List<String> columns, String[][] data) {
		this.columns = columns;
		this.data = data;
		addColumns(table);
		addData(table);
	}
	
	void addColumns(TableView<ObservableList<String>> table) {
		for (int i = 0; i < columns.size(); i++) {
            final int index = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columns.get(i));
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(index)));
            table.getColumns().add(column);
        }
	}
	
	void addData(TableView<ObservableList<String>> table) {
		for (int i = 0; i < data.length; i++) 
            table.getItems().add(FXCollections.observableArrayList(data[i]));
	}   
}

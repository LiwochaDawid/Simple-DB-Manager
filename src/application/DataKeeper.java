package application;

import java.util.ArrayList;
import java.util.List;

class DataKeeper {
	private DataBase dataBase;
	private List<String> messagesLabelTexts = new ArrayList<>();
	private String dataBaseNameLabelText = "Create/Load Database first!";
	private String openedQueryContent;
	private List<String> tableColumns = new ArrayList<>();
	private String[][] tableData;
	
	boolean isDataBaseExist(String name) {
		if (dataBase.getName().equals(name))
			return true;
		else
			return false;				
	}
	
	void deleteDataBase(String name) {
		if (dataBase.getName().equals(name))
			dataBase = null;
	}
	
	List<String> getTableColumns() {
		return tableColumns;
	}
	
	String[][] getTableData() {
		return tableData;
	}
	
	String getDataBaseNameLabelText() {
		return dataBaseNameLabelText;
	}
	
	String getMessagesLabelText() {
		String messagesLabelText="";
		int messageIndex;
		for (int i=0; i<messagesLabelTexts.size(); i++) {
			messageIndex = i+1; 
			messagesLabelText += "["+messageIndex+"] "+ messagesLabelTexts.get(i) + "\n" ;
		}
		return messagesLabelText;
	}
	
	String getOpenedQueryContent() {
		return openedQueryContent;
	}
	
	DataBase getDataBase() throws DBException{
		return dataBase;
	}
	
	void updateDataBase(DataBase dataBase) {
		this.dataBase = dataBase;
	}
	
	void updateTableColumns(List<String> tableColumns) {
		this.tableColumns = tableColumns;
	}
	
	void updateTableData(String[][] tableData) {
		this.tableData = tableData;
	}
	
	void updateMessagesLabelText(String messagesLabelText) {
		messagesLabelTexts.add(messagesLabelText);
	}
	
	void updateDataBaseNameLabelText(String dataBaseNameLabelText) {
		this.dataBaseNameLabelText = dataBaseNameLabelText;
	}
	
	void updateOpenedQueryContent(String openedQueryContent) {
		this.openedQueryContent = openedQueryContent;
	}
	
	void clearTableColumns() {
		tableColumns = null;
	}
	
	void clearTableData() {
		tableData = null;
	}
	
	void clearMessagesLabelText() {
		messagesLabelTexts.clear();
	}
}

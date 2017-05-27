package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CreateTable {
	private DataBase dataBase;
	private Table table;
    private List<String> columnsNames = new ArrayList<>();
    private String regex = "^create\\s+table\\s+(.*)\\((.*)\\)$";
    private Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private Matcher matcher;
    
	CreateTable (DataBase dataBase, String input) throws DBException{
		this.dataBase = dataBase;
		matcher = pattern.matcher(input);
		if (matcher.find()) {
			if (this.dataBase.isTableExist(matcher.group(1)))
				throw new DBException("Table ["+matcher.group(1)+"] already exists!");
			else {
				columnsNames = Arrays.asList(matcher.group(2).split(","));
				createColumns();
				this.dataBase.newTable(table);
			}
		}
		else {
			throw new DBException("Incorrect create table syntax!");
		}
	}
	
	private void createColumns() throws DBException{
		table = new Table(matcher.group(1));
		table.addColumn(columnsNames);
	}

	
    DataBase getDataBase() {
    	return dataBase;
    }
	
}
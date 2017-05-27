package application;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SelectCommand implements Command {
	private String[] regex = {
		"^select \\* from (.*) where (.*) (.*) (.*)$",
		"^select (.*) from (.*) where (.*) (.*) (.*)$",
		"^select \\* from (.*)$",
		"^select (.*) from (.*)$"
	};
    private Pattern[] pattern = {
    		Pattern.compile(regex[0], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
    		Pattern.compile(regex[1], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
    		Pattern.compile(regex[2], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
    		Pattern.compile(regex[3], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
    };
    private Matcher matcher;
	
    @Override
    public DataKeeper executeCommand(DataKeeper dataKeeper, String input) throws DBException {
		if (dataKeeper.getDataBase() == null)
			throw new DBException("Database does not exist!");
    	matcher = pattern[0].matcher(input);
    	if (matcher.find()) 
    		return showAllPredicated(dataKeeper, input);
    	matcher = pattern[1].matcher(input);
    	if (matcher.find()) 
    		return showPredicated(dataKeeper, input);
    	matcher = pattern[2].matcher(input);
    	if (matcher.find()) 
    		return showAll(dataKeeper, input);
    	matcher = pattern[3].matcher(input);
    	if (matcher.find()) 
    		return show(dataKeeper, input);
    	throw new DBException("Incorrect select syntax");
    }
    
    private DataKeeper showAll(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
			throw new DBException("Table ["+matcher.group(1)+"] does not exists!");
		else {
			List<String> columns = dataKeeper.getDataBase().getTable(matcher.group(1)).getColumnsNames();
			String[][] records = dataKeeper.getDataBase().getTable(matcher.group(1)).getRecords();
			dataKeeper.updateTableColumns(columns);
			dataKeeper.updateTableData(records);
		}
		return dataKeeper;
    }
    
    private DataKeeper showAllPredicated(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
			throw new DBException("Table ["+matcher.group(1)+"] does not exists!");
		else {
			List<String> columns = dataKeeper.getDataBase().getTable(matcher.group(1)).getColumnsNames();
			String[][] records = dataKeeper.getDataBase().getTable(matcher.group(1)).getRecords(matcher.group(2), matcher.group(3), matcher.group(4));
			dataKeeper.updateTableColumns(columns);
			dataKeeper.updateTableData(records);
		}
		return dataKeeper;
    }
    
    private DataKeeper show(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(2)))
			throw new DBException("Table ["+matcher.group(2)+"] does not exists!");
		else {
			List<String> columns = Arrays.asList(matcher.group(1).split(","));
			String[][] records = dataKeeper.getDataBase().getTable(matcher.group(2)).getRecords(columns);
			dataKeeper.updateTableColumns(columns);
			dataKeeper.updateTableData(records);
		}
		return dataKeeper;
    }
    
    private DataKeeper showPredicated(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(2)))
			throw new DBException("Table ["+matcher.group(2)+"] does not exists!");
		else {
			List<String> columns = Arrays.asList(matcher.group(1).split(","));
			String[][] records = dataKeeper.getDataBase().getTable(matcher.group(2)).getRecords(columns, matcher.group(3), matcher.group(4), matcher.group(5));
			dataKeeper.updateTableColumns(columns);
			dataKeeper.updateTableData(records);
		}
		return dataKeeper;
    }
    
    
}

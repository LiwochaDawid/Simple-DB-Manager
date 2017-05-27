package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DeleteCommand implements Command {
	private String[] regex = {
		"^delete from (.*) where (.*) (.*) (.*)$",
		"^delete from (.*)$",
		"^delete table (.*)$",
		"^delete database (.*)$"
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
    		return deleteAllPredicated(dataKeeper, input);
    	matcher = pattern[1].matcher(input);
    	if (matcher.find()) 
    		return deleteAll(dataKeeper, input);
    	matcher = pattern[2].matcher(input);
    	if (matcher.find()) 
    		return deleteTable(dataKeeper, input);
    	matcher = pattern[3].matcher(input);
    	if (matcher.find()) 
    		return deleteDataBase(dataKeeper, input);
    	throw new DBException("Incorrect delete syntax");
    }
    
    private DataKeeper deleteAll(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
			throw new DBException("Table ("+matcher.group(1)+") does not exist!");
		else
			dataKeeper.getDataBase().getTable(matcher.group(1)).deleteRecords(dataKeeper);
		return dataKeeper;
    }
    
    private DataKeeper deleteAllPredicated(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
			throw new DBException("Table ("+matcher.group(1)+") does not exist!");
		else
			dataKeeper.getDataBase().getTable(matcher.group(1)).deleteRecords(dataKeeper, matcher.group(2), matcher.group(3), matcher.group(4));
		return dataKeeper;
    }
    
    private DataKeeper deleteTable(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
			throw new DBException("Table ("+matcher.group(1)+") does not exist!");
		else {
			dataKeeper.getDataBase().deleteTable(matcher.group(1));
			dataKeeper.updateMessagesLabelText("Table ("+matcher.group(1)+") succesfully deleted!");
		}
		return dataKeeper;
    }
    
    private DataKeeper deleteDataBase(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.isDataBaseExist(matcher.group(1)))
			throw new DBException("Table ("+matcher.group(1)+") does not exist!");
		else {
			dataKeeper.deleteDataBase(matcher.group(1));
			dataKeeper.updateMessagesLabelText("Database ("+matcher.group(1)+") succesfully deleted!");
		}
		return dataKeeper;
    }
    
}

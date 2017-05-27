package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CreateCommand implements Command {
	
	private String[] regex = {
			"^create database .*$",
			"^create table .*$"
		};
	private Pattern[] pattern = {
	    	Pattern.compile(regex[0], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
	    	Pattern.compile(regex[1], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
	    };
    private Matcher matcher;
    
    @Override
    public DataKeeper executeCommand (DataKeeper dataKeeper, String input) throws DBException {
    	matcher = pattern[0].matcher(input);
    	if (matcher.find()) {
    		if (dataKeeper.getDataBase() != null)
    			throw new DBException("Database already exist! You can have only one Database!");
    		else {
    			dataKeeper.updateDataBase(new CreateDataBase(dataKeeper.getDataBase(), input).getDataBase());
    			dataKeeper.updateMessagesLabelText("Database ("+dataKeeper.getDataBase().getName()+") succesfully created.");
    			dataKeeper.updateDataBaseNameLabelText("DB Name: "+dataKeeper.getDataBase().getName());
    			return dataKeeper;
    		}
		}
    	matcher = pattern[1].matcher(input);
    	if (matcher.find()) {	
			if (dataKeeper.getDataBase() == null)
				throw new DBException("Database does not exist!");
			else {
				dataKeeper.updateDataBase(new CreateTable(dataKeeper.getDataBase(), input).getDataBase());	
				dataKeeper.updateMessagesLabelText("Table ("+dataKeeper.getDataBase().getLastTableName()+") succesfully created.");
				return dataKeeper;
			}
		}
		throw new DBException("Incorrect create syntax!");
	}
}

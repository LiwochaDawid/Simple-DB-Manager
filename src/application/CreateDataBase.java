package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CreateDataBase {
	private DataBase dataBase;
    private String regex = "^create\\s+database\\s+(.*)$";
    private Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private Matcher matcher;
    
    CreateDataBase (DataBase dataBase, String input) throws DBException{
    	this.dataBase = dataBase;
    	matcher = pattern.matcher(input);
    	if (matcher.find())
    		this.dataBase = new DataBase(matcher.group(1));
    	else
    		throw new DBException ("Incorrect create database syntax!");
    }  
    
    DataBase getDataBase() {
    	return dataBase;
    }
    
}

package application;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InsertCommand implements Command{
    private String[] regex = {
    		"^insert\\s+into\\s+(.*)\\((.*)\\)values\\((.*)\\)$",
    		"^insert\\s+into\\s+(.*)\\s+values\\((.*)\\)$"
    };
    private Pattern[] pattern = {
    		Pattern.compile(regex[0], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
    		Pattern.compile(regex[1], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
    };
    private Matcher matcher;
	
    @Override
    public DataKeeper executeCommand(DataKeeper dataKeeper, String input) throws DBException {
		if (dataKeeper.getDataBase() == null)
			throw new DBException("Database does not exist!");
    	matcher = pattern[0].matcher(input);
    	if (matcher.find())
    		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
    			throw new DBException("Table ["+matcher.group(1)+"] does not exists!");
    		else {
    			List<String> columnNames = Arrays.asList(matcher.group(2).split(","));
    			List<String> values = Arrays.asList(matcher.group(3).split(","));
    			dataKeeper.getDataBase().getTable(matcher.group(1)).addRecord(columnNames, values);
    			dataKeeper.updateMessagesLabelText("1 row(s) added.");
    			return dataKeeper;
    		}
    	matcher = pattern[1].matcher(input);
    	if (matcher.find())
    		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
    			throw new DBException("Table ["+matcher.group(1)+"] does not exists!");
    		else {
    			List<String> values = Arrays.asList(matcher.group(2).split(","));
    			dataKeeper.getDataBase().getTable(matcher.group(1)).addRecord(values);
    			dataKeeper.updateMessagesLabelText("1 row(s) added.");
    			return dataKeeper;
    		}
    	else
    		throw new DBException("Incorrect insert syntax");
    }
}

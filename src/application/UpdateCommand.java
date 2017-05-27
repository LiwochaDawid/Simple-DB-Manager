package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UpdateCommand implements Command {
	private String[] regex = {
		"^update (.*) set (.*) where (.*) (.*) (.*)$",
		"^update (.*) set (.*)$"
	};
    private Pattern[] pattern = {
    		Pattern.compile(regex[0], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
    		Pattern.compile(regex[1], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
    };
    private Matcher matcher;
	
    @Override
    public DataKeeper executeCommand(DataKeeper dataKeeper, String input) throws DBException {
    	matcher = pattern[0].matcher(input);
    	if (matcher.find()) 
    		return updatePredicated(dataKeeper, input);
    	matcher = pattern[1].matcher(input);
    	if (matcher.find()) 
    		return update(dataKeeper, input);
    	throw new DBException("Incorrect DELETE syntax!");
    }
    
    private DataKeeper update(DataKeeper dataKeeper, String input) throws DBException {
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
			throw new DBException("Table ["+matcher.group(1)+"] does not exists!");
		else {
			List<String> toSet = Arrays.asList(matcher.group(2).split(","));
			List<String> columnsNames = new ArrayList<>();
			List<String> values = new ArrayList<>();
			for (int i=0; i<toSet.size(); i++) {
				toSet.set(i, toSet.get(i).replaceAll(" =", "="));
				toSet.set(i, toSet.get(i).replaceAll("= ", "="));
				int indexOfEqualsOperator = toSet.get(i).indexOf("=");
				if (indexOfEqualsOperator == -1)
					throw new DBException ("Missing operator (=) in one or more SET statement!");
				columnsNames.add(toSet.get(i).substring(0, indexOfEqualsOperator));
				values.add(toSet.get(i).substring(indexOfEqualsOperator+1));
			}
			dataKeeper.getDataBase().getTable(matcher.group(1)).updateRecords(dataKeeper, columnsNames, values);
		}
		return dataKeeper;
    }
    
    private DataKeeper updatePredicated(DataKeeper dataKeeper, String input) throws DBException {
		if (dataKeeper.getDataBase() == null)
			throw new DBException("Database does not exist!");
		if (!dataKeeper.getDataBase().isTableExist(matcher.group(1)))
			throw new DBException("Table ["+matcher.group(1)+"] does not exists!");
		else {
			List<String> toSet = Arrays.asList(matcher.group(2).split(","));
			List<String> columnsNames = new ArrayList<>();
			List<String> values = new ArrayList<>();
			for (int i=0; i<toSet.size(); i++) {
				toSet.set(i, toSet.get(i).replaceAll(" =", "="));
				toSet.set(i, toSet.get(i).replaceAll("= ", "="));
				int indexOfEqualsOperator = toSet.get(i).indexOf("=");
				if (indexOfEqualsOperator == -1)
					throw new DBException ("Missing operator (=) in one or more SET statement!");
				columnsNames.add(toSet.get(i).substring(0, indexOfEqualsOperator));
				values.add(toSet.get(i).substring(indexOfEqualsOperator+1));
			}
			dataKeeper.getDataBase().getTable(matcher.group(1)).updateRecords(dataKeeper, columnsNames, values, matcher.group(3), matcher.group(4), matcher.group(5));
		}
		return dataKeeper;
    }
    
}

package application;

import java.util.HashMap;

class CommandsInterpreter {
	
	private HashMap<String, Command> commandsMap= new HashMap<>();
	
	CommandsInterpreter() {
	    commandsMap.put("create", new CreateCommand());
	    commandsMap.put("insert", new InsertCommand());
	    commandsMap.put("select", new SelectCommand());
	    commandsMap.put("update", new UpdateCommand());
	    commandsMap.put("delete", new DeleteCommand());
	}
	
	DataKeeper executeCommand(DataKeeper dataKeeper, String input) throws DBException {
		int indexOfFirstSpace = input.indexOf(" ");
		if (indexOfFirstSpace == -1)
			throw new DBException("Incorrect command!");
		else {
			String firstWord = input.substring(0, indexOfFirstSpace).toLowerCase();
			Command command = commandsMap.get(firstWord);
			if (command == null)
				throw new DBException("Incorrect command!");
			else
				dataKeeper = command.executeCommand(dataKeeper , input);
			return dataKeeper;
		}
	}	
}

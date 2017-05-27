package application;

interface Command {
	DataKeeper executeCommand(DataKeeper dataKeeper, String input) throws DBException;
}

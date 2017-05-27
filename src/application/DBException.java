package application;

@SuppressWarnings("serial")
class DBException extends Exception {
	DBException (String message) {
		super(message);
	}
	
	String getErrorMessage() {
		return "ERROR: "+getMessage();
	}
}

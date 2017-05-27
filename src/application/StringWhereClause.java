package application;

import java.util.ArrayList;
import java.util.List;


public class StringWhereClause {
	
	List<Integer> recordIndexes = new ArrayList<>();
	List<String> leftOperands;
	String rightOperand;
	String operator;

	StringWhereClause(List<String> leftOperands, String operator, String rightOperand) throws DBException {
		this.rightOperand = rightOperand;
		this.operator = operator;
		this.leftOperands = leftOperands;
		findOperator();			
	}

	
	private void findOperator() throws DBException{
		switch (operator.toLowerCase()) {
		case "like":
			isLike();
			break;
		default:
			throw new DBException("Invalid operator ("+operator+")!");
		}
	}
	
	List<Integer> getResult() {
		return recordIndexes;
	}
		
	private void isLike() {
		for (int i=0; i<leftOperands.size(); i++) 
			if (leftOperands.get(i).equals(rightOperand))
				recordIndexes.add(i); 
	}
	
}


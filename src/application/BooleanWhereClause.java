package application;

import java.util.ArrayList;
import java.util.List;


public class BooleanWhereClause {
	
	List<Integer> recordIndexes = new ArrayList<>();
	List<Boolean> leftOperands;
	Boolean rightOperand;
	String operator;

	BooleanWhereClause(List<Boolean> leftOperands, String operator, String rightOperand) throws DBException {
		try {
			this.rightOperand = Boolean.parseBoolean(rightOperand);
		} catch (NumberFormatException e) {
			throw new DBException ("Right operand ("+rightOperand+") isn't Boolean!");
		}
		this.operator = operator;
		this.leftOperands = leftOperands;
		findOperator();			
	}

	private void findOperator() throws DBException{
		switch (operator) {
		case "=":
			isEqual();
			break;
		case "!=":
			isNotEqual();
			break;
		default:
			throw new DBException("Invalid operator ("+operator+")!");
		}
	}
	
	List<Integer> getResult() {
		return recordIndexes;
	}
		
	private void isEqual() {
		for (int i=0; i<leftOperands.size(); i++) 
			if (leftOperands.get(i) == rightOperand)
				recordIndexes.add(i); 
	}
	
	private void isNotEqual() {
		for (int i=0; i<leftOperands.size(); i++) 
			if (leftOperands.get(i) != rightOperand)
				recordIndexes.add(i); 
	}
}


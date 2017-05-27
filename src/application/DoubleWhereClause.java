package application;

import java.util.ArrayList;
import java.util.List;


public class DoubleWhereClause {
	
	List<Integer> recordIndexes = new ArrayList<>();
	List<Double> leftOperands;
	Double rightOperand;
	String operator;

	DoubleWhereClause(List<Double> leftOperands, String operator, String rightOperand) throws DBException {
		try {
			this.rightOperand = Double.parseDouble(rightOperand);
		} catch (NumberFormatException e) {
			throw new DBException ("Right operand ("+rightOperand+") isn't Double!");
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
		case ">":
			isGreater();
			break;
		case ">=":
			isGreaterOrEqual();
			break;
		case "<":
			isSmaller();
			break;
		case "<=":
			isSmallerOrEqual();	
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
	
	private void isGreater() {
		for (int i=0; i<leftOperands.size(); i++) 
			if (leftOperands.get(i) > rightOperand)
				recordIndexes.add(i); 
	}
	
	private void isGreaterOrEqual() {
		for (int i=0; i<leftOperands.size(); i++) 
			if (leftOperands.get(i) >= rightOperand)
				recordIndexes.add(i); 
	}
	
	private void isSmaller() {
		for (int i=0; i<leftOperands.size(); i++) 
			if (leftOperands.get(i) < rightOperand)
				recordIndexes.add(i); 
	}
	
	private void isSmallerOrEqual() {
		for (int i=0; i<leftOperands.size(); i++) 
			if (leftOperands.get(i) <= rightOperand)
				recordIndexes.add(i); 
	}
	
}


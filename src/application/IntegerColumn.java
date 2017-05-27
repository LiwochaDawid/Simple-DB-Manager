package application;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
class IntegerColumn implements Column {
	private List<Integer> column = new ArrayList<>();
	private String name;
	private String dataType = "int";
	
	IntegerColumn(String name) {
		this.name = name;
	}
	
	@Override
	public void add(String value) throws DBException{
		try {
			column.add(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			throw new DBException("Can't insert ("+value+") to ("+dataType+") column!");
		}
	}
	
	@Override
	public void addDefault() {
		column.add(0);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getDataType() {
		return dataType;
	}
	
	@Override
	public String get(int index) {
		return Integer.toString(column.get(index));
	}
	
	@Override
	public int size() {
		return column.size();
	}
	
	@Override
	public void delete() {
		column.clear();
	}
	
	@Override
	public void delete(List<Integer> recordIndexes) {
		for (int i=0; i<recordIndexes.size(); i++)
			column.remove(recordIndexes.get(i)-i);
	}
	
	@Override
	public void set(String value) {
		for (int i=0; i<size(); i++)
			column.set(i, Integer.parseInt(value));
	}
	
	@Override
	public void set(String value, List<Integer> recordIndexes) {
		for (int i=0; i<recordIndexes.size(); i++)
			column.set(recordIndexes.get(i), Integer.parseInt(value));
	}
	
	@Override
	public List<Integer> compare(String operator, String rightOperand) throws DBException {
		return new IntegerWhereClause(column, operator, rightOperand).getResult();
	}
	
}

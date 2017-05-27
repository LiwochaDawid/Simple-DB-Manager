package application;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
class PrimaryKeyColumn implements Column {
	private List<Integer> column = new ArrayList<>();
	private String name;
	private String dataType = "primarykey";
	
	PrimaryKeyColumn(String name) {
		this.name = name;
	}
	
	@Override
	public void add(String value) throws DBException{
		try {
			if (column.contains(Integer.parseInt(value)))
				throw new DBException("Can't duplicate Primary Key ("+value+")!");
			column.add(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			throw new DBException("Can't insert ("+value+") to ("+dataType+") column!");
		}
	}
	
	@Override
	public void addDefault() throws DBException {
		throw new DBException("Primary Key hasn't default value!");
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
	public void set(String value) throws DBException {
		for (int i=0; i<size(); i++) {
			if (column.contains(Integer.parseInt(value)))
				throw new DBException("Can't duplicate Primary Key ("+value+")!");
			column.set(i, Integer.parseInt(value));
		}
	}
	
	@Override
	public void set(String value, List<Integer> recordIndexes) throws DBException{
		for (int i=0; i<recordIndexes.size(); i++) {
			if (column.contains(Integer.parseInt(value)))
				throw new DBException("Can't duplicate Primary Key ("+value+")!");
			column.set(recordIndexes.get(i), Integer.parseInt(value));
		}		
	}
	
	@Override
	public List<Integer> compare(String operator, String rightOperand) throws DBException {
		return new IntegerWhereClause(column, operator, rightOperand).getResult();
	}
	
}


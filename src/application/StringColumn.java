package application;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
class StringColumn implements Column {
	private List<String> column = new ArrayList<>();
	private String name;
	private String dataType = "string";
	
	StringColumn(String name) {
		this.name = name;
	}
	
	@Override
	public void add(String value) {
		column.add(value);
	}
	
	@Override
	public void addDefault() {
		column.add("");
	}
	
	@Override
	public String get(int index) {
		return column.get(index);
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
			column.set(i, value);
	}
	
	@Override
	public void set(String value, List<Integer> recordIndexes) {
		for (int i=0; i<recordIndexes.size(); i++)
			column.set(recordIndexes.get(i), value);
	}
	
	@Override
	public List<Integer> compare(String operator, String rightOperand) throws DBException {
		return new StringWhereClause(column, operator.toLowerCase(), rightOperand).getResult();
	}
}
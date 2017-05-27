package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
class Table implements Serializable{
		Map<String, ColumnFactory> columnsMap = new HashMap<>();
		private String name = new String();
		private List<Column> columns = new ArrayList<>();
		boolean containsPrimaryKey;
		
		Table(String name) {
			this.name = name;
		    columnsMap.put("string", new StringColumnFactory());
		    columnsMap.put("int", new IntegerColumnFactory());
		    columnsMap.put("double", new DoubleColumnFactory());
		    columnsMap.put("bool", new BooleanColumnFactory());
		    columnsMap.put("primarykey", new PrimaryKeyColumnFactory());
		}
		
		Table(Table table) {
			name = table.name;
			columnsMap = table.columnsMap;
			columns = table.columns;
		}
		
		String getName() {
			return name;
		}
		
		int getSizeOfRecords() {
			if (columns.isEmpty())
				return 0;
			else {
				return columns.get(0).size();
			}
		}
		
		int getSizeOfColumns() {
			return columns.size();
		}
		
		int indexOf(String name) throws DBException{
			for (int i=0; i<getSizeOfColumns(); i++) 
				if(columns.get(i).getName().equals(name))
					return i;
			throw new DBException ("Column ["+name+"] doesn't exist!");
		}
		
		boolean isColumnExist(String name) {
			for (int i=0; i<getSizeOfColumns(); i++) 
				if(columns.get(i).getName().equals(name))
					return true;
			return false;
		}	
		
		List<String> getColumnsNames() {
			List<String> columnsNames = new ArrayList<>();
			for (int i=0; i<getSizeOfColumns(); i++) {
				columnsNames.add(this.columns.get(i).getName());
			}
			return columnsNames;
		}		
		
		void addColumn(List<String> columns) throws DBException{
			String dataType, columnName;
			for (int i=0; i<columns.size(); i++)
				if (!isColumnExist(columns.get(i))) {
					columnName = columns.get(i).substring(0, columns.get(i).indexOf(" "));	
					dataType = columns.get(i).substring(columns.get(i).indexOf(" ")+1);
					if (dataType.equals("primarykey"))
						if (!containsPrimaryKey)
							containsPrimaryKey = true;
						else
							throw new DBException("Can't create more than one Primary Key column!");
					else {
						ColumnFactory columnFactory = columnsMap.get(dataType.toLowerCase());
						if (columnFactory == null)
							throw new DBException("Incorrect datatype ("+dataType+")!");
						else	
							this.columns.add(columnFactory.create(columnName));
					}
				}
			else
				throw new DBException ("Column ["+name+"] already exist!");
		}
		
		void addRecord(List<String> values) throws DBException {
			if (values.size() == getSizeOfColumns())
				for (int i=0; i<getSizeOfColumns(); i++)
					columns.get(i).add(values.get(i));
			else
				throw new DBException("Disproportionate number of columns ("+getSizeOfColumns()+") and values ("+values.size()+")!");
		}
		
		void addRecord(List<String> columnsNames, List<String> values) throws DBException {
			int sizeOfRecords = getSizeOfRecords();
			if (values.size() == columnsNames.size())
				for (int i=0; i<columnsNames.size(); i++)
					columns.get(indexOf(columnsNames.get(i))).add(values.get(i));
			else 
				throw new DBException("Disproportionate number of columns ("+columnsNames.size()+") and values ("+values.size()+")!");
			completeColumns(sizeOfRecords+1);
		}
		
		private void completeColumns(int sizeOfRecords) throws DBException{
			for (int i=0; i<columns.size(); i++)
				if (columns.get(i).size() < sizeOfRecords) 
					columns.get(i).addDefault();
		}
		
		String[][] getRecords() {
			String[][] records = new String[getSizeOfRecords()][getSizeOfColumns()];
			for (int i=0; i<getSizeOfColumns(); i++)
				for (int j=0; j<getSizeOfRecords(); j++)
					records[j][i] = this.columns.get(i).get(j);
			return records;
		}
		
		String[][] getRecords(String leftOperandName, String operator, String rightOperand) throws DBException{
			List<Integer> recordsIndexes = columns.get(indexOf(leftOperandName)).compare(operator, rightOperand);
			String[][] records = new String[recordsIndexes.size()][getSizeOfColumns()];
			for (int i=0; i<getSizeOfColumns(); i++)
				for (int j=0; j<recordsIndexes.size(); j++)
						records[j][i] = this.columns.get(i).get(recordsIndexes.get(j));
			return records;
		}
		
		String[][] getRecords(List<String> columnsNames) throws DBException {
			String[][] records = new String[getSizeOfRecords()][columnsNames.size()];
			for (int i=0; i<columnsNames.size(); i++)
				for (int j=0; j<getSizeOfRecords(); j++)
					records[j][i] = this.columns.get(indexOf(columnsNames.get(i))).get(j);
			return records;
		}
		
		String[][] getRecords(List<String> columnsNames, String leftOperandName, String operator, String rightOperand) throws DBException {
			List<Integer> recordsIndexes = columns.get(indexOf(leftOperandName)).compare(operator, rightOperand);
			String[][] records = new String[getSizeOfRecords()][columnsNames.size()];
			for (int i=0; i<columnsNames.size(); i++)
				for (int j=0; j<recordsIndexes.size(); j++)
					records[j][i] = this.columns.get(indexOf(columnsNames.get(i))).get(recordsIndexes.get(j));
			return records;
		}
		
		void deleteRecords(DataKeeper dataKeeper) {
			int sizeOfRecords = getSizeOfRecords();
			for (int i=0; i<getSizeOfColumns(); i++)
				columns.get(i).delete();
			dataKeeper.updateMessagesLabelText(sizeOfRecords+" row(s) deleted.");
		}
		
		void deleteRecords(DataKeeper dataKeeper, String leftOperandName, String operator, String rightOperand) throws DBException {
			List<Integer> recordsIndexes = columns.get(indexOf(leftOperandName)).compare(operator, rightOperand);
			for (int i=0; i<getSizeOfColumns(); i++)
				columns.get(i).delete(recordsIndexes);
			dataKeeper.updateMessagesLabelText(recordsIndexes.size()+" row(s) deleted.");
		}
		
		void updateRecords(DataKeeper dataKeeper, List<String> columnNames, List<String> values) throws DBException{
			int sizeOfRecords = getSizeOfRecords();
			for (int i=0; i<columnNames.size(); i++)
				columns.get(indexOf(columnNames.get(i))).set(values.get(i));
			dataKeeper.updateMessagesLabelText(sizeOfRecords+" row(s) updated.");
		}
		
		void updateRecords(DataKeeper dataKeeper, List<String> columnNames, List<String> values, String leftOperandName, String operator, String rightOperand) throws DBException{
			List<Integer> recordsIndexes = columns.get(indexOf(leftOperandName)).compare(operator, rightOperand);
			for (int i=0; i<columnNames.size(); i++)
				columns.get(indexOf(columnNames.get(i))).set(values.get(i), recordsIndexes);
			dataKeeper.updateMessagesLabelText(recordsIndexes.size()+" row(s) updated.");
		}
}


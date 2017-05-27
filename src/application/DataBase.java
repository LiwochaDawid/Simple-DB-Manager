package application;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
class DataBase implements Serializable {
	
	private String name = new String();
	private ArrayList<Table> tables = new ArrayList<Table>();
	
	DataBase(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	void newTable(String name) {
		tables.add(new Table(name));
	}
	
	void newTable(Table table) {
		tables.add(new Table(table));
	}
	
	void deleteTable(String name) {
		for (int i=0; i<tables.size(); i++)
			if (tables.get(i).getName().equals(name))
				tables.remove(i);
	}
	
	Table getTable(String name) throws DBException{
		for (int i=0; i<tables.size(); i++) 
			if (tables.get(i).getName().equals(name))
				return tables.get(i);
		throw new DBException("Table ["+name+"] does not exists!");
	}
	
	String getLastTableName() {
		return tables.get(tables.size()-1).getName();
	}
	
	boolean isTableExist(String name) {
		for (int i=0; i<tables.size(); i++) 
			if (tables.get(i).getName().equals(name))
				return true;
		return false;
	}	
	
}

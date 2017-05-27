package application;

import java.io.Serializable;
import java.util.List;

interface Column extends Serializable {
	void add(String value) throws DBException;
	void addDefault() throws DBException;
	String getName();
	String getDataType();
	String get(int index);
	void delete();
	void delete(List<Integer> recordIndexes);
	void set(String value) throws DBException;
	void set(String value, List<Integer> recordIndexes) throws DBException;
	int size();
	List<Integer> compare(String operator, String rightOperand) throws DBException;
}

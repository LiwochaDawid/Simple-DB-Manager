package application;

@SuppressWarnings("serial")
class IntegerColumnFactory implements ColumnFactory{
	@Override
	public Column create(String name) {
		return new IntegerColumn(name);
	}
}

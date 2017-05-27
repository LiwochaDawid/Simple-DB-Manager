package application;

@SuppressWarnings("serial")
class StringColumnFactory implements ColumnFactory{
	@Override
	public Column create(String name) {
		return new StringColumn(name);
	}
}

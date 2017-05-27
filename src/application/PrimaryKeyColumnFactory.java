package application;

@SuppressWarnings("serial")
class PrimaryKeyColumnFactory implements ColumnFactory{
	@Override
	public Column create(String name) {
		return new PrimaryKeyColumn(name);
	}
}

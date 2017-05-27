package application;

@SuppressWarnings("serial")
class BooleanColumnFactory implements ColumnFactory{
	@Override
	public Column create(String name) {
		return new BooleanColumn(name);
	}
}

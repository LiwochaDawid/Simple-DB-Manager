package application;

@SuppressWarnings("serial")
class DoubleColumnFactory implements ColumnFactory{
	@Override
	public Column create(String name) {
		return new DoubleColumn(name);
	}
}


package application;

import java.io.Serializable;

interface ColumnFactory extends Serializable {
	Column create(String name);
}

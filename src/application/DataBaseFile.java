package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


class DataBaseFile {
	DataKeeper saveDataBase(DataKeeper dataKeeper) throws DBException{
		try{
			 ObjectOutputStream outDB = new ObjectOutputStream(new FileOutputStream("DataBase.jsdb"));
			 outDB.writeObject(dataKeeper.getDataBase());
			 outDB.flush();
			 outDB.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new DBException("Database saving error!");
		}
		return dataKeeper;
	}
	
	DataKeeper loadDataBase(DataKeeper dataKeeper) throws DBException{
		try {
		    ObjectInputStream inDB = new ObjectInputStream(new FileInputStream("DataBase.jsdb"));
		    dataKeeper.updateDataBase((DataBase) inDB.readObject());
		    dataKeeper.updateDataBaseNameLabelText("DB Name: "+dataKeeper.getDataBase().getName());
		    inDB.close();
		} catch (ClassNotFoundException e) {
			throw new DBException("Database loading error!");
		} catch (IOException e) {
			throw new DBException("Database loading error!");
		}
		return dataKeeper;
	}
}

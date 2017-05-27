package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


class QueryFile {
	DataKeeper loadQuery(DataKeeper dataKeeper) throws DBException{
		try{
			BufferedReader queryReader = new BufferedReader(new FileReader("Query.jsq"));
			String readedNextLine = "";
			String queryContent = "";
				while((readedNextLine = queryReader.readLine()) != null)
					queryContent += readedNextLine + "\n";
				dataKeeper.updateOpenedQueryContent(queryContent);
				queryReader.close();
			dataKeeper.updateMessagesLabelText("Query load success.");
			return dataKeeper;
		} catch (IOException e) {
			throw new DBException("Query load error!");
		}
	}
	
	DataKeeper saveQuery(DataKeeper dataKeeper) throws DBException{
		String[] query = dataKeeper.getOpenedQueryContent().split("\n");
		try {
		    BufferedWriter queryWriter = new BufferedWriter(new FileWriter("Query.jsq"));
		    queryWriter.write("");
		    for (int i=0; i<query.length; i++) {
		    	queryWriter.append(query[i]); 
		    	queryWriter.newLine();
		    }
		    queryWriter.close();
		    dataKeeper.updateMessagesLabelText("Query save success.");
		    return dataKeeper;
		}
		catch (IOException e) {
			throw new DBException("Query save error!");
		}
	}
}

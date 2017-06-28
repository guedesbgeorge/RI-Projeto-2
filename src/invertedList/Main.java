package invertedList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		FileWriter resultFile = new FileWriter(new File("saida.csv"));
		List<File> files = new ArrayList<>();
		files.add(new File("1.csv"));
		BuildInvertedList bil = new BuildInvertedList(resultFile, files);
		bil.build();
	
	}

}

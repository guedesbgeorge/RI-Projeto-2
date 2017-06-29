package invertedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		FileWriter resultFile = new FileWriter(new File("invertedIndex/saida.csv"));
		FileWriter compressedResultFile = new FileWriter(new File("invertedIndex/compressed.csv"));
		List<File> files = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			String fileName = "database/" + i + ".csv";
			files.add(new File(fileName));
		}
		
		BuildInvertedList bil = new BuildInvertedList(resultFile, files, compressedResultFile);
		bil.build();
	}

}

package invertedList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BuildInvertedList {
	private FileWriter resultFile;
	private List<File> files;//files to be handled
	private InvertedIndex invertedIndex;
	
	public BuildInvertedList(FileWriter resultFile, List<File> files)
	{
		this.resultFile = resultFile;
		this.files = files;
		this.invertedIndex = new InvertedIndex();
	}
	
	public void build() throws IOException
	{
		for (File file : this.files) 
		{
			FileReader inputStream = new FileReader(file);
			BufferedReader br = new BufferedReader(inputStream);
			this.findPriceTag(br, file.getName());			
		}
		
		this.makeInvertedIndexCSV();
	}
		
	private void findPriceTag(BufferedReader br, String fileName)
	{
		String line, numero = "";
		try {
			line = br.readLine();
			//System.out.println(line);
			while(line != null)
			{
				String lowercaseLine = line.toLowerCase();
				if (lowercaseLine.contains("preco"))
				{
					//Getting just the numerical part of price tag
					String preco[] = lowercaseLine.split(";");
					//System.out.println(preco[1]);
					numero = preco[1].split(",")[0];
					numero = numero.replaceAll("[^0-9]", "");
					//System.out.println(numero);
					
					//type for price tag
					this.invertedIndex.insertInvertedIndex(0, numero, fileName, 1); //ver como pegar filePosition
				}
					
				line = br.readLine();
				//System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void makeInvertedIndexCSV() throws IOException
	{
		this.resultFile.write(this.invertedIndex.toString());
		this.resultFile.close();
	}
}

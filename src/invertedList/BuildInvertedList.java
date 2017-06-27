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
		String line;
		try {
			line = br.readLine();
			int position = 0, count = 0;
			while(line != null)
			{
				String lowercaseLine = line.toLowerCase();
				if (lowercaseLine.contains("preco"))
				{
					this.getPriceData(lowercaseLine, fileName, position);
				}
				else if (line.contains(""))
				{
					count++;
					if (count >= 3)
					{
						System.out.println(position);
						position++;
						count = 0;
					}
				}
				
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void makeInvertedIndexCSV() throws IOException
	{
		this.resultFile.write(this.invertedIndex.toString());
		this.resultFile.close();
	}
	
	private void getPriceData(String lowercaseLine, String fileName, int position)
	{
		String numero = "";
		//Getting just the numerical part of price tag
		String preco[] = lowercaseLine.split(";");
		
		//remove de cents parts and de R$
		numero = preco[1].split(",")[0];
		numero = numero.replaceAll("[^0-9]", "");
		

		this.invertedIndex.insertInvertedIndex(TypeData.PRICE, numero, fileName, position);
	}
}

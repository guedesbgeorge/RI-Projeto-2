package invertedList;

import java.io.BufferedReader;
import model.Smartphone;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author Allyson Manoel 
 *
 */
public class BuildInvertedList {
	private FileWriter resultFile;
	private List<File> files;//files to be handled
	private InvertedIndex invertedIndex;
	private int[] tamCSVs;
	private ArrayList<Smartphone> bancoSmartphones;
	
	public BuildInvertedList(FileWriter resultFile, List<File> files)
	{
		this.bancoSmartphones = new ArrayList<>();
		this.resultFile = resultFile;
		this.files = files;
		this.invertedIndex = new InvertedIndex();
		this.tamCSVs = new int[10];
	}
	
	public ArrayList<Smartphone> getSmartphones(){
		return this.bancoSmartphones;
	}

	public int getNumFiles() {
		return this.files.size();
	}

	public int[] getTamCSVs() {
		return this.tamCSVs;
	}

	public InvertedIndex getInvertedIndex() {
		return this.invertedIndex;
	}
	
	public void build() throws IOException
	{
		int count = 0;
		for (File file : this.files) 
		{
			FileReader inputStream = new FileReader(file);
			BufferedReader br = new BufferedReader(inputStream);
			this.tamCSVs[count] = this.realBuild(br, file.getName());
			count++;
		}
		
		this.makeInvertedIndexCSV();
	}
		
	private int realBuild(BufferedReader br, String fileName)
	{
		String line;
		int position = 0, count = 0;
		try {
			line = br.readLine();
			while(line != null)
			{
				String lowercaseLine = line.toLowerCase();
				String preco, bateria, conectividade, so, nome = "";
				conectividade = "";
				preco = "";
				so = "";
				bateria = "";
				
				if (lowercaseLine.contains("preco"))
				{
					preco = this.getPriceData(lowercaseLine, fileName, position);
				}
				else if (line.equals(""))
				{
					count++;
					if (count >= 3)
					{
						position++;
						count = 0;
					}
				}
				else if (lowercaseLine.contains("alimentacao") || lowercaseLine.contains("tipo de bateria"))
				{
					bateria = this.getBateryTag(lowercaseLine, fileName, position);
				}
				else if (lowercaseLine.contains("conectividade") || lowercaseLine.contains("conexão Internet"))
				{
					conectividade = this.getConnectivityTag(lowercaseLine, fileName, position);
				}
				else if (lowercaseLine.contains("sistema operacional") || lowercaseLine.contains("versão"))
				{
					so = this.getOS(lowercaseLine, fileName, position);
				}
				else if (lowercaseLine.contains("nome produto"))
				{
					nome = this.getProductName(lowercaseLine, fileName, position);
				}
				ArrayList<String> c = new ArrayList<>();
				c.add(conectividade);
				bancoSmartphones.add(new Smartphone(nome, preco, bateria, so, c));
				line = br.readLine();
			}
			return position;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	private void makeInvertedIndexCSV() throws IOException
	{
		//System.out.println(this.invertedIndex.toString());
		this.resultFile.write(this.invertedIndex.toString());
		this.resultFile.close();
	}
	
	private String getProductName(String line, String fileName, int position)
	{
		String aux = line.split(";")[1];
		aux = aux.replaceAll("-", "");
		String values[] = aux.split(" ");
		
		for (int i = 0; i < values.length; i++) 
		{
			if (!values[i].equals("")) 
			{
				//System.out.println(values[i]);
				this.invertedIndex.insertInvertedIndex(TypeData.PRODUCT_NAME, values[i], fileName, position);
			}
		}
		return aux;
	}
	
	private String getOS(String line, String fileName, int position)
	{
		String values = line.split(";")[1];
		if (this.isNotNumeric(values))
		{
			values = values.split(" ")[0];
			//System.out.println(values);
			this.invertedIndex.insertInvertedIndex(TypeData.OPERATING_SYSTEM, values, fileName, position);
			return values;
		}
		return null;
	}
	
	private String getConnectivityTag(String line, String fileName, int position)
	{
		String values[] = line.split(";");
		values = values[1].split(",");
		String l = "";
		for (int i = 0; i < values.length; i++)
		{
			String aux[] = values[i].split(" ");
			l = aux[0];
			if (values[i].equals("wi-fi"))
			{
				l = "Wifi"; 
			} 
			else 
			{
				if (aux.length > 1) 
				{
					l = aux[0] + aux[1];
				}
			}
			this.invertedIndex.insertInvertedIndex(TypeData.CONNECTIVITE, l, fileName, position);
		}
		return l;
	}
	
	private String getBateryTag(String line, String fileName, int position)
	{
		String value = line.split(";")[1];
		int pos = value.indexOf("mah");
		
		if (pos != -1)
		{
			value = value.substring(0, pos);
			boolean whitespace = true;
			int finalPos = 0;
			for(int i = value.length() - 1; i >= 0; i--)
			{
				char aux = value.charAt(i); 
				if((aux >= '0' && aux <= '9') || aux == '.')
				{
					finalPos = i;
				}	
				else if (whitespace)
				{
					whitespace = false;
				}
				else break;
					
			}
			value = value.substring(finalPos);
			value = removePonto(value);

			this.invertedIndex.insertInvertedIndex(TypeData.BATTERY_TYPE, value, fileName, position);
			return value;
		}
		return null;
	}
	
	private String getPriceData(String lowercaseLine, String fileName, int position)
	{
		String numero = "";
		//Getting just the numerical part of price tag
		String preco[] = lowercaseLine.split(";");
		
		if (preco.length > 1)
		{
			//remove ,00 parts and de R$
			numero = preco[1].split(",")[0];
			numero = numero.replaceAll("[^0-9]", "");
	
			this.invertedIndex.insertInvertedIndex(TypeData.PRICE, numero, fileName, position);
			return numero;
		}
		return null;
	}
	
	
	private boolean isNotNumeric(String str) 
	{
		for (int i = 0; i < str.length(); i++) 
		{
			char aux = str.charAt(i); 
			if(aux >= '0' && aux <= '9')
			{
				return false;
			}
		}
		return true;
	}
	
	private String removePonto(String value)
	{
		String aux = "";

		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) >= '0' && value.charAt(i) <= '9')
			{
				aux += value.charAt(i);
			}
		}

		return aux;
	}
}

package invertedList;

import java.util.Vector;
/**
 * 
 * @author Allyson Manoel
 *
 */
public class InvertedIndex {
	private Vector<IndexRow> indexRows;
	
	public InvertedIndex() {
		this.indexRows = new Vector<>();
		setIndexRowDefaultElements();
	}
	
	private void setIndexRowDefaultElements()
	{
		//Make price labels 
		for (int i = 0; i < 4000; i+=300)
		{
			String preco = "Preco[" + (i+1) + "-" + (i+300) +"]";
			this.indexRows.addElement(new IndexRow(preco));
		}
		
		//make connectivity labels
		this.indexRows.add(new IndexRow("Conexao.Wifi"));
		this.indexRows.add(new IndexRow("Conexao.3g"));
		this.indexRows.add(new IndexRow("Conexao.4g"));
		this.indexRows.add(new IndexRow("Conexao.Bluetooth"));
		this.indexRows.add(new IndexRow("Conexao.NFC"));
		
		//make battery labels
		for (int i = 000; i < 5000; i+=300)
		{
			String battery = "Bateria[" + (i+1) + "-" + (i+300) +"]";
			this.indexRows.addElement(new IndexRow(battery));
		}
		
		//make OS labels
		this.indexRows.addElement(new IndexRow("OS.android"));
		this.indexRows.addElement(new IndexRow("OS.ios"));
		this.indexRows.addElement(new IndexRow("OS.windows_phone"));
	}
	
	
	public void insertInvertedIndex(TypeData type, String value, String fileName, int dataPosition)
	{
		//if price
		if(type.equals(TypeData.PRICE))
		{
			this.insertPrice(dataPosition, value, fileName);
		}
		else if (type.equals(TypeData.CONNECTIVITE))
		{
			this.insertConnetivite(dataPosition, value, fileName);
		}
		else if (type.equals(TypeData.BATTERY_TYPE))
		{
			this.insertBattery(dataPosition, value, fileName);
		}
		else if (type.equals(TypeData.OPERATING_SYSTEM))
		{
			this.insertOS(dataPosition, value, fileName);
		}
		else if (type.equals(TypeData.PRODUCT_NAME))
		{
			this.insertProductName(dataPosition, value, fileName);
		}
			
	}
	
	private void insertProductName(int dataPosition, String value, String fileName)
	{
		String info = "Nome." + value;
		TermData td = new TermData(dataPosition, 1, fileName);
		IndexRow inRow = getRowIfExists(info);
		
		if (inRow == null)
		{
			this.indexRows.addElement(new IndexRow(info, td));
		}
		else 
		{
			inRow.addPosting(td);
		}
	}
	
	private void insertOS(int dataPosition, String value, String fileName)
	{
		for (IndexRow indexRow : indexRows) 
		{
			if(indexRow.getWord().contains(value))
			{
				TermData td = new TermData(dataPosition, 1, fileName);
				indexRow.addPosting(td);
			}
		}
	}
	
	private void insertBattery(int dataPosition, String value, String fileName)
	{
		double number = Double.parseDouble(value);
		double position = Math.ceil(number/300);
		
		double count = 0;
		for (IndexRow indexRow : indexRows) 
		{
			if(indexRow.getWord().startsWith("Bateria"))
			{
				count+=1;
				if (count == position)
				{
					TermData td = new TermData(dataPosition, 1, fileName);
					indexRow.addPosting(td);
				}
			}
		}
	}
	
	private void insertConnetivite(int dataPosition, String value, String fileName)
	{
		for (IndexRow indexRow : indexRows) 
		{
			String aux = "Conexao." + value; 
			if(indexRow.getWord().contains(aux))
			{
				TermData td = new TermData(dataPosition, 1, fileName);
				indexRow.addPosting(td);
			}
		}
	}
	
	
	private void insertPrice(int dataPosition, String value, String fileName)
	{
		double number = Double.parseDouble(value);
		double position = Math.ceil(number/300);
		
		double count = 0;
		for (IndexRow indexRow : indexRows) 
		{
			if(indexRow.getWord().startsWith("Preco"))
			{
				count+=1;
				if (count == position)
				{
					TermData td = new TermData(dataPosition, 1, fileName);
					indexRow.addPosting(td);
					break;
				}
			}
		}
	}

	private IndexRow getRowIfExists(String info)
	{
		for (IndexRow indexRow : this.indexRows) 
		{
			if (indexRow.getWord().equals(info))
				return indexRow;
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (IndexRow indexRow : indexRows) {
			sb.append(indexRow.getWord());
			sb.append(";");
			
			sb.append(indexRow.getPosting().size());
			
			for (TermData posting : indexRow.getPosting()) {
				sb.append(posting.toString());
				sb.append(",");
			}
			sb.setLength(sb.length() - 1);
			sb.append("\n");
		}
		
		return sb.toString();
	}
}

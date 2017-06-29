package invertedList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
/**
 * 
 * @author Allyson Manoel
 *
 */
public class InvertedIndex {
	private HashMap<String, IndexRow> indexRows;
	private int curDocID;
	
	public InvertedIndex() {
		this.indexRows = new HashMap<>();
		setIndexRowDefaultElements();
		this.curDocID = 0;
	}

	public HashMap<String, IndexRow> getIndexRows() {
		return this.indexRows;
	}
	
	private void setIndexRowDefaultElements()
	{
		//Make price labels 
		for (int i = 0; i < 4000; i+=300)
		{
			String preco = "Preco[" + (i+1) + "-" + (i+300) +"]";
			this.indexRows.put(preco, new IndexRow(preco));
		}
		
		//make connectivity labels
		this.indexRows.put("Conexao.Wifi", new IndexRow("Conexao.Wifi"));
		this.indexRows.put("Conexao.3g", new IndexRow("Conexao.3g"));
		this.indexRows.put("Conexao.4g", new IndexRow("Conexao.4g"));
		this.indexRows.put("Conexao.Bluetooth", new IndexRow("Conexao.Bluetooth"));
		this.indexRows.put("Conexao.NFC", new IndexRow("Conexao.NFC"));
		
		//make battery labels
		for (int i = 000; i < 5000; i+=300)
		{
			String battery = "Bateria[" + (i+1) + "-" + (i+300) +"]";
			this.indexRows.put(battery, new IndexRow(battery));
		}
		
		//make OS labels
		this.indexRows.put("OS.android", new IndexRow("OS.android"));
		this.indexRows.put("OS.ios", new IndexRow("OS.ios"));
		this.indexRows.put("OS.windows_phone", new IndexRow("OS.windows_phone"));
	}
	
	
	public void insertInvertedIndex(TypeData type, String value, String fileName, int dataPosition)
	{


		//if price
		if(type.equals(TypeData.PRICE))
		{
			this.insertPrice(dataPosition, value, fileName, this.curDocID);
		}
		else if (type.equals(TypeData.CONNECTIVITE))
		{
			this.insertConnetivite(dataPosition, value, fileName, this.curDocID);
		}
		else if (type.equals(TypeData.BATTERY_TYPE))
		{
			this.insertBattery(dataPosition, value, fileName, this.curDocID);
		}
		else if (type.equals(TypeData.OPERATING_SYSTEM))
		{
			this.insertOS(dataPosition, value, fileName, this.curDocID);
		}
		else if (type.equals(TypeData.PRODUCT_NAME))
		{
			this.insertProductName(dataPosition, value, fileName, this.curDocID);
		}

		this.curDocID = this.curDocID + 1;
	}
	
	private void insertProductName(int dataPosition, String value, String fileName, int docID)
	{
		String info = "Nome." + value;
		TermData td = new TermData(dataPosition, 1, fileName, docID);
		IndexRow inRow = getRowIfExists(info);
		
		if (inRow == null)
		{
			this.indexRows.put(info, new IndexRow(info, td));
		}
		else 
		{
			inRow.addPosting(td);
		}
	}
	
	private void insertOS(int dataPosition, String value, String fileName, int docID)
	{
		Iterator it = indexRows.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, IndexRow> pair = (Map.Entry<String, IndexRow>)it.next();
			//System.out.println(pair.getKey());
			//System.out.println(pair.getValue());
			
			if(pair.getKey().contains(value)){
				TermData td = new TermData(dataPosition, 1, fileName, docID);
				pair.getValue().addPosting(td);
			}
		}
		
		/*for (IndexRow indexRow : indexRows) 
		{
			if(indexRow.getWord().contains(value))
			{
				TermData td = new TermData(dataPosition, 1, fileName);
				indexRow.addPosting(td);
			}
		}*/
	}
	
	private void insertBattery(int dataPosition, String value, String fileName, int docID)
	{
		double number = Double.parseDouble(value);
		double position = Math.ceil(number/300);
		Iterator it = indexRows.entrySet().iterator();
		
		double count = 0;
		while(it.hasNext()){
			Map.Entry<String, IndexRow> pair = (Map.Entry<String, IndexRow>)it.next();
			
			//System.out.println(pair.getKey());
			//System.out.println(pair.getValue());
			IndexRow indexRow = pair.getValue();
			if(indexRow.getWord().startsWith("Bateria"))
			{
				count+=1;
				if (count == position)
				{
					TermData td = new TermData(dataPosition, 1, fileName, docID);
					indexRow.addPosting(td);
				}
			}
		}
		
		/*
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
		}*/
	}
	
	private void insertConnetivite(int dataPosition, String value, String fileName, int docID)
	{
		Iterator it = indexRows.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, IndexRow> pair = (Map.Entry<String, IndexRow>)it.next();
			//System.out.println(pair.getKey());
			//System.out.println(pair.getValue());
			IndexRow indexRow = pair.getValue();
			String aux = "Conexao." + value; 
			 
			if(indexRow.getWord().contains(aux))
			{
				TermData td = new TermData(dataPosition, 1, fileName, docID);
				indexRow.addPosting(td);
			}
		}
		/*
		for (IndexRow indexRow : indexRows) 
		{
			String aux = "Conexao." + value; 
			if(indexRow.getWord().contains(aux))
			{
				TermData td = new TermData(dataPosition, 1, fileName);
				indexRow.addPosting(td);
			}
		}
		*/
	}
	
	
	private void insertPrice(int dataPosition, String value, String fileName, int docID)
	{
		double number = Double.parseDouble(value);
		double position = Math.ceil(number/300);
		
		double count = 0;
		Iterator it = indexRows.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, IndexRow> pair = (Map.Entry<String, IndexRow>)it.next();
			IndexRow indexRow = pair.getValue();
			if(indexRow.getWord().startsWith("Preco"))
			{
				count+=1;
				if (count == position)
				{
					TermData td = new TermData(dataPosition, 1, fileName, docID);
					indexRow.addPosting(td);
					break;
				}
			}
		}
		/*
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
		}*/
	}

	private IndexRow getRowIfExists(String info)
	{
		Iterator it = indexRows.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, IndexRow> pair = (Map.Entry<String, IndexRow>)it.next();
			IndexRow indexRow = pair.getValue();
			
			if (indexRow.getWord().equals(info))
				return indexRow;
		}
		/*
		for (IndexRow indexRow : this.indexRows) 
		{
			if (indexRow.getWord().equals(info))
				return indexRow;
		}
		*/
		return null;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		Iterator it = indexRows.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, IndexRow> pair = (Map.Entry<String, IndexRow>)it.next();
			IndexRow indexRow = pair.getValue();
			
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

		/*
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
		*/
		
		return sb.toString();
	}
}

package invertedList;

import java.util.Vector;
/**
 * 
 * @author allyson
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
		for (int i = 0; i < 4000; i+=300)
		{
			String preco = "Preco[" + (i+1) + "-" + (i+300) +"]";
			//System.out.println(preco);
			this.indexRows.addElement(new IndexRow(preco));
		}
	}
	
	
	public void insertInvertedIndex(TypeData type, String value, String fileName, int dataPosition)
	{
		//if price
		if(type.equals(TypeData.PRICE))
		{
			double number = Double.parseDouble(value);
			double position = Math.ceil(number/300) - 1.0;
			
			double count = 0;
			for (IndexRow indexRow : indexRows) 
			{
				if(indexRow.getWord().startsWith("Preco"))
				{
					count+=1;
					if (count == position)
					{
						//discover how to show frequency
						TermData td = new TermData(dataPosition, 0, fileName);
						indexRow.addPosting(td);
					}
				}
			}
		}
			
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (IndexRow indexRow : indexRows) {
			sb.append(indexRow.getWord());
			sb.append(";");
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

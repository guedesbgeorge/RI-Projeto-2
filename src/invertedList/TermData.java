package invertedList;
/**
 * 
 * @author allyson
 * Information about the word in documents 
 */
public class TermData {
	//starts with zero and increment for each 3 lines separation in csv document
	private int position;
	private int frequency;
	private String documentID;
	
	public TermData(int position, int frequency, String documentID) {
		this.position = position;
		this.frequency = frequency;
		this.documentID = documentID;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getDocumentID() {
		return documentID;
	}

	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}
	
	private String getId()
	{
		String aux = "";
		for (int i = 0; i < this.documentID.length(); i++) 
		{
			if (this.documentID.charAt(i) == '.') break;
			aux += this.documentID.charAt(i);
		}
		
		return aux;
	}
	
	@Override
	public String toString()
	{
		String doc = getId();
		return "[" + this.position + "," + this.frequency + "," + doc + "]";
	}
	
	public String compressedToString(int tamCSVs[])
	{
		int valor = this.position;
		int doc = Integer.parseInt(getId());
		for (int i = 0; i < doc; i++)
		{
			valor += tamCSVs[i];
		}
		return "[" + valor + "," + this.frequency + "]";
	}
}

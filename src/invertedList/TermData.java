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
	
	@Override
	public String toString()
	{
		return "[" + this.position + "," + this.frequency + "," + this.documentID + "]";
	}
}

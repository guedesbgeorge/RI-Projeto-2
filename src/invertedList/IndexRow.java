package invertedList;

import java.util.ArrayList;
import java.util.List;

public class IndexRow {
	private String word;
	private List<TermData> posting;

	public IndexRow(String word) {
		this.word = word;
		this.posting = new ArrayList<TermData>();
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<TermData> getPosting() {
		return posting;
	}

	public void setPosting(List<TermData> posting) {
		this.posting = posting;
	}

	public void addPosting(TermData termData)
	{
		this.posting.add(termData);
	}
	
}

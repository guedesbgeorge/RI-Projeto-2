package invertedList;

import java.util.ArrayList;
import java.util.List;

public class IndexRow {
	private String word;
	private List<TermData> posting;
	private int position;

	public IndexRow(String word) {
		this.word = word;
		this.posting = new ArrayList<TermData>();
		this.position = 0;
	}

	public TermData getTermData() {
		return posting.get(position);
	}

	public void movePastDocument() {
		this.position = this.position + 1;
		if(this.position >= this.posting.size()) {
			this.position = 0;
		}
	}
	
	public IndexRow(String word, TermData posting)
	{
		this.word = word;
		this.posting = new ArrayList<TermData>();
		this.posting.add(posting);
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

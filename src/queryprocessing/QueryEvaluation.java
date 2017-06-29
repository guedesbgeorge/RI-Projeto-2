package queryprocessing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import invertedList.BuildInvertedList;
import invertedList.IndexRow;
import invertedList.InvertedIndex;
import invertedList.TermData;
import model.Smartphone;

/**
 * Created by ianmanor on 28/06/17.
 */
public class QueryEvaluation {
    private InvertedIndex invertedIndex;
    private int numFiles;
    private int[] tamCSVs;
    private ArrayList<Smartphone> bancoSmartphones;

    private final boolean TFIDF_RANKING = true;
    private final boolean DOCUMENT_AT_ATIME = true;

    public QueryEvaluation() {
        BuildInvertedList bil = null;

        try {
            FileWriter resultFile = new FileWriter(new File("invertedIndex/saida.csv"));
            List<File> files = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                String fileName = "database/" + i + ".csv";
                files.add(new File(fileName));
            }

            bil = new BuildInvertedList(resultFile, files);
            bil.build();
            this.bancoSmartphones = bil.getSmartphones();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            this.invertedIndex = bil.getInvertedIndex();
            this.numFiles = bil.getNumFiles();
            this.tamCSVs = bil.getTamCSVs();
        }
    }

    public HashMap<Smartphone, Double> query(Smartphone queryPhone) {
        HashMap<Smartphone, Double> results;
        Query query = new Query(queryPhone);
        if(DOCUMENT_AT_ATIME) {
            results = documentRetrieval(query);
        } else {
            results = termRetrieval(query);
        }
        return results;
    }

    public HashMap<Smartphone, Double> documentRetrieval(Query queryPhone) {
        HashMap<Smartphone, Double> results = new HashMap<>();

        HashMap<String, IndexRow> indexRows = this.invertedIndex.getIndexRows();

        Vector<IndexRow> filteredIndexRows = new Vector<>();

        //filter index rows that dont contain query words
        for(int i = 0; i < queryPhone.getTerms().size(); i++){
            String term = queryPhone.getTerms().get(i);
            IndexRow indexRow = indexRows.get(term);
            filteredIndexRows.addElement(indexRow);
        }
        /*
        for(int i = 0; i < indexRows.size(); i++) {
        	//System.out.println(indexRows.elementAt(i).getTermData().getPosition());
        	String posting = indexRows.elementAt(i).getWord();
        	System.out.print(i + " ");
        	System.out.println(posting);
        	if(posting.indexOf(".") != -1){
        		posting = posting.substring(posting.indexOf(".")+1, posting.length());
        	}
        	//System.out.println(posting);

        	if(i <= 13){
        		if(posting.equals(queryPhone.getTerms().get(3))) {
        			filteredIndexRows.add(indexRows.elementAt(i));
        		}
        	}
        	if(i > 13 && i < 19){
        		if(queryPhone.getConectividades() != null && queryPhone.getConectividades().contains(posting)){
        			filteredIndexRows.add(indexRows.elementAt(i));
        		}
        	}
        	if(i >= 19 && i < 36){
        		if(posting.contains(queryPhone.getTerms().get(1))){
        			filteredIndexRows.add(indexRows.elementAt(i));
        		}
        	}
        	if(i >= 36 && i < 40){
        		if(posting.equals(queryPhone.getTerms().get(2))){
        			filteredIndexRows.add(indexRows.elementAt(i));
        		}
        	}
        }*/

        System.out.println("tamanho lista = " + filteredIndexRows.size());

        //loop over documents
        for(int docID = 0; docID < bancoSmartphones.size(); docID++) {
            double score = 0;
            for(int i = 0; i < filteredIndexRows.size(); i++) {
                System.out.println();
                System.out.println(docID);
                System.out.println(i);
                System.out.println(filteredIndexRows.elementAt(i).getWord());
                TermData termData = filteredIndexRows.elementAt(i).getTermData();
                if(termData.getDocumentID() == "docID") {
                    //update document score
                    if(TFIDF_RANKING) {
                        //score = score + 1;
                    } else {
                        score = score + 1;
                    }
                }
                filteredIndexRows.elementAt(i).movePastDocument(bancoSmartphones.size());
            }
        }

        /*
        int docID = 0;
        for(int i = 0; i < numFiles; i++) {
            for(int j = 0; j < tamCSVs[i]; j++) {

                docID = docID + 1;

                //results.put(queryPhone, new Double(score));
            }
        }*/

        return results;
    }

    public HashMap<Smartphone, Double> termRetrieval(Query queryPhone) {
        HashMap<Smartphone, Double> results = new HashMap<>();

        HashMap<String, IndexRow> indexRows = this.invertedIndex.getIndexRows();

        Vector<IndexRow> filteredIndexRows = new Vector<>();

        //filter index rows that dont contain query words
        for(int i = 0; i < queryPhone.getTerms().size(); i++){
            String term = queryPhone.getTerms().get(i);
            IndexRow indexRow = indexRows.get(term);
            filteredIndexRows.addElement(indexRow);
        }

        for(int i = 0; i < filteredIndexRows.size(); i++) {
            List<TermData> postings = filteredIndexRows.elementAt(i).getPosting();
            for(int j = 0; j < postings.size(); j++) {
                //get current document
                //Double oldScore = results.get(queryPhone);
                if(TFIDF_RANKING) {
                    //results.replace(queryPhone, oldScore + 1);
                } else {
                    //results.replace(queryPhone, oldScore + 1);
                }
            }
        }

        return results;
    }


    public static void main(String[] args) {
        QueryEvaluation q = new QueryEvaluation();
        ArrayList<String> c = new ArrayList<>();
        c.add("4G");
        c.add("WiFi");
        HashMap<Smartphone, Double> r = q.documentRetrieval(new Query(new Smartphone("samsung galaxy 4s", 3000.0, 1000.0, "Android", c)));
        System.out.println(r.size() + " resultados");

        Iterator it = r.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<Smartphone, Double> pair = (Map.Entry<Smartphone, Double>) it.next();
            System.out.println(pair.getKey().getNome());
            System.out.println(pair.getValue());
            System.out.println();
        }
    }
}
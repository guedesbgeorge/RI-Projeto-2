package queryprocessing;

import invertedList.BuildInvertedList;
import invertedList.IndexRow;
import invertedList.InvertedIndex;
import invertedList.TermData;
import model.Smartphone;
import sun.tools.tree.DoubleExpression;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by ianmanor on 28/06/17.
 */
public class QueryEvaluation {
    private InvertedIndex invertedIndex;
    private int numFiles;
    private int[] tamCSVs;

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
        if(DOCUMENT_AT_ATIME) {
            results = documentRetrieval(queryPhone);
        } else {
            results = termRetrieval(queryPhone);
        }
        return results;
    }

    public HashMap<Smartphone, Double> documentRetrieval(Smartphone queryPhone) {
        HashMap<Smartphone, Double> results = new HashMap<>();

        Vector<IndexRow> indexRows = this.invertedIndex.getIndexRows();
        Vector<IndexRow> filteredIndexRows = new Vector<>();

        //filter index rows that dont contain query words
        for(int i = 0; i < indexRows.size(); i++) {
            if(indexRows.elementAt(i).getWord().equals("")) {
                filteredIndexRows.add(indexRows.elementAt(i));
            }
        }

        //loop over documents
        int docID = 0;
        for(int i = 0; i < numFiles; i++) {
            for(int j = 0; j < tamCSVs[i]; j++) {
                double score = 0;
                for(int k = 0; k < filteredIndexRows.size(); k++) {
                    TermData termData = filteredIndexRows.elementAt(k).getTermData();
                    if(termData.getDocumentID() == "docID") {
                        //update document score
                        if(TFIDF_RANKING) {
                            score = score + 1;
                        } else {
                            score = score + 1;
                        }
                    }
                    filteredIndexRows.elementAt(k).movePastDocument();
                }
                docID = docID + 1;

                results.put(queryPhone, new Double(score));
            }
        }

        return results;
    }

    public HashMap<Smartphone, Double> termRetrieval(Smartphone queryPhone) {
        HashMap<Smartphone, Double> results = new HashMap<>();

        Vector<IndexRow> indexRows = this.invertedIndex.getIndexRows();
        Vector<IndexRow> filteredIndexRows = new Vector<>();

        //filter index rows that dont contain query words
        for(int i = 0; i < indexRows.size(); i++) {
            if(indexRows.elementAt(i).getWord().equals("")) {
                filteredIndexRows.add(indexRows.elementAt(i));
            }
        }

        for(int i = 0; i < filteredIndexRows.size(); i++) {
            List<TermData> postings = filteredIndexRows.elementAt(i).getPosting();
            for(int j = 0; j < postings.size(); j++) {
                //get current document
                Double oldScore = results.get(queryPhone);
                if(TFIDF_RANKING) {
                    results.replace(queryPhone, oldScore + 1);
                } else {
                    results.replace(queryPhone, oldScore + 1);
                }
            }
        }

        return results;
    }
}
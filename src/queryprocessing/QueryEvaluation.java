package queryprocessing;

import invertedList.BuildInvertedList;
import invertedList.IndexRow;
import invertedList.InvertedIndex;
import model.Smartphone;

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

    public HashMap<Smartphone, Double> query(Smartphone queryPhone, int k) {
        HashMap<Smartphone, Double> results = new HashMap<>();
        if(DOCUMENT_AT_ATIME) {
            documentRetrieval(k);
        } else {
            termRetrieval(k);
        }

        results.put(queryPhone, 3.0);
        return results;
    }

    public void documentRetrieval(int k) {
        PriorityQueue R = new PriorityQueue(k);
        Vector<IndexRow> indexRows = this.invertedIndex.getIndexRows();

    }

    public void termRetrieval(int k) {
        PriorityQueue R = new PriorityQueue(k);

    }
}
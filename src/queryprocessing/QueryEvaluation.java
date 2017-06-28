package queryprocessing;

import invertedList.InvertedIndex;
import model.Smartphone;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by ianmanor on 28/06/17.
 */
public class QueryEvaluation {
    private InvertedIndex invertedIndex;

    private final boolean TFIDF_RANKING = true;
    private final boolean DOCUMENT_AT_ATIME = true;

    public QueryEvaluation() {

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

    }

    public void termRetrieval(int k) {
        PriorityQueue R = new PriorityQueue(k);

    }
}
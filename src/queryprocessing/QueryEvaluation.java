package queryprocessing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    private ArrayList<Smartphone> bancoSmartphones;
    private double[] scoreArray;

    private boolean TFIDF_RANKING = true;
    private boolean DOCUMENT_AT_ATIME = true;

    public double[] getScoreArray() {
        double[] returnArray = this.scoreArray.clone();
        this.scoreArray = new double[bancoSmartphones.size()];
        return returnArray;
    }

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
            this.scoreArray = new double[this.bancoSmartphones.size()];
            this.invertedIndex = bil.getInvertedIndex();
            for(int i = 1; i <= bancoSmartphones.size(); i++) {
                bancoSmartphones.get(i-1).setCorrelationID(i);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
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

        //filter index rows that don't contain query words
        for(int i = 0; i < queryPhone.getTerms().size(); i++){
            String term = queryPhone.getTerms().get(i);
            IndexRow indexRow = indexRows.get(term);
            System.out.println(i);
            System.out.println(term);
            if(indexRow != null) {
                filteredIndexRows.addElement(indexRow);
            }
        }

        System.out.println("tamanho lista = " + filteredIndexRows.size());

        //loop over documents
        for(int docID = 0; docID < bancoSmartphones.size(); docID++) {
            double score = 0;
            for(int i = 0; i < filteredIndexRows.size(); i++) {
                IndexRow row = filteredIndexRows.elementAt(i);
                if(row.getPosition() < row.getPosting().size()) {
                    TermData termData = row.getTermData();
                    if(termData.getDocID() == docID) {
                        //update document score
                        if(TFIDF_RANKING) {
                            double df = row.getPosting().size();
                            score = score + termData.getFrequency()/df;
                        } else {
                            score = score + 1;
                        }
                        row.movePastDocument(bancoSmartphones.size());
                    }
                }
            }
            Smartphone doc = bancoSmartphones.get(docID);
            results.put(doc, score);
            scoreArray[docID] = score;
        }

        normalize(results);

        return results;
    }

    private void normalize(HashMap<Smartphone, Double> results) {
        if(!TFIDF_RANKING) {
            Iterator it = results.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<Smartphone, Double> pair = (Map.Entry<Smartphone, Double>) it.next();
                results.replace(pair.getKey(), pair.getValue()/bancoSmartphones.size());
            }
        }
    }

    public HashMap<Smartphone, Double> termRetrieval(Query queryPhone) {
        Double[] scores = new Double[this.bancoSmartphones.size()];
        for(int i = 0; i < scores.length; i++) {
            scores[i] = 0.0;
        }

        HashMap<String, IndexRow> indexRows = this.invertedIndex.getIndexRows();

        Vector<IndexRow> filteredIndexRows = new Vector<>();

        //filter index rows that dont contain query words
        for(int i = 0; i < queryPhone.getTerms().size(); i++){
            String term = queryPhone.getTerms().get(i);
            IndexRow indexRow = indexRows.get(term);
            if(indexRow != null) {
                filteredIndexRows.addElement(indexRow);
            }
        }

        for(int i = 0; i < filteredIndexRows.size(); i++) {
            List<TermData> postings = filteredIndexRows.elementAt(i).getPosting();
            for(int j = 0; j < postings.size(); j++) {
                TermData termData = postings.get(j);
                int docID = termData.getDocID();

                Double oldScore = scores[docID];
                System.out.println("docID " + docID);
                System.out.println("oldScore " + oldScore);
                if(TFIDF_RANKING) {
                    double df = filteredIndexRows.elementAt(i).getPosting().size();
                    scores[docID] = oldScore + termData.getFrequency()/df;
                } else {
                    scores[docID] = oldScore + 1;
                }
            }
        }

        for(int i = 0; i < scores.length; i++)
        {
            scoreArray[i] = scores[i].doubleValue();
        }

        HashMap<Smartphone, Double> results_phones = new HashMap<>();

        for(int i = 0; i < scores.length; i++) {
            results_phones.put(this.bancoSmartphones.get(i), scores[i]);
        }

        //normalizing
        normalize(results_phones);

        return results_phones;
    }

    public void setTFIDF(boolean value) {
        this.TFIDF_RANKING = value;
    }

    public void setDocRetrieval(boolean value) {
        this.DOCUMENT_AT_ATIME = value;
    }

    public static ArrayList<Smartphone> top10(HashMap<Smartphone, Double> r1) {
        List<Smartphone> smartphonesSorted = r1.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        for(int i = 0; i < smartphonesSorted.size(); i++) {
        }

        ArrayList<Smartphone> smartphones = new ArrayList<>();
        for(int i = 0; (i < 10 || i < smartphones.size()); i++) {
            smartphones.add(smartphonesSorted.get(i));
        }
        return smartphones;
    }

    public static void main(String[] args) {

        QueryEvaluation q = new QueryEvaluation();

        q.setTFIDF(true);
        HashMap<Smartphone, Double> r1 = fakeQuery(q);
        double[] array1 = q.getScoreArray();


        q.setTFIDF(false);
        HashMap<Smartphone, Double> r2 = fakeQuery(q);
        double[] array2= q.getScoreArray();

        printArray(array1);
        printArray(array2);

        System.out.println();

        System.out.println("SPEARMAN");
        System.out.println(RankCorrelation.spearman(array1, array2));
        System.out.println("KENDAL_TAU");
        System.out.println(RankCorrelation.kendaltau(array1, array2));
    }

    public static void printArray(double[] array) {
        System.out.print("[");
        for(int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if(i < array.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.print("]");
        System.out.println();
    }

    public static HashMap<Smartphone, Double> fakeQuery(QueryEvaluation q) {
        ArrayList<String> c1 = new ArrayList<>();
        c1.add("4g");
        c1.add("Wifi");
        Smartphone s = new Smartphone("samsung galaxy 4s", 3000.0, 1000.0, "android", c1);

        HashMap<Smartphone, Double> r = q.query(new Smartphone("samsung galaxy 4s touch camera display", 3000.0, 1000.0, "android", c1));

        System.out.println();
        System.out.println(r.size() + " resultados");

        Iterator it = r.entrySet().iterator();

        double total = 0;
        while(it.hasNext()) {
            Map.Entry<Smartphone, Double> pair = (Map.Entry<Smartphone, Double>) it.next();
            if(pair.getValue().doubleValue() > 0) {
                System.out.println(pair.getKey().getNome());
                System.out.println(pair.getKey().getBateriaRange());
                System.out.println(pair.getKey().getSo());
                System.out.println(pair.getKey().getPrecoRange());
                System.out.println(pair.getKey().getConectividades());
                System.out.println(pair.getValue());
                //total = total + pair.getValue();
                System.out.println();
            }
        }
        return r;
    }
}
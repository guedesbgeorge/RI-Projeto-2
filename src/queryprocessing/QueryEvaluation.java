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
    private ArrayList<Smartphone> bancoSmartphones;

    private final boolean TFIDF_RANKING = false;
    private final boolean DOCUMENT_AT_ATIME = false;

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
            for(int i = 0; i < bancoSmartphones.size(); i++) {
                //System.out.println("nome: " + bancoSmartphones.get(i).getNome());
                //System.out.println("preco: " + bancoSmartphones.get(i).getPreco());
                //System.out.println("so: " + bancoSmartphones.get(i).getSo());
                //System.out.println("bateria: " + bancoSmartphones.get(i).getBateria());
                //System.out.println("conectividades: " + bancoSmartphones.get(i).getConectividades());

            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            this.invertedIndex = bil.getInvertedIndex();
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
            //System.out.println();
            //System.out.println("docID: " + docID);
            for(int i = 0; i < filteredIndexRows.size(); i++) {
                IndexRow row = filteredIndexRows.elementAt(i);
                //System.out.println(row.getWord());
                if(row.getPosition() < row.getPosting().size()) {
                    TermData termData = row.getTermData();
                    System.out.println("compared to docID " + termData.getDocID());
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
            //System.out.println("Score: " + score);
        }
        normalize(results);


        return results;
    }

    private void normalize(HashMap<Smartphone, Double> results) {
        //normalizing
        if(!TFIDF_RANKING) {
            Iterator it = results.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<Smartphone, Double> pair = (Map.Entry<Smartphone, Double>) it.next();
                results.replace(pair.getKey(), pair.getValue()/bancoSmartphones.size());
            }
        }
    }

    public HashMap<Smartphone, Double> termRetrieval(Query queryPhone) {
        HashMap<Smartphone, Double> results = new HashMap<>();
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
                Smartphone doc = bancoSmartphones.get(docID);

                Double oldScore = results.get(doc);
                System.out.print(doc);
                System.out.print(oldScore);
                if(TFIDF_RANKING) {
                    double df = filteredIndexRows.elementAt(i).getPosting().size();
                    results.replace(doc, oldScore + termData.getFrequency()/df);
                } else {
                    results.replace(doc, oldScore + 1);
                }
            }
        }

        //normalizing
        normalize(results);

        return results;
    }


    public static void main(String[] args) {
        QueryEvaluation q = new QueryEvaluation();
        ArrayList<String> c = new ArrayList<>();
        c.add("4g");
        c.add("Wifi");
        HashMap<Smartphone, Double> r = q.documentRetrieval(new Query(new Smartphone("samsung galaxy 4s", 3000.0, 1000.0, "android", c)));
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
                total = total + pair.getValue();
                System.out.println();
            }
        }
        System.out.println("Total: "+total);
    }
}
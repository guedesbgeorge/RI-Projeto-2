package queryprocessing;

import model.Smartphone;

import java.util.ArrayList;

/**
 * Created by ianmanor on 29/06/17.
 */
public class Query {
    private ArrayList<String> terms;

    public Query(Smartphone smartphone) {
        this.terms = new ArrayList<>();

        String nome = smartphone.getNome();
        double bateria = smartphone.getBateria();
        
        ArrayList<String> conectividades = smartphone.getConectividades();
        for(int i = 0; i < conectividades.size(); i++) {
            this.terms.add(conectividades.get(i));
        }

        String so = smartphone.getSo();
        this.terms.add(so);

        double preco = smartphone.getPreco();
    }

    public ArrayList<String> getTerms() {
        return this.terms;
    }
}

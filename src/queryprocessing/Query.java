package queryprocessing;

import model.Smartphone;

import java.util.ArrayList;

/**
 * Created by ianmanor on 29/06/17.
 */
public class Query {
    private ArrayList<String> terms;
    private Smartphone sp;
    public Query(Smartphone smartphone) {
        this.sp = smartphone;

        this.terms = new ArrayList<>();

        String nome = smartphone.getNome();

        String bateria = smartphone.getBateriaRange();

        String so = smartphone.getSo();
        //this.terms.add(so);

        String preco = smartphone.getPrecoRange();

        terms.add("Nome."+nome);
        terms.add("Bateria."+bateria);
        terms.add("So."+so);
        terms.add("Preco"+preco);

        ArrayList<String> conectividades = smartphone.getConectividades();
        if(conectividades != null){
            for(int i = 0; i < conectividades.size(); i++) {
                this.terms.add(conectividades.get(i));
            }
        }
    }

    public ArrayList<String> getConectividades(){
        return this.sp.getConectividades();
    }

    public ArrayList<String> getTerms() {
        return this.terms;
    }
}

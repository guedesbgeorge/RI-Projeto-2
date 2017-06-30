package queryprocessing;

import model.Smartphone;

import java.lang.reflect.Array;
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

        ArrayList<String> nomeTerms = getSmartphoneNameTerms(smartphone.getNome());
        String bateriaRange = smartphone.getBateriaRange();
        String so = smartphone.getSo();
        String precoRange = smartphone.getPrecoRange();

        for(int i = 0; i < nomeTerms.size(); i++) {
            terms.add("Nome."+nomeTerms.get(i));
        }
        terms.add(bateriaRange);
        terms.add("OS."+so);
        terms.add(precoRange);

        ArrayList<String> conectividades = smartphone.getConectividades();
        if(conectividades != null){
            for(int i = 0; i < conectividades.size(); i++) {
                this.terms.add("Conexao."+conectividades.get(i));
            }
        }
    }

    public ArrayList<String> getSmartphoneNameTerms(String smartphoneName) {
        ArrayList<String> terms = new ArrayList<>();

        String aux = smartphoneName.replaceAll("-", "");
        String values[] = aux.split(" ");

        for (int i = 0; i < values.length; i++)
        {
            if (!values[i].equals(""))
            {
                terms.add(values[i]);
            }
        }

        return terms;
    }

    public ArrayList<String> getConectividades(){
        return this.sp.getConectividades();
    }

    public ArrayList<String> getTerms() {
        return this.terms;
    }
}

package userinterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MutualInformationCalculator {
	public static void main(String[] args) {
		String csvFile = "searchUI/mutualinformation/nome.csv";
		String saida = "searchUI/mutualinformation/nomeMutualInformation.csv";
        String line = "";
        String cvsSplitBy = "],";
        String firtsSeparator = ";";
        ArrayList<Palavra> palavras = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
            	Palavra palavra = new Palavra();

                // use comma as separator
                String[] linha = line.split(firtsSeparator);
                palavra.setPalavra(linha[0].substring(5, linha[0].length()));
               
                String[] ocorrencias = linha[1].split(cvsSplitBy);
                palavra.setFrequenciaGeral(Integer.parseInt(ocorrencias[0].split(Pattern.quote("["))[0]));
             
                
                for(int i = 0; i < ocorrencias.length; i++) {
                	int doc;
                	String[] spl = ocorrencias[i].split(Pattern.quote(","));
                	doc = Integer.parseInt(spl[2].split(Pattern.quote("]"))[0]);
                	
                	if(!palavra.getDocumentos().contains(doc)) {
                		palavra.getDocumentos().add(doc);
                	}
                }
                
                //System.out.println("Palavra: " + palavra.getPalavra() + "  Frequencia geral: " + palavra.getFrequenciaGeral() + "   Qnt documentos: " + palavra.getDocumentos().size());
                palavras.add(palavra);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        HashMap<Tuple<Palavra, Palavra>, Double> dict = new HashMap<>();
        for(int i = 0; i < palavras.size(); i++) {
        	for(int j = i + 1; j < palavras.size(); j++) {
        		Palavra p1 = palavras.get(i);
        		Palavra p2 = palavras.get(j);
        		
        		int comum = 0;
        		for(int k = 0; k < p1.getDocumentos().size(); k++) {
        			if(p2.getDocumentos().contains(p1.getDocumentos().get(k))) {
        				comum++;
        			}
        		}
        		
        		System.out.println("Comum: " + comum + "  Mult: " + p1.getFrequenciaGeral()*p2.getFrequenciaGeral());
        		
        		if(comum > 0) {
            		
            		dict.put(new Tuple<Palavra, Palavra>(p1, p2), (double) p1.getFrequenciaGeral()*p2.getFrequenciaGeral()/10000);
        		}
        	}
        }
		try {
			PrintWriter pw = new PrintWriter(new File(saida));
	        StringBuilder sb = new StringBuilder();
	        ArrayList<Tuple<Palavra, Palavra>> comb = (ArrayList<Tuple<Palavra, Palavra>>) dict.entrySet().stream()
														.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
														.map(Map.Entry::getKey)
														.collect(Collectors.toList());
	        
	        for(int i = 0; i < comb.size(); i++) {
	        	sb.append(comb.get(i).x.getPalavra());
	        	sb.append(';');
	        	sb.append(comb.get(i).y.getPalavra());
	        	sb.append(';');
	        	sb.append(dict.get(comb.get(i)));
	        	sb.append('\n');
	        }
	     
	        pw.write(sb.toString());
	        pw.close();
	        System.out.println("done!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static final double log2(float f)
	{
	    return Math.floor(Math.log(f)/Math.log(2.0));
	}
}

class Palavra {
	String palavra;
	ArrayList<Integer> documentos;
	int frequenciaGeral;
	
	public Palavra() {
		this.palavra = null;
		this.documentos = new ArrayList<>();
		this.frequenciaGeral = 0;
	}
	
	public Palavra(String palavra, ArrayList<Integer> documentos, int frequenciaGeral) {
		this.palavra = palavra;
		this.documentos = documentos;
		this.frequenciaGeral = frequenciaGeral;
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public ArrayList<Integer> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(ArrayList<Integer> documentos) {
		this.documentos = documentos;
	}

	public int getFrequenciaGeral() {
		return frequenciaGeral;
	}

	public void setFrequenciaGeral(int frequenciaGeral) {
		this.frequenciaGeral = frequenciaGeral;
	}
}

class Tuple<X, Y> { 
	  public final X x; 
	  public final Y y; 
	  public Tuple(X x, Y y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	} 
package userinterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MutualInformationCalculator {
	public static void main(String[] args) {
		String csvFile = "invertedIndex/nome.csv";
        String line = "";
        String cvsSplitBy = ",";
        String firtsSeparator = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] linha = line.split(firtsSeparator);
               
                System.out.println("Atributo: " + linha[0]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}

class Palavra {
	String palavra;
	ArrayList<Integer> documentos;
	int frequenciaGeral;
	
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
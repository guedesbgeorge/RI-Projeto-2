package userinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSArray;

import model.Smartphone;
import queryprocessing.QueryEvaluation;

public class SearchInterface {
	private Browser browser;
	
	public SearchInterface(Browser browser) {
		this.browser = browser;
	}
	
	public void pesquisar(String nome, String preco, String bateria, String so, JSArray conectividade) {
		System.out.println("Nome: " + nome);
		System.out.println("Preço: R$ " + preco);
		System.out.println("Bateria (mAh): " + bateria);
		System.out.println("Sistema Operacional: " + so);
		
		ArrayList<String> conectividades = new ArrayList<>();
		String conect = "";
		for (int i = 0; i < conectividade.length(); i++) {
			conectividades.add(conectividade.get(i).getStringValue());
			conect += conectividades.get(i);
			
			if(i < conectividade.length() - 1)
				conect += ", ";
		}
		System.out.println("Conectividade: " + conect);


		Smartphone queryPhone = new Smartphone(nome, Double.parseDouble(preco), Double.parseDouble(bateria), so, conectividades);
		QueryEvaluation queryEvaluation = new QueryEvaluation();
		HashMap<Smartphone, Double> results = queryEvaluation.query(queryPhone);

		fillResults(results);
	}
	
	public void fillResults(HashMap<Smartphone, Double> results) {
		List<Smartphone> smartphones = results.entrySet().stream()
			    						.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
			 							.map(Map.Entry::getKey)
			 							.collect(Collectors.toList());
		browser.executeJavaScript("fillResults(" + smartphoneListToString(smartphones) + ")");
	}
	
	private String smartphoneListToString(List<Smartphone> smartphones) {
		String list = "[";
		for(int i = 0; i < smartphones.size(); i++) {
			Smartphone smartphone = smartphones.get(i);
			list += "['" + smartphone.getNome() + "', '" + smartphone.getPreco() + "', '" + smartphone.getBateria() + "', '" + smartphone.getSo() + "', ";
			List<String> conectividades = smartphone.getConectividades();
			list += "['";
			for(int j = 0; j < conectividades.size(); j++) {
				list += conectividades.get(j);
				
				if(j < conectividades.size() - 1)
					list += "', '";
			}
			list += "']]";
			
			if(i < smartphones.size() - 1)
				list += ", ";
		}
		list += "]";
		return list;
	}
}

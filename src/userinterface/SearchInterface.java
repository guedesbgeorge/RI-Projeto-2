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
	
	public void pesquisar(String nome, String precoRange, String bateriaRange, String so, JSArray conectividade) {
		System.out.println("Nome: " + nome);
		System.out.println("Preço: R$ " + precoRange);
		System.out.println("Bateria (mAh): " + bateriaRange);
		System.out.println("Sistema Operacional: " + so);
		
		ArrayList<String> conectividades = new ArrayList<>();
		String conect = "";
		for (int i = 0; i < conectividade.length(); i++) {
			if(!(conectividade.get(i).getStringValue().equals("NFC") || conectividade.get(i).getStringValue().equals("Bluetooth"))) {
				conectividades.add(conectividade.get(i).getStringValue().toLowerCase());
			} else if(conectividade.get(i).getStringValue().equals("Wifi")) {
				conectividades.add("Wifi");
			} else {
				conectividades.add(conectividade.get(i).getStringValue());
			}
			conect += conectividades.get(i);
			
			if(i < conectividade.length() - 1)
				conect += ", ";
		}
		System.out.println("Conectividade: " + conect);


		Smartphone queryPhone = new Smartphone(nome, precoRange, bateriaRange, so.toLowerCase(), conectividades);
		QueryEvaluation queryEvaluation = new QueryEvaluation();
		HashMap<Smartphone, Double> results = queryEvaluation.query(queryPhone);
		//HashMap<Smartphone, Double> results = new HashMap<>();

		//results.put(queryPhone, 3.0);

				fillResults(results);
	}
	
	public void fillResults(HashMap<Smartphone, Double> results) {
		System.out.println(results.size());
		List<Smartphone> smartphones = results.entrySet().stream()
			    						.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
			 							.map(Map.Entry::getKey)
			 							.collect(Collectors.toList());
		System.out.println(smartphones.size());
		for(int i = 0; i < smartphones.size(); i++) {
			System.out.println(smartphones.get(i));
		}
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

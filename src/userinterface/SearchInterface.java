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
	private QueryEvaluation queryEvaluation;
	
	public SearchInterface(Browser browser) {
		this.browser = browser;
		this.queryEvaluation = new QueryEvaluation();
		System.out.println("new query eval class");
	}
	
	public void pesquisar(String nome, String precoRange, String bateriaRange, String so, JSArray conectividade) {
		System.out.println("---QUERY---");
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

		//ArrayList<String> c = new ArrayList<>();
		//c.add("4g");
		//c.add("Wifi");
		//Smartphone queryPhone = new Smartphone("samsung galaxy 4s", 3000.0, 1000.0, "android", c);
		Smartphone queryPhone = new Smartphone(nome, precoRange, bateriaRange, so.toLowerCase(), conectividades);

		HashMap<Smartphone, Double> results = queryEvaluation.query(queryPhone);
		//HashMap<Smartphone, Double> results = new HashMap<>();

		//results.put(queryPhone, 3.0);

				fillResults(results);
	}
	
	public void fillResults(HashMap<Smartphone, Double> results) {
		System.out.println("NUMERO RESULTADOS" + results.size());
		List<Smartphone> smartphonesSorted = results.entrySet().stream()
			    						.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
			 							.map(Map.Entry::getKey)
			 							.collect(Collectors.toList());
		//System.out.println(smartphonesSorted.size());
		for(int i = 0; i < smartphonesSorted.size(); i++) {
			//System.out.println(smartphonesSorted.get(i));
		}

		ArrayList<Smartphone> smartphones = new ArrayList<>();
		for(int i = 0; (i < 10 || i < smartphones.size()); i++) {
			//System.out.println("RESULTADO " + i);
			smartphones.add(smartphonesSorted.get(i));

			//System.out.println(smartphones.get(i));
		}

		try {
			smartphoneListToString(smartphones);
		}catch (Exception e) {
			System.out.println("erro");
		}

		browser.executeJavaScript("fillResults(" + smartphoneListToString(smartphones) + ")");
	}
	
	private String smartphoneListToString(List<Smartphone> smartphones) {
		String list = "[";


		//System.out.println("SIZE");
		//System.out.println(smartphones.size());

		for(int i = 0; i < smartphones.size(); i++) {

			//System.out.println(i);

			Smartphone smartphone = smartphones.get(i);
			//System.out.println(smartphone);
			String precoString = smartphone.getPrecoRange().substring(6, smartphone.getPrecoRange().length()-1);
			String bateriaString = smartphone.getBateriaRange();
			//System.out.println(bateriaString);
			if(!bateriaString.equals("invalido")) {
				bateriaString = bateriaString.substring(8, smartphone.getBateriaRange().length()-1);
			}
			//System.out.println(bateriaString);
			list += "['" + smartphone.getNome().replace("\'", "") + "', '" + precoString + "', '" + bateriaString + "', '" + smartphone.getSo() + "', ";
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
		//System.out.println("LISTA");
		//System.out.println(list);
		return list;
	}
}

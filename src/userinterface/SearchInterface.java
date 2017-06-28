package userinterface;

import java.util.ArrayList;
import java.util.HashMap;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSArray;

import model.Smartphone;

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
		
		HashMap<Smartphone, Double> results = new HashMap<>();
		results.put(new Smartphone(nome, Double.parseDouble(preco), Double.parseDouble(bateria), so, conectividades), 3.0);
		
		fillResults(results);
	}
	
	public void fillResults(HashMap<Smartphone, Double> results) {
		browser.executeJavaScript("changeButtonTitle('Worked from eclipse!')");
	}
}

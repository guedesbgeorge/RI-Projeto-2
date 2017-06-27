package userinterface;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSArray;

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
		
		String conect = "";
		for (int i = 0; i < conectividade.length(); i++) {
			conect += conectividade.get(i).getStringValue();
			
			if(i < conectividade.length() - 1)
				conect += ", ";
		}
		System.out.println("Conectividade: " + conect);
		
		browser.executeJavaScript("changeButtonTitle('Worked from eclipse!')");
	}
}

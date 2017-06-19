package userinterface;

import com.teamdev.jxbrowser.chromium.Browser;

public class SearchInterface {
	private Browser browser;
	
	public SearchInterface(Browser browser) {
		this.browser = browser;
	}
	public void print(String message) {
		System.out.println(message);
		browser.executeJavaScript("changeButtonTitle('" + message + " from eclipse')");
	}
}

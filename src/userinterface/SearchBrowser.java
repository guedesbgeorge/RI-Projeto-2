package userinterface;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class SearchBrowser {

	public static void main(String[] args) {
		Browser browser = new Browser();
		BrowserView browserView = new BrowserView(browser);
		JFrame frame = new JFrame("Pesquisar smartphones");
		frame.add(browserView, BorderLayout.CENTER);
		frame.setSize(1300, 700);
		frame.setVisible(true);
		File file = new File("searchUI/search.html");
		browser.loadURL("file://" + file.getAbsolutePath());
		browser.addScriptContextListener(new ScriptContextAdapter() {
			@Override
			public void onScriptContextCreated(ScriptContextEvent event) {
				Browser browser = event.getBrowser();
				JSValue window = browser.executeJavaScriptAndReturnValue("window");
				window.asObject().setProperty("java", new SearchInterface(browser));
			}
		});
	}
}

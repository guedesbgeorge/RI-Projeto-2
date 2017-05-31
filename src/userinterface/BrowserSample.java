package userinterface;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class BrowserSample {
   public static void main(String[] args) {
       Browser browser = new Browser();
       BrowserView browserView = new BrowserView(browser);
       JFrame frame = new JFrame("JxBrowser");
       frame.add(browserView, BorderLayout.CENTER);
       frame.setSize(1100, 700);
       frame.setVisible(true);
       browser.loadURL("http://www.google.com");
   }
}

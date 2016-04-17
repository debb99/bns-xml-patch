import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PatcherMain {
	public static void main(String[] args) {
		JFrame myFrame = new Patcher("Blade & Soul XML Patcher");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	    Image image = Toolkit.getDefaultToolkit().getImage(PatcherMain.class.getResource("/res/logo.png"));
	    myFrame.setIconImage(image);
		myFrame.setSize(400, 250);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		myFrame.setVisible(true);
	}
}

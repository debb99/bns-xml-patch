import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Settings extends JFrame {
	private static final long serialVersionUID = 1L;
	Font font1 = new Font(Font.DIALOG, Font.PLAIN, 20);
	Font font2 = new Font(Font.DIALOG, Font.PLAIN, 12);
	JFileChooser chooser;
	FileDialog dialog;
	JTextField pathTo;
	static Path ncroot;

	public Settings(String title) {
		super(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/settings.png")));
		setSize(400, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		Path userdir = Paths.get("C:");
		ncroot = Paths.get(userdir + "/Program Files (x86)" + "/NCJapan");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chooser = new JFileChooser(userdir + "/Program Files (x86)");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("Open Blade & Soul root directory");

		JPanel toPanel = new JPanel(new GridLayout(4, 1));
		JLabel labelTo = new JLabel("Path to NCJapan folder:");
		JLabel note = new JLabel("root folder of Blade & Soul installation containing \\bin\\Client.exe");
		note.setHorizontalAlignment(JLabel.CENTER);
		note.setFont(new Font(Font.DIALOG, Font.ITALIC, 12));
		labelTo.setFont(font1);
		labelTo.setHorizontalAlignment(JLabel.CENTER);

		pathTo = new JTextField(ncroot.toString());
		pathTo.setEditable(false);
		pathTo.setFont(font2);

		JButton browseTo = new JButton("Browse...");
		browseTo.addActionListener(new Listener());
		browseTo.setFont(font1);

		toPanel.add(labelTo);
		toPanel.add(note);
		toPanel.add(pathTo);
		toPanel.add(browseTo);

		add(toPanel);
	}

	class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (actionCommand.equals("Browse...")) {
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					ncroot = Paths.get(chooser.getSelectedFile().toString());
				}
				pathTo.setText(ncroot.toString());
			}
		}
	}

}

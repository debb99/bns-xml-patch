import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.chrono.JapaneseChronology;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Patcher extends JFrame {
	static{
		System.loadLibrary("movefile");
	}
	
	private static final long serialVersionUID = 1L;
	JPanel mainPanel;
	JPanel northPanel;
	JPanel southPanel;
	Font font1 = new Font(Font.DIALOG, Font.PLAIN, 20);
	Font font2 = new Font(Font.DIALOG, Font.PLAIN, 12);
	Settings settingsFrame = new Settings("Settings");
	JFileChooser chooser;
	JTextField pathFrom;
	static Path englishXML;

	public Patcher(String title) {
		super(title);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		String userDir = System.getProperty("user.home");
		chooser = new JFileChooser(userDir + "/Downloads");
		FileNameExtensionFilter datfilter = new FileNameExtensionFilter("dat files (*.dat)", "dat");
		chooser.setDialogTitle("Open English xml.dat");
		// set selected filter
		chooser.setFileFilter(datfilter);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			englishXML = Paths.get(chooser.getSelectedFile().toString());
		}

		mainPanel = new JPanel(new BorderLayout());
		northPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel();
		Listener listener = new Listener();

		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		JMenu file = new JMenu("File");
		menubar.add(file);
		JMenuItem settings = new JMenuItem("Settings");
		settings.addActionListener(listener);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(listener);
		file.add(settings);
		file.add(about);

		JPanel fromPanel = new JPanel(new GridLayout(3, 1, 15, 15));
		JLabel labelFrom = new JLabel("Path to English xml.dat:");
		labelFrom.setFont(font1);
		labelFrom.setHorizontalAlignment(JLabel.CENTER);

		pathFrom = new JTextField(englishXML.toString());
		pathFrom.setEditable(false);
		pathFrom.setFont(font2);

		JButton browseFrom = new JButton("Browse...");
		browseFrom.addActionListener(listener);
		browseFrom.setFont(font1);

		fromPanel.add(labelFrom);
		fromPanel.add(pathFrom);
		fromPanel.add(browseFrom);

		northPanel.add(fromPanel);

		JButton translateButton = new JButton("Translate XML");
		translateButton.setFont(font1);
		translateButton.addActionListener(listener);
		southPanel.add(translateButton);

		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		add(mainPanel);
	}

	public native void moveFile(String src, String dest);

	class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (actionCommand.equals("Settings")) {
				settingsFrame.setVisible(true);
			}
			if (actionCommand.equals("About")) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/res/bladeandsoul.png"));
				JOptionPane.showMessageDialog(settingsFrame, "Blade & Soul XML Patcher" + "\nby Debashish Biswas",
						"About", JOptionPane.PLAIN_MESSAGE, icon);
			}
			if (actionCommand.equals("Browse...")) {
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					englishXML = Paths.get(chooser.getSelectedFile().toString());
				}
				pathFrom.setText(englishXML.toString());
			}
			if (actionCommand.equals("Translate XML")) {
				boolean completed = false;
				try {
					 Path desktop = Paths.get(System.getProperty("user.home")
					 + "/Desktop" + "/newFile.txt");
//					 Path progFiles = Paths.get(Settings.ncroot + "");
					Path japaneseXML = Paths.get(
							Settings.ncroot + "/contents" + "/Local" + "/NCJAPAN" + "/JAPANESE" + "/data" + "/xml.dat");
//					System.out.println(japaneseXML.toUri().getPath());
//					moveFile(englishXML.toString(), japaneseXML.toString());
					Files.copy(englishXML, japaneseXML, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
					completed = true;
				} catch (Exception e1) {
					completed = false;
					JOptionPane.showMessageDialog(null,
							"There was an error transferring the file:\n" + e1.getClass().getTypeName(),
							"Error", JOptionPane.ERROR_MESSAGE);
//					e1.printStackTrace();
				} finally {
					if (completed) {
						JOptionPane.showMessageDialog(null, "xml.dat transferred successfully!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}
}

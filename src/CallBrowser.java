import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CallBrowser {

	private JFrame frmCallTreeBrowser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: callbrowser input");
			System.exit(1);
		}
		String fileName = args[0];
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {
			System.err.println("Error: " + e.getLocalizedMessage());
		}
		catch (IllegalAccessException e) {
			System.err.println("Error: " + e.getLocalizedMessage());
		}
		catch (InstantiationException e) {
			System.err.println("Error: " + e.getLocalizedMessage());
		}
		catch (ClassNotFoundException e) {
			System.err.println("Error: " + e.getLocalizedMessage());
		}
		
		class UiRunner implements Runnable{
			String fileName;
			UiRunner(String fileName) {
				this.fileName = fileName;
			}
		
			public void run() {
				CallNode callTree = null;
				try {
					BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
					callTree = CallNode.buildTree(DotReader.readDot(reader));
					try {
						CallBrowser window = new CallBrowser(callTree);
						window.frmCallTreeBrowser.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		}
		EventQueue.invokeLater(new UiRunner(fileName));
	}

	/**
	 * Create the application.
	 */
	public CallBrowser(CallNode callTree) {
		initialize(callTree);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize(DefaultMutableTreeNode callTree) {
		frmCallTreeBrowser = new JFrame();
		frmCallTreeBrowser.setTitle("Call Tree Browser");
		frmCallTreeBrowser.setBounds(100, 100, 450, 300);
		frmCallTreeBrowser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCallTreeBrowser.getContentPane().setLayout(new BoxLayout(frmCallTreeBrowser.getContentPane(), BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		frmCallTreeBrowser.getContentPane().add(scrollPane);
		
		JTree tree = new JTree();
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(callTree));
		tree.putClientProperty("JTree.lineStyle", "Angled");
		scrollPane.setViewportView(tree);
		
		JMenuBar menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setMnemonic(KeyEvent.VK_X);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setMnemonic(KeyEvent.VK_A);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmCallTreeBrowser, "\u00a9 2016 Mikael Ågren. \nLicense: GPLv3 \nsee https://gnu.org/licenses/gpl.html");
			}
		});
		mnHelp.add(mntmAbout);
	}
}

// This file should be assessed for Stage 3
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;

public class WebBrowserGUI extends JFrame implements HyperlinkListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension appSize = null;
	private Dimension screenSize = null;
	private URL url = null;
	private WebIndex webIndex = new WebIndex();
	private WebBrowserToolBar toolBar;
	private QuerySection querySection;
	private JEditorPane jEditorPane;
	private HistoryRecord historyRecord;
	
	public WebBrowserGUI() {		
		
		// general settings
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.screenSize = toolkit.getScreenSize();
		this.appSize = new Dimension((int) (screenSize.width * 0.8),
				(int) (screenSize.height * 0.8));
		setSize(appSize);
		setLocation(new Point((screenSize.width - appSize.width) / 2,
				(screenSize.height - appSize.height) / 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("Web Browser");
		setLayout(new BorderLayout());

		// content pane
		Container contentPane = getContentPane();

		// toolbar
		 toolBar = new WebBrowserToolBar();
		
		// query section
		 querySection = new QuerySection(appSize);

		// editor pane
		jEditorPane = new JEditorPane();
		jEditorPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
		jEditorPane.setEditable(false);
		jEditorPane.addHyperlinkListener(this);
		
		// history record
		 historyRecord = new HistoryRecord();
		
		try {
			url = new URL("http://www.ngweb.org/Java/Java_7-2-1.html");
			jEditorPane.setPage(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// scroll pane
		JScrollPane jScrollPane = new JScrollPane(jEditorPane);

		// actions
		URLFieldAction urlFieldAction = new URLFieldAction(this);
		toolBar.getUrlField().addActionListener(urlFieldAction);
		
		QueryAction queryAction = new QueryAction(this);
		querySection.getQueryEntryField().addActionListener(queryAction);
		
		ForwardAction forwardAction = new ForwardAction(this);
		toolBar.getForwardButton().addActionListener(forwardAction);
		
		BackwardAction backwardAction = new BackwardAction(this);
		toolBar.getBackButton().addActionListener(backwardAction);
		
		// add to content pane
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(jScrollPane, BorderLayout.CENTER);
		contentPane.add(querySection, BorderLayout.SOUTH);
		contentPane.add(historyRecord, BorderLayout.EAST);
		System.out.println(jEditorPane.getSize());

	}

	public WebBrowserToolBar getToolBar() {
		return toolBar;
	}
	
	public QuerySection getQuerySection() {
		return querySection;
	}
	
	public HistoryRecord getHistoryRecord() {
		return historyRecord;
	}
	
	public JEditorPane getEditorPane() {
		return jEditorPane;
	}
	
	public WebIndex getWebIndex() {
		return webIndex;
	}
	
	private class URLFieldAction extends AbstractAction {
		private JEditorPane editorPane = null;
		private HistoryRecord historyRecord = null;
		private WebIndex webIndex = null;
		private List<String> historyList = null;
		private Integer historyCursor = 0;
		private WebBrowserGUI mainGUI = null;
		public URLFieldAction (WebBrowserGUI mainGUI) {
			this.mainGUI = mainGUI;
			this.editorPane = mainGUI.getEditorPane();
			this.historyRecord = mainGUI.getHistoryRecord();
			this.webIndex = mainGUI.getWebIndex();
			this.historyCursor = this.historyRecord.getHistoryCursor();
			this.historyList = mainGUI.getHistoryRecord().getHistoryList();
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			JTextField urlField = null;
			this.historyCursor = this.historyRecord.getHistoryCursor();
			if (event.getSource() instanceof JTextField) {
				urlField = (JTextField)event.getSource();
			}
			if (urlField != null) {
				URL url = null;
				String urlString = urlField.getText().trim();
				boolean loaded = false;
				try {
					
					if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
						urlString = "http://" + urlString;
					}
					url = new URL(urlString);
					editorPane.setDocument(new HTMLDocument());
					editorPane.setPage(url);
					urlField.setText(urlString);
					loaded = true;
				} catch (Exception e) {
					e.printStackTrace();
					editorPane.setText("Unable to dispaly the url page.");
					loaded = false;
				}
				if (historyCursor != historyList.size() - 1 && historyList.size() != 0) {
					historyList.subList(historyCursor + 1, historyList.size()).clear();
				}
				this.historyList.add(urlString);
				this.historyRecord.setHistoryCursor(this.historyRecord.getHistoryList().size() - 1);
				JLabel urlLabel = new JLabel(urlString);
				
				urlLabel.setFont(urlLabel.getFont().deriveFont(16.0f));
				urlLabel.addMouseListener(new ClickAction(mainGUI));
				urlLabel.setName(urlString);
				this.historyRecord.add(urlLabel);
				this.historyRecord.validate();
				if (loaded) {
					try {
						this.webIndex.add(new WebDoc(urlString));
						System.out.println(this.webIndex);
					} catch (Exception e) {
						System.err.println("failed to init webdoc");
					}
				}
			}
		}
		
	}
	
	private class QueryAction extends AbstractAction {
		private JPanel resultJTextArea = null;
		private WebIndex webIndex = null;
		private WebBrowserGUI mainGUI = null;
		public QueryAction(WebBrowserGUI mainGUI) {
			this.resultJTextArea = mainGUI.getQuerySection().getQueryResultArea();
			this.webIndex = mainGUI.getWebIndex();
			this.mainGUI = mainGUI;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			JTextField queryEntry = null;
			if (event.getSource() instanceof JTextField) {
				queryEntry = (JTextField)event.getSource();
			}
			if (queryEntry != null) {
				try {
					resultJTextArea.removeAll();
					Query query = QueryBuilder.parse(queryEntry.getText());
					System.out.println(query);
					Set<WebDoc> webDocs = query.matches(this.webIndex);
					System.out.println(webDocs);
					if (webDocs.isEmpty()) {
						this.resultJTextArea.add(new JLabel("No matched result."));
						this.resultJTextArea.validate();
					} else {
						for (WebDoc webDoc : webDocs) {
							JLabel webDocLabel = new JLabel(webDoc.toString());
							webDocLabel.addMouseListener(new ClickAction(mainGUI));
							webDocLabel.setName(webDoc.getURL());
							this.resultJTextArea.add(webDocLabel);
							this.resultJTextArea.validate();
						}
						
					}
					
				} catch (BadQueryFormatException e) {
					this.resultJTextArea.add(new JLabel("This is not a correct query."));
				}
				resultJTextArea.revalidate();
				resultJTextArea.repaint();
			}
		}
	}
	
	private class ForwardAction extends AbstractAction {
		private JEditorPane editorPane = null;
		private List<String> historyList = null;
		private HistoryRecord historyRecord = null;
		private JTextField urlTextField = null;
		private Integer historyCursor = 0;
		public ForwardAction(WebBrowserGUI mainGUI) {
			editorPane = mainGUI.getEditorPane();
			historyList = mainGUI.getHistoryRecord().getHistoryList();
			historyRecord = mainGUI.getHistoryRecord();
			urlTextField = mainGUI.getToolBar().getUrlField();
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			historyCursor = historyRecord.getHistoryCursor();
			if (historyCursor != historyList.size() - 1) {
				historyRecord.setHistoryCursor(++historyCursor); 
			}
			System.out.println(historyCursor);
			try {
				urlTextField.setText(historyList.get(historyCursor));
				editorPane.setPage(historyList.get(historyCursor));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				editorPane.setText("Unable to dispaly the url page.");
			}
		}
	}
	
	private class BackwardAction extends AbstractAction {
		private JEditorPane editorPane = null;
		private List<String> historyList = null;
		private HistoryRecord historyRecord = null;
		private JTextField urlTextField = null;
		private Integer historyCursor = 0;
		public BackwardAction(WebBrowserGUI mainGUI) {
			editorPane = mainGUI.getEditorPane();
			historyList = mainGUI.getHistoryRecord().getHistoryList();
			historyRecord = mainGUI.getHistoryRecord();
			urlTextField = mainGUI.getToolBar().getUrlField();
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			historyCursor = historyRecord.getHistoryCursor();
			if (historyCursor != 0) {
				historyRecord.setHistoryCursor(--historyCursor); 
			}
			System.out.println(historyCursor);
			try {
				urlTextField.setText(historyList.get(historyCursor));
				editorPane.setPage(historyList.get(historyCursor));
				
			} catch (IOException e) {
				editorPane.setText("Unable to dispaly the url page.");
			}
		}
	}
	
	private class ClickAction extends MouseAdapter {
		private JEditorPane editorPane = null;
		
		public ClickAction(WebBrowserGUI mainGUI) {
			editorPane = mainGUI.getEditorPane();
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel label = null;
			if (e.getSource() instanceof JLabel) {
				label = (JLabel)e.getSource();
			}
			if (label != null) {
				try {
					editorPane.setPage(label.getName());
				} catch (IOException e1) {
					editorPane.setText("Unable to display the url page.");
				}
			}
			System.out.println("clicked.");
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new WebBrowserGUI().setVisible(true);
			}
		});

	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		// TODO Auto-generated method stub
		boolean loaded = false;
		String urlString = e.getURL().toString();
		try {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				jEditorPane.setPage(e.getURL());
				this.historyRecord.getHistoryList().add(urlString);
				this.historyRecord.setHistoryCursor(this.historyRecord.getHistoryList().size() - 1);
				JLabel urlLabel = new JLabel(urlString);
				
				urlLabel.setFont(urlLabel.getFont().deriveFont(16.0f));
				urlLabel.addMouseListener(new ClickAction(this));
				urlLabel.setName(urlString);
				this.historyRecord.add(urlLabel);
				this.historyRecord.validate();
				loaded = true;
			}
		} catch (Exception e2) {
			jEditorPane.setText("Unable to display the url page.");
			loaded = false;
		}
		if (loaded) {
			try {
				this.webIndex.add(new WebDoc(urlString));
				System.out.println(this.webIndex);
			} catch (Exception e1) {
				System.err.println("failed to init webdoc");
			}
		}
	}

}

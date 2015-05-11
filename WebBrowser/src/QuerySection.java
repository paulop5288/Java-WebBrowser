// This file should be assessed for Stage 3
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class QuerySection extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanelForQueryEntry = new JPanel();
	private JPanel jPanelForQueryResult = new JPanel();
	private JTextField queryEntryField = new JTextField();
	private JPanel queryResultArea = new JPanel();
	private Dimension appSize = null;
	
	public QuerySection(Dimension appSize) {
		this.appSize = appSize;
		init();
	}
	
	public JTextField getQueryEntryField() {
		return queryEntryField;
	}
	
	public JPanel getQueryResultArea() {
		return queryResultArea;
	}
	
	private void init() {
		setLayout(new BorderLayout());
		BoxLayout boxLayout = new BoxLayout(this.queryResultArea, BoxLayout.PAGE_AXIS );
		queryResultArea.setLayout(boxLayout);
		
		JLabel entryLabel = new JLabel("Enter Query");
		this.queryEntryField.setPreferredSize(new Dimension(appSize.width * 3 / 4, 24));
		
		JLabel resuJLabel = new JLabel("Result : ");
		this.queryResultArea.setPreferredSize(new Dimension(appSize.width * 3 / 4, 24 * 6));
		
		this.jPanelForQueryEntry.add(entryLabel);
		this.jPanelForQueryEntry.add(this.queryEntryField);
		
		this.jPanelForQueryResult.add(resuJLabel);
		this.jPanelForQueryResult.add(this.queryResultArea);
		add(this.jPanelForQueryEntry, BorderLayout.NORTH);
		add(this.jPanelForQueryResult, BorderLayout.CENTER);
	}

}

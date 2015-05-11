// This file should be assessed for Stage 3
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HistoryRecord extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> historyList = new ArrayList<String>();
	private Integer historyCursor = 0;
	public HistoryRecord() {
		init();
	}

	public List<String> getHistoryList() {
		return historyList;
	}

	public Integer getHistoryCursor() {
		return historyCursor;
	}

	public void setHistoryCursor(Integer historyCursor) {
		this.historyCursor = historyCursor;
	}
	
	private void init() {
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS );
		setLayout(boxLayout);
		JLabel titleLabel = new JLabel("History record");
		titleLabel.setFont(titleLabel.getFont().deriveFont(16.0f));
        add(titleLabel); 
		this.setPreferredSize(new Dimension(24 * 10, 24 * 20));
	}
}

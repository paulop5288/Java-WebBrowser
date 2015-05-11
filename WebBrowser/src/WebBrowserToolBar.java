// This file should be assessed for Stage 3
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;


public class WebBrowserToolBar extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField urlField = new JTextField();
	private JButton jButtonForBack = null;
	private JButton jButtonForForward = null;
	public WebBrowserToolBar() {
		// TODO Auto-generated constructor stub
		init();
	}

	public WebBrowserToolBar(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public WebBrowserToolBar(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public WebBrowserToolBar(String arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public JTextField getUrlField() {
		return urlField;
	}
	
	public JButton getForwardButton() {
		return jButtonForForward;
	}
	
	public JButton getBackButton() {
		return jButtonForBack;
	}
	
	
	private void init() {
		this.jButtonForBack = new JButton();
		this.jButtonForForward = new JButton();
		try {
		    Image leftImg = ImageIO.read(getClass().getResource("left.png"));
		    jButtonForBack.setIcon(new ImageIcon(leftImg));
		    Image rightImg = ImageIO.read(getClass().getResource("right.png"));
		    jButtonForForward.setIcon(new ImageIcon(rightImg));
		  } catch (IOException ex) {
			  
		  }
		this.add(jButtonForBack);
		this.add(jButtonForForward);
		urlField.setToolTipText("Type here to search");
//		Action action = new AbstractAction() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				//System.out.println(e.getSource());
//				JTextField urlField = null;
//				if (e.getSource() instanceof JTextField) {
//					urlField = (JTextField)e.getSource();
//				}
//				if (urlField != null) {
//					//System.out.println(urlField.getText());
//				}
//			}
//		};
//		urlField.addActionListener(action);
		
		this.add(urlField);
		this.setFloatable(false);
		this.setRollover(true);
	}

}

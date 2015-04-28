package Game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StartMessage extends JPanel {
	
	private JLabel prompt;
	private JButton ok;
	private ButtonListener submitListener;

	public StartMessage() {
		this.setLayout(new GridLayout(2, 1));
		prompt = new JLabel("Kill all enemies to win!");
		ok = new JButton("OK");
		
		submitListener = new ButtonListener();
		ok.addActionListener(submitListener);
		
		this.add(prompt);
		this.add(ok);
		this.setSize(prompt.getPreferredSize().width + 20, this.getPreferredSize().height);
	}
	
	public static void main (String args[]) {
		StartMessage StartMessage = new StartMessage();
		JFrame frame = new JFrame();
		frame.add(StartMessage);
		frame.setSize(StartMessage.getSize());
		frame.setVisible(true);
		while(true) {
			if(!StartMessage.isVisible()) {
				frame.setVisible(false);
				break;
			}
		}
	}
	
	private class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
			
		}
}
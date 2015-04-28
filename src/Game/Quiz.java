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

public class Quiz extends JPanel {

	private JLabel question;
	private JRadioButton a;
	private JRadioButton b;
	private JRadioButton c;
	private JRadioButton d;
	private JButton submit;
	private ButtonListener submitListener;
	private ArrayList<JRadioButton> options;

	public Quiz() {
		this.setLayout(new GridLayout(6, 1));
		question = new JLabel("What angle makes the bullet travel farthest?");
		a = new JRadioButton("45 Degrees");
		b = new JRadioButton("30 Degrees");
		c = new JRadioButton("60 Degrees");
		d = new JRadioButton("90 Degrees");
		submit = new JButton("Submit");
		options = new ArrayList<JRadioButton>();
		
		options.add(a);
		options.add(b);
		options.add(c);
		options.add(d);
		
		ButtonGroup group = new ButtonGroup();
		group.add(a);
		group.add(b);
		group.add(c);
		group.add(d);
		
		a.setSelected(true);
		
		submitListener = new ButtonListener(options);
		submit.addActionListener(submitListener);
		
		this.add(question);
		this.add(a);
		this.add(b);
		this.add(c);
		this.add(d);
		this.add(submit);
		this.setSize(question.getPreferredSize().width + 20, this.getPreferredSize().height);
	}
	
	public static void main (String args[]) {
		Quiz quiz = new Quiz();
		JFrame frame = new JFrame();
		frame.add(quiz);
		frame.setSize(quiz.getSize());
		frame.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener {

		private ArrayList<JRadioButton> options;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(options.get(0).isSelected()) {
				JDialog pane = new JDialog();
				pane.setLayout(new GridLayout(1,1));
				pane.add(new JLabel("YOU WIN!"));
				pane.setSize(50,80);
				pane.setVisible(true);
			}
			else {
				JDialog pane = new JDialog();
				pane.setLayout(new GridLayout(1,1));
				pane.add(new JLabel("Incorrect!"));
				pane.setSize(50,80);
				pane.setVisible(true);
			}
			
		}
		public ButtonListener(ArrayList<JRadioButton> options) {
			this.options = options;
		}	
	}
}
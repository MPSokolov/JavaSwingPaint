import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class CustomRoundRectValueDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JSpinner rWInput;
	private JTextField placeholder1;
	private JTextField placeholder2;
	private JTextField placeholder3;
	private JTextField placeholder4;
	private JLabel rHLabel;
	private JSpinner rHInput;
	private int rW = 50;
	private int rH = 50;
	private RoundPanel previewPanel = new RoundPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CustomRoundRectValueDialog dialog = new CustomRoundRectValueDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CustomRoundRectValueDialog() {
		super((java.awt.Frame) null, true);
		setTitle("Round Rect Value");
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JPanel inputPanel = new JPanel();
			contentPanel.add(inputPanel);
			inputPanel.setLayout(new GridLayout(0, 2, 0, 30));
			{
				placeholder1 = new JTextField();
				placeholder1.setVisible(false);
				inputPanel.add(placeholder1);
				placeholder1.setColumns(10);
			}
			{
				placeholder2 = new JTextField();
				placeholder2.setVisible(false);
				inputPanel.add(placeholder2);
				placeholder2.setColumns(10);
			}
			{
				JLabel rWLabel = new JLabel("Round Width:");
				rWLabel.setHorizontalAlignment(SwingConstants.CENTER);
				inputPanel.add(rWLabel);
			}
			{
				rWInput = new JSpinner();
				rWInput.setModel(new SpinnerNumberModel(Integer.valueOf(50), null, null, Integer.valueOf(1)));
				inputPanel.add(rWInput);
				rWInput.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						rW = (int) rWInput.getValue();
						previewPanel.update(previewPanel.getGraphics());
					}
				});
			}
			{
				placeholder3 = new JTextField();
				placeholder3.setVisible(false);
				{
					rHLabel = new JLabel("Round Height:");
					rHLabel.setHorizontalAlignment(SwingConstants.CENTER);
					inputPanel.add(rHLabel);
					
				}
				{
					rHInput = new JSpinner();
					rHInput.setModel(new SpinnerNumberModel(Integer.valueOf(50), null, null, Integer.valueOf(1)));
					inputPanel.add(rHInput);
					rHInput.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							rH = (int) rHInput.getValue();
							previewPanel.update(previewPanel.getGraphics());
						}
					});
				}
				inputPanel.add(placeholder3);
				placeholder3.setColumns(10);
			}
			{
				placeholder4 = new JTextField();
				placeholder4.setVisible(false);
				inputPanel.add(placeholder4);
				placeholder4.setColumns(10);
			}
		}
		{
			
			contentPanel.add(previewPanel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CustomRoundRectValueDialog.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						rW = 50;
						rH = 50;
						previewPanel.update(previewPanel.getGraphics());
						CustomRoundRectValueDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	class RoundPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawRoundRect(40, 40, getWidth()-70, getHeight()-70, rW, rH);
		}

	}
	
	public int[] getRoundRectValues() {
		return new int[]{rW, rH};
	}

}

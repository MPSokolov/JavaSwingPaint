import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomColorInputDialog extends JDialog {

	private JPanel contentPanel = new JPanel();
	private int redValue = 0;
	private int greenValue = 0;
	private int blueValue = 0;
	private JTextField placeholder1;
	private JTextField placeholder2;
	private JLabel redV;
	private JSpinner redIF;
	private JLabel greenV;
	private JSpinner greenIF;
	private JLabel blueV;
	private JSpinner blueIF;
	private JTextField placeholder3;
	private JTextField placeholder4;
	JPanel previewPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CustomColorInputDialog dialog = new CustomColorInputDialog();
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
	public CustomColorInputDialog() {
		super((java.awt.Frame) null, true);
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		setTitle("Color Picker");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JPanel inputPanel = new JPanel();
			contentPanel.add(inputPanel);
			inputPanel.setLayout(new GridLayout(5, 2, 0, 10));
			{

				placeholder1 = new JTextField();
				placeholder1.setVisible(false);
				inputPanel.add(placeholder1);
				placeholder1.setColumns(10);

				placeholder2 = new JTextField();
				placeholder2.setVisible(false);
				inputPanel.add(placeholder2);

				redV = new JLabel("Red Value:");
				inputPanel.add(redV);

			}

			redIF = new JSpinner();
			redIF.setModel(new SpinnerNumberModel(0, 0, 255, 1));
			inputPanel.add(redIF);
			redIF.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					redValue = (int) redIF.getValue();
					previewPanel.setBackground(new Color(redValue, greenValue, blueValue));
				}
			});

			greenV = new JLabel("Green Value:");
			inputPanel.add(greenV);

			greenIF = new JSpinner();
			greenIF.setModel(new SpinnerNumberModel(0, 0, 255, 1));
			inputPanel.add(greenIF);
			greenIF.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					greenValue = (int) greenIF.getValue();
					previewPanel.setBackground(new Color(redValue, greenValue, blueValue));
				}
			});

			blueV = new JLabel("Blue Value:");
			inputPanel.add(blueV);

			blueIF = new JSpinner();
			blueIF.setModel(new SpinnerNumberModel(0, 0, 255, 1));
			inputPanel.add(blueIF);
			blueIF.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					blueValue = (int) blueIF.getValue();
					previewPanel.setBackground(new Color(redValue, greenValue, blueValue));

				}
			});

			placeholder3 = new JTextField();
			placeholder3.setVisible(false);
			inputPanel.add(placeholder3);
			placeholder3.setColumns(10);

			placeholder4 = new JTextField();
			placeholder4.setVisible(false);
			inputPanel.add(placeholder4);
			placeholder4.setColumns(10);
			{

				previewPanel.setBackground(new Color(redValue, greenValue, blueValue));
				previewPanel.setBorder(new CompoundBorder(null, new LineBorder(contentPanel.getBackground(), 28)));
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
							CustomColorInputDialog.this.dispose();
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
//				getRootPane().setDefaultButton(okButton);
				}
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							redValue = 0;
							greenValue = 0;
							blueValue = 0;
							previewPanel.setBackground(new Color(redValue, greenValue, blueValue));
							CustomColorInputDialog.this.dispose();
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			}
		}

	}
	
	public Color getCustomColor() {
	    return previewPanel.getBackground();
	}
	
}

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JCheckBox;

public class Test2_2 extends JFrame {

	private JPanel contentPane;
	ArrayList<ArrayList<Integer>> x = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> y = new ArrayList<ArrayList<Integer>>();
	ArrayList<PolygonsPanel> polygonPanels = new ArrayList<>();
	JComboBox shapeBox = new JComboBox();
	JComboBox colorBox = new JComboBox();
	JCheckBox isFilledBtn = new JCheckBox("Filled");
	private final JPanel colorPreview = new JPanel();
	JPanel drawPanel = new JPanel();
	int lastRW = 0;
	int lastRH = 0;

	public static void main(String[] args) {

		Test2_2 frame = new Test2_2();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public Test2_2() {
		setTitle("PaintDEMO");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1300, 700);
		contentPane = new JPanel();
		setContentPane(contentPane);

		/*
		 * *************** DRAW PANEL INIT
		 */
		drawPanel.setBounds(0, 0, 1216, 661);
		contentPane.add(drawPanel);
		drawPanel.setLayout(null);
		x.add(new ArrayList<Integer>());
		y.add(new ArrayList<Integer>());

		/*
		 * **************************** INITIAL DRAWING PANEL INIT
		 */
		PolygonsPanel panel1 = new PolygonsPanel(0);
		drawPanel.add(panel1);
		polygonPanels.add(panel1);

		/*
		 * ***************** COLOR COMBO BOX
		 */

		colorBox.setBounds(1216, 99, 70, 261);
		colorBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changingColor();
				if (colorBox.getSelectedItem() == "Custom") {
					CustomColorInputDialog d = new CustomColorInputDialog();
					d.setVisible(true);
					polygonPanels.get(polygonPanels.size() - 1).changeColor(d.getCustomColor());
				}
				colorPreview.setBackground(polygonPanels.get(polygonPanels.size() - 1).color);
				isFilledBtn.setForeground(new Color(255 - colorPreview.getBackground().getRed(), 
						255 - colorPreview.getBackground().getGreen(), 
						255 - colorPreview.getBackground().getBlue()));
				
				contentPane.repaint();
			}
		});
		contentPane.setLayout(null);
		colorBox.setModel(new DefaultComboBoxModel(new String[] {"Red", "Green", "Blue", "Black", "Cyan", "DGray", "LGray", "Gray", "Magenta", "Pink", "Yellow", "Orange", "Custom"}));
		colorBox.setSelectedIndex(3);
		contentPane.add(colorBox);

		/*
		 * ************* UNDO BTN
		 */
		JButton btnNewButton = new JButton("Undo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPanel.remove(polygonPanels.get(polygonPanels.size() - 2));
				x.set(x.size() - 2, new ArrayList<Integer>());
				y.set(y.size() - 2, new ArrayList<Integer>());
				x.remove(x.size() - 1);
				y.remove(y.size() - 1);
				polygonPanels.remove(polygonPanels.size() - 2);
				polygonPanels.get(x.size() - 1).panelIndex--;
				contentPane.repaint();

				changingType();
				if (shapeBox.getSelectedItem() == "RoundRect") {
					x.get(x.size() - 1).set(2, lastRW);
					y.get(y.size() - 1).set(2, lastRH);
				}

//				System.out.println(x);
//				System.out.println(polygonPanels);

			}
		});
		btnNewButton.setBounds(1216, 42, 68, 55);
		contentPane.add(btnNewButton);

		/*
		 * ***************** SHAPE COMBO BOX
		 */

		shapeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (shapeBox.getSelectedItem() == "Free") {
					x.set(x.size() - 1, new ArrayList<Integer>());
					y.set(y.size() - 1, new ArrayList<Integer>());
				}
				changingType();
				if (shapeBox.getSelectedItem() == "RoundRect") {
					CustomRoundRectValueDialog r = new CustomRoundRectValueDialog();
					r.setVisible(true);
					x.get(x.size() - 1).set(2, r.getRoundRectValues()[0]);
					y.get(y.size() - 1).set(2, r.getRoundRectValues()[1]);
					lastRW = r.getRoundRectValues()[0];
					lastRH = r.getRoundRectValues()[1];
				}

				contentPane.repaint();
			}
		});
		shapeBox.setModel(new DefaultComboBoxModel(new String[] {"Rectangle", "Oval", "Free", "Line", "RoundRect"}));
		shapeBox.setSelectedIndex(2);
		shapeBox.setBounds(1216, 360, 70, 250);
		contentPane.add(shapeBox);
		
		/* ***************
		 * FILLED BTN
		 ***************** */
		FlowLayout flowLayout = (FlowLayout) colorPreview.getLayout();
		flowLayout.setVgap(15);
		colorPreview.setBackground(new Color(0, 0, 0));
		colorPreview.setBounds(1216, 606, 70, 55);
		contentPane.add(colorPreview);
		isFilledBtn.setForeground(new Color(255, 255, 255));
		isFilledBtn.setOpaque(false);
		
		isFilledBtn.setHorizontalAlignment(SwingConstants.CENTER);
		isFilledBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
		isFilledBtn.setBounds(1216, 620, 62, 23);
		colorPreview.add(isFilledBtn);
		isFilledBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changingFill();
			}
		});
		
		/* *************
		 * SAVE BTN
		 *************** */
		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveImage();
			}
		});
		saveBtn.setBounds(1216, 0, 68, 44);
		contentPane.add(saveBtn);
		
		/*
		 * **************************** DRAW PANEL ACTION LISTENERS
		 */
		
		/* ***************
		 * DRAG MOUSE
		 ***************** */
		drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				if (shapeBox.getSelectedItem() == "Free") {
					x.get(x.size() - 1).add(e.getX());
					y.get(y.size() - 1).add(e.getY());
				} else if (shapeBox.getSelectedItem() == "Rectangle" || 
						shapeBox.getSelectedItem() == "Oval" ||
						shapeBox.getSelectedItem() == "RoundRect") {
					x.get(x.size() - 1).set(1, e.getX() - x.get(x.size() - 1).get(0));
					y.get(y.size() - 1).set(1, e.getY() - y.get(y.size() - 1).get(0));
				} else if (shapeBox.getSelectedItem() == "Line") {
					x.get(x.size() - 1).set(1, e.getX());
					y.get(y.size() - 1).set(1, e.getY());
				}
				
				
				contentPane.repaint();
			}
		});
		
		/* ***************
		 * RELEASE MOUSE
		 ***************** */
		drawPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				x.add(new ArrayList<Integer>());
				y.add(new ArrayList<Integer>());
				polygonPanels.add(new PolygonsPanel(x.size() - 1));
				drawPanel.add(polygonPanels.get(polygonPanels.size() - 1));
				drawPanel.setComponentZOrder(polygonPanels.get(polygonPanels.size() - 1), 0);

				changingColor();
				if (colorBox.getSelectedItem() == "Custom") {
					polygonPanels.get(polygonPanels.size() - 1).
					changeColor(polygonPanels.get(polygonPanels.size() - 2).color);
				}
				colorPreview.setBackground(polygonPanels.get(polygonPanels.size() - 1).color);
				isFilledBtn.setForeground(new Color(255 - colorPreview.getBackground().getRed(), 
						255 - colorPreview.getBackground().getGreen(), 
						255 - colorPreview.getBackground().getBlue()));

				changingType();
				if (shapeBox.getSelectedItem() == "RoundRect") {
					x.get(x.size() - 1).set(2, lastRW);
					y.get(y.size() - 1).set(2, lastRH);
				}
				
				changingFill();

//				System.out.println(x);
//				System.out.println(polygonPanels);
			}
		});
		
		/* ***************
		 * PRESS MOUSE
		 ***************** */
		drawPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (shapeBox.getSelectedItem() == "Rectangle" || shapeBox.getSelectedItem() == "Oval"
						|| shapeBox.getSelectedItem() == "Line" ||
						shapeBox.getSelectedItem() == "RoundRect") {
					x.get(x.size() - 1).set(0, e.getX());
					y.get(y.size() - 1).set(0, e.getY());
				}
			}
		});

	}
	
	/* ********************
	 * POLYGON PANEL CLASS
	 ********************** */

	class PolygonsPanel extends JPanel {
		int panelIndex;
		Color color = Color.BLACK;
		String figureType = "free";
		boolean isFilled = false;

		public PolygonsPanel(int panelIndex) {
			this.panelIndex = panelIndex;
			this.setBounds(0, 0, 1216, 661);
			this.setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(color);
			
			if (isFilled) {
				if (figureType.equals("free")) {
					g.drawPolyline(arrayListToIntArray(x.get(panelIndex)), arrayListToIntArray(y.get(panelIndex)),
							x.get(panelIndex).size());
				} else if (figureType.equals("rect")) {
					g.fillRect(arrayListToIntArray(x.get(panelIndex))[0], arrayListToIntArray(y.get(panelIndex))[0],
							arrayListToIntArray(x.get(panelIndex))[1], arrayListToIntArray(y.get(panelIndex))[1]);
				} else if (figureType.equals("oval")) {
					g.fillOval(arrayListToIntArray(x.get(panelIndex))[0], arrayListToIntArray(y.get(panelIndex))[0],
							arrayListToIntArray(x.get(panelIndex))[1], arrayListToIntArray(y.get(panelIndex))[1]);
				} else if (figureType.equals("line")) {
					g.drawLine(arrayListToIntArray(x.get(panelIndex))[0], arrayListToIntArray(y.get(panelIndex))[0],
							arrayListToIntArray(x.get(panelIndex))[1], arrayListToIntArray(y.get(panelIndex))[1]);
				} else if (figureType.equals("roundrect")) {
					g.fillRoundRect(arrayListToIntArray(x.get(panelIndex))[0], arrayListToIntArray(y.get(panelIndex))[0],
							arrayListToIntArray(x.get(panelIndex))[1], arrayListToIntArray(y.get(panelIndex))[1], 
							arrayListToIntArray(x.get(panelIndex))[2], arrayListToIntArray(y.get(panelIndex))[2]);
				}
			} else {
				if (figureType.equals("free")) {
					g.drawPolyline(arrayListToIntArray(x.get(panelIndex)), arrayListToIntArray(y.get(panelIndex)),
							x.get(panelIndex).size());
				} else if (figureType.equals("rect")) {
					g.drawRect(arrayListToIntArray(x.get(panelIndex))[0], arrayListToIntArray(y.get(panelIndex))[0],
							arrayListToIntArray(x.get(panelIndex))[1], arrayListToIntArray(y.get(panelIndex))[1]);
				} else if (figureType.equals("oval")) {
					g.drawOval(arrayListToIntArray(x.get(panelIndex))[0], arrayListToIntArray(y.get(panelIndex))[0],
							arrayListToIntArray(x.get(panelIndex))[1], arrayListToIntArray(y.get(panelIndex))[1]);
				} else if (figureType.equals("line")) {
					g.drawLine(arrayListToIntArray(x.get(panelIndex))[0], arrayListToIntArray(y.get(panelIndex))[0],
							arrayListToIntArray(x.get(panelIndex))[1], arrayListToIntArray(y.get(panelIndex))[1]);
				} else if (figureType.equals("roundrect")) {
					g.drawRoundRect(arrayListToIntArray(x.get(panelIndex))[0], arrayListToIntArray(y.get(panelIndex))[0],
							arrayListToIntArray(x.get(panelIndex))[1], arrayListToIntArray(y.get(panelIndex))[1], 
							arrayListToIntArray(x.get(panelIndex))[2], arrayListToIntArray(y.get(panelIndex))[2]);
				}
			}

		}

		public void changeColor(Color newColor) {
			color = newColor;
		}

		public void changeType(String type) {
			figureType = type;
		}
		
		public void changeFill(boolean isFilled) {
			this.isFilled = isFilled;
		}

	}
	
	/* ****************
	 * FUNCTIONS
	 ****************** */

	public static int[] arrayListToIntArray(ArrayList<Integer> nums) {
		Object[] obj_arr = nums.toArray();
		int[] arr = new int[nums.size()];
		for (int i = 0; i < obj_arr.length; i++) {
			arr[i] = (int) obj_arr[i];
		}
		return arr;
	}

	public void changingColor() {
		if (colorBox.getSelectedItem() == "Red") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.RED);
		} else if (colorBox.getSelectedItem() == "Green") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.GREEN);
		} else if (colorBox.getSelectedItem() == "Blue") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.BLUE);
		} else if (colorBox.getSelectedItem() == "Black") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.BLACK);
		} else if (colorBox.getSelectedItem() == "Cyan") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.CYAN);
		} else if (colorBox.getSelectedItem() == "DGray") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.DARK_GRAY);
		} else if (colorBox.getSelectedItem() == "LGray") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.LIGHT_GRAY);
		} else if (colorBox.getSelectedItem() == "Gray") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.GRAY);
		} else if (colorBox.getSelectedItem() == "Magenta") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.MAGENTA);
		} else if (colorBox.getSelectedItem() == "Pink") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.PINK);
		} else if (colorBox.getSelectedItem() == "Orange") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.ORANGE);
		} else if (colorBox.getSelectedItem() == "Yellow") {
			polygonPanels.get(polygonPanels.size() - 1).changeColor(Color.YELLOW);
		}
	}

	public void changingType() {
		if (shapeBox.getSelectedItem() == "Free") {
			polygonPanels.get(polygonPanels.size() - 1).changeType("free");
		} else if (shapeBox.getSelectedItem() == "Rectangle") {
			setXandYinArrToZero();
			polygonPanels.get(polygonPanels.size() - 1).changeType("rect");
		} else if (shapeBox.getSelectedItem() == "Oval") {
			setXandYinArrToZero();
			polygonPanels.get(polygonPanels.size() - 1).changeType("oval");
		} else if (shapeBox.getSelectedItem() == "Line") {
			setXandYinArrToZero();
			polygonPanels.get(polygonPanels.size() - 1).changeType("line");
		} else if (shapeBox.getSelectedItem() == "RoundRect") {
			setXandYinArrToZero();
			x.get(x.size() - 1).add(0);
			y.get(y.size() - 1).add(0);
			polygonPanels.get(polygonPanels.size() - 1).changeType("roundrect");
		}
		
	}
	
	public void changingFill() {
		if (isFilledBtn.isSelected()) {
			polygonPanels.get(polygonPanels.size() - 1).changeFill(true);
		} else {
			polygonPanels.get(polygonPanels.size() - 1).changeFill(false);
		}
		
	}
	
	public void setXandYinArrToZero() {
		x.get(x.size() - 1).add(0);
		y.get(y.size() - 1).add(0);
		x.get(x.size() - 1).add(0);
		y.get(y.size() - 1).add(0);
	}
	
	public void saveImage(){
		String path;
		FileDialog fd = new FileDialog(this, "Save", FileDialog.SAVE);
		fd.setDirectory("C:\\");
		fd.setFile("*.png");
		fd.setVisible(true);
		path = fd.getDirectory() + fd.getFile();
		
		
		BufferedImage image = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		drawPanel.paint(g);
		 try {
			 	ImageIO.write(image, "png", new File(path));
		    } catch (IOException ex) {
		        
		   }
    }
}

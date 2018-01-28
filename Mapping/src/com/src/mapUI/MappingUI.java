package com.src.mapUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import jlibs.core.lang.StringUtil;

import com.jgoodies.validation.ValidationResult;
import com.src.exception.mapUtility.MappingUtilityException;
import com.src.mapUtility.MappingSheet;
 
public class MappingUI extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JLabel  mappingLabel= new JLabel("Mapping Sheet: ", SwingConstants.LEFT);
	JLabel emptyLabel1=new JLabel("",10);
	JLabel emptyLabel2=new JLabel("",10);
	JLabel emptyLabel3=new JLabel("",10);
	JLabel emptyLabel4=new JLabel("",10);
	JLabel emptyLabel5=new JLabel("",10);
	JLabel emptyLabel6=new JLabel("",10);
	JLabel emptyLabel7=new JLabel("",10);
	JLabel emptyLabel8=new JLabel("",10);
	JLabel emptyLabel9=new JLabel("",10);
	JLabel emptyLabel20=new JLabel("",10);


	JLabel  xsdLabel = new JLabel("XSD Schema: ", SwingConstants.LEFT);
	JLabel  operationLabel = new JLabel("Operation: ", SwingConstants.LEFT);
	JLabel  resultLabel = new JLabel("Result File Location: ", SwingConstants.LEFT);
	JTextField mappingText = new JTextField(30);
	final JTextField xsdText = new JTextField(30);
	final JTextField resultText = new JTextField(30);
	final JTextField operationText = new JTextField(30);

	JButton submitButton = new JButton("Submit");
	JButton resetButton = new JButton("Reset");
	final JButton browseButton1 = new JButton("Browse");
	JButton browseButton2 = new JButton("Browse");
	JButton browseButton3 = new JButton("Browse");


	private JFrame mainFrame;
	private JLabel statusLabel;
	private JPanel controlPanel1,controlPanel2,controlPanel3,controlPanel4,controlPanel5;

	public MappingUI(){
		prepareGUI();
	}

	public static void main(String[] args){

		MappingUI  swingControlDemo = new MappingUI();

		swingControlDemo.showDemo();
	}

	private void prepareGUI(){
		mainFrame = new JFrame("Details");
		mainFrame.setSize(700,500);
		mainFrame.setResizable(false);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}       
		});   

		statusLabel = new JLabel("",JLabel.LEFT);   

		statusLabel.setSize(10,50);

		controlPanel1 = new JPanel();
		controlPanel2 = new JPanel();
		controlPanel3 = new JPanel();
		controlPanel4 = new JPanel();
		controlPanel5 = new JPanel();

		controlPanel1.setLayout(new GridLayout(7,1));
		controlPanel2.setLayout(new GridLayout(8,1));
		controlPanel3.setLayout(new GridLayout(7,1));
		controlPanel4.setLayout(new GridLayout(1,2));
		controlPanel5.setLayout(new GridLayout(1,1));

		mainFrame.add(controlPanel1);
		mainFrame.add(controlPanel2);
		mainFrame.add(controlPanel3);
		mainFrame.add(controlPanel4);
		mainFrame.add(controlPanel5);

		mainFrame.setVisible(true); 
	}

	private void showDemo(){

		controlPanel1.add(mappingLabel);
		controlPanel1.add(emptyLabel1);
		controlPanel1.add(xsdLabel);
		controlPanel1.add(emptyLabel2);
		controlPanel1.add(resultLabel);
		controlPanel1.add(emptyLabel3);
		controlPanel1.add(operationLabel);

		controlPanel2.add(emptyLabel9);
		controlPanel2.add(mappingText);
		controlPanel2.add(emptyLabel4);
		controlPanel2.add(xsdText);
		controlPanel2.add(emptyLabel5);
		controlPanel2.add(resultText);
		controlPanel2.add(emptyLabel6);
		controlPanel2.add(operationText);

		controlPanel3.add(emptyLabel7);
		browseButton1.addActionListener(this);
		controlPanel3.add(browseButton1);
		controlPanel3.add(emptyLabel8);
		browseButton2.addActionListener(this);
		controlPanel3.add(browseButton2);
		controlPanel3.add(emptyLabel20);
		browseButton3.addActionListener(this);
		controlPanel3.add(browseButton3);

		submitButton.addActionListener(this);
		controlPanel4.add(submitButton);

		resetButton.addActionListener(this);
		controlPanel4.add(resetButton);

		controlPanel5.add(statusLabel);

		mainFrame.setVisible(true); 
	}
	public void actionPerformed(ActionEvent e) {    

		if (e.getSource() == browseButton1) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("choose Mapping Sheet");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				mappingText.setText(""+chooser.getCurrentDirectory());
				mappingText.setText(""+chooser.getSelectedFile());
			} else {
				mappingText.setText("choose mapping sheet");
			}
		}
		else if (e.getSource() == browseButton2) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("choose XSD");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				xsdText.setText(""+chooser.getCurrentDirectory());
				xsdText.setText(""+chooser.getSelectedFile());
			} else {
				xsdText.setText("choose XSD");
			}
		}
		else if (e.getSource() == browseButton3) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("choose result file location");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				resultText.setText(""+chooser.getCurrentDirectory());
				resultText.setText(""+chooser.getSelectedFile());
			} else {
				resultText.setText("choose location");
			}
		}
		else if(e.getSource()==submitButton)
		{
			String command = e.getActionCommand();
			ValidationResult vr = new ValidationResult();
			String notValidFile = "N";
			File f = new File(mappingText.getText());
			System.out.println("command"+command+"==="+mappingText.getText()+"==="+xsdText.getText()+"==="+resultText.getText()+"==="+operationText.getText());
			if("".equals(mappingText.getText())){
				System.out.println("Eneterd if block");
				JOptionPane.showMessageDialog(null,"Please give the path of mapping sheet");
			}else if("".equals(xsdText.getText())){
				JOptionPane.showMessageDialog(null,"Please give the path of xsd schema");
			}else if("".equals(resultText.getText())){
				JOptionPane.showMessageDialog(null,"Please give the folder path to write the result file");
			}else if("".equals(operationText.getText())){
				JOptionPane.showMessageDialog(null,"Please give the operation name");
			}
			
			if(!"".equals(mappingText.getText()) && !f.exists() && !f.isFile()){
				JOptionPane.showMessageDialog(null,"Please enter valid excel file.");
				notValidFile = "Y";
			}
			f = new File(xsdText.getText());
			if(!"".equals(xsdText.getText()) && !f.exists() && !f.isFile() && notValidFile == "N" ){
				JOptionPane.showMessageDialog(null,"Please enter valid XSD schema.");
				notValidFile = "Y";
			}
			f = new File(resultText.getText());
			if(!"".equals(resultText.getText()) && !f.exists() && !f.isDirectory() && notValidFile == "N"){
				JOptionPane.showMessageDialog(null,"Please enter valid path for the result file to save.");
				notValidFile = "Y";
			}
			String[] inputFields = {mappingText.getText().trim(),xsdText.getText().trim(),resultText.getText().trim(),operationText.getText().trim()};
			try{
				if(!"".equals(mappingText.getText()) && !"".equals(xsdText.getText()) && !"".equals(resultText.getText()) && !"".equals(operationText.getText()) && notValidFile=="N"){
					MappingSheet.main(inputFields);
					statusLabel.setText("Open result file location to view result");
				}
			}
			
			catch (Exception exc) {
				exc.printStackTrace();
			}
			
		}
		else if(e.getSource()==resetButton)
		{
			mappingText.setText("");
			xsdText.setText("");
			resultText.setText("");
			statusLabel.setText("");
			operationText.setText("");
		}
	}
}

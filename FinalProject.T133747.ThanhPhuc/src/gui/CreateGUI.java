package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;


import da.BrandDA;
import da.CategoryDA;
import da.ProductDA;
import da.SQLiteDB;
import da.UnitofMeasureDA;
import dataobject.Brand;
import dataobject.Category;
import dataobject.UnitOfMeasure;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.util.Vector;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CreateGUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textFieldName;
	
	private SQLiteDB foc2warehouseDb;
	private ProductDA productDA;
	private CategoryDA catDA;
	private BrandDA braDA;
	private UnitofMeasureDA unitDA;
	
	private JTextField textFieldPrice;
	private JTextField textFieldProductCode;
	private JButton btnOk;
	private JButton btnCancel;
	private JComboBox comboBox;
	private Vector<Category> catList;
	private JComboBox comboBox_Measure;
	private Vector<UnitOfMeasure> unitOfMeasuresList;
	private JComboBox comboBox_Brand;
	private Vector<Brand> brandList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateGUI frame = new CreateGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateGUI() {
		//setType(Type.UTILITY);
		
		productDA = new ProductDA();
		catDA = new CategoryDA();
		braDA = new BrandDA();
		unitDA = new UnitofMeasureDA();
		
		//foc2warehouse = new SQLiteDB();
		//foc2warehouse.getAllCategories();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 332);
		contentPane = new JPanel();
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddProduct = new JLabel("Add Product");
		lblAddProduct.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddProduct.setBounds(149, 18, 111, 14);
		contentPane.add(lblAddProduct);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(25, 81, 46, 14);
		contentPane.add(lblNewLabel);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(128, 82, 216, 20);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Categoryid");
		lblNewLabel_1.setBounds(25, 112, 86, 14);
		contentPane.add(lblNewLabel_1);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				//System.out.println(comboBox.getSelectedItem());
				Category cat = (Category)comboBox.getSelectedItem();
				
			}
		});
		
		
		catList = catDA.getAllCategories();
		comboBox.setModel(new DefaultComboBoxModel(catList));
		
		comboBox.setBounds(128, 113, 216, 20);
		contentPane.add(comboBox);
		
		
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(211, 245, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblUnitOfMeasure = new JLabel("Unit of Measure");
		lblUnitOfMeasure.setBounds(25, 143, 93, 14);
		contentPane.add(lblUnitOfMeasure);
		
		comboBox_Measure = new JComboBox();
		comboBox_Measure.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				UnitOfMeasure uom = (UnitOfMeasure)comboBox_Measure.getSelectedItem();
			}
		});
		
		unitOfMeasuresList = unitDA.getAllUnitOfMeasure();
		comboBox_Measure.setModel(new DefaultComboBoxModel(unitOfMeasuresList));
		
		comboBox_Measure.setBounds(128, 144, 216, 20);
		contentPane.add(comboBox_Measure);
		
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setBounds(25, 174, 86, 14);
		contentPane.add(lblBrand);
		
		comboBox_Brand = new JComboBox();
		comboBox_Brand.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Brand bra = (Brand)comboBox_Brand.getSelectedItem();
			}
		});
		
		brandList = braDA.getAllBrands();
		comboBox_Brand.setModel(new DefaultComboBoxModel(brandList));
		
		comboBox_Brand.setBounds(128, 175, 216, 20);
		contentPane.add(comboBox_Brand);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(25, 212, 46, 14);
		contentPane.add(lblPrice);
		
		textFieldPrice = new JTextField();
		textFieldPrice.setBounds(128, 206, 216, 20);
		contentPane.add(textFieldPrice);
		textFieldPrice.setColumns(10);
		
		JLabel lblInstock = new JLabel("Product Code");
		lblInstock.setBounds(25, 53, 86, 14);
		contentPane.add(lblInstock);
		
		textFieldProductCode = new JTextField();
		textFieldProductCode.setBounds(128, 54, 216, 20);
		contentPane.add(textFieldProductCode);
		textFieldProductCode.setColumns(10);
		
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(this);
		
		btnOk.setBounds(82, 245, 89, 23);
		contentPane.add(btnOk); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOk)
		{
			addProduct();
		}else if(e.getSource() == btnCancel){
			CreateGUI.this.dispose();
		}
	}

	private void addProduct() {
		String productname = textFieldName.getText();
		double price = Double.parseDouble(textFieldPrice.getText());
		String productCode = textFieldProductCode.getText();
		
		Brand selectedBra = (Brand)comboBox_Brand.getSelectedItem();
		int braId = selectedBra.getId();
		
		UnitOfMeasure selectedUnitOfMeasure = (UnitOfMeasure) comboBox_Measure.getSelectedItem();
		int uomId = selectedUnitOfMeasure.getId();
		
		Category selectedCat = (Category) comboBox.getSelectedItem();
		int catId = selectedCat.getCategoryId();
		
		productDA.insert(productCode, productname, catId, braId, uomId, price," ");
		productDA.getAllProducts();
	}
}
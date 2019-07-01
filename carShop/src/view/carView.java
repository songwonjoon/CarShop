package view;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.carModel;
import model.vo.car;

public class carView extends Panel implements ActionListener{
	JTextField tfcarNum,tfcarName,tfuser,tfpNum;
	JComboBox comcarType;
	JTextArea tacarContents;
	Checkbox cbcon;
	Checkbox cbwing;
	 CheckboxGroup chGroup;
	
	
	
	JButton bcarInsert,bcarModify,bcarDelete;
	JComboBox comcarSearch;
	JTextField tfcarSearch;
	JTable tablecar;
	
	
	//�߰�
	carModel model;
	carTableModel tbModelcar;
	public carView() {
		addLayout();
		InitStyle();
		eventproc();
		// db����
		connectDB();
	}
	private void eventproc() {
		bcarInsert.addActionListener(this);
		bcarModify.addActionListener(this);
		bcarDelete.addActionListener(this);
		tfcarSearch.addActionListener(this);
		tablecar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=tablecar.getSelectedRow();
				int col=0;
				//Ŭ�����ڵ�
				String data=(String) tablecar.getValueAt(row, col);
				int no=Integer.parseInt(data);
				
				try {
					car c=model.selectbyPK(no);
					selectbyPK(c);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		});
		
	}
	void selectbyPK(car c) {
		tfcarNum.setText(String.valueOf(c.getCarcode()));
		tfcarName.setText(c.getName());
		tfuser.setText(c.getBuyer());
		tacarContents.setText(c.getDetail());
		comcarType.setSelectedItem(c.getKind());
	}
	private void InitStyle() {
	tfcarNum.setEditable(false);
	
}
	private void connectDB() {
		try {
			model=new carModel();
			System.out.println("����");
		} catch (Exception e) {
			System.out.println("����");
			e.printStackTrace();
		}
		
	}
	private void addLayout() {
		tfcarNum=new JTextField();
		tfcarName=new JTextField();
		tfuser=new JTextField();
		String[] cbTypeStr= {"����","���������","bmw","���"};
		comcarType=new JComboBox(cbTypeStr);
		tacarContents=new JTextArea();
		tfpNum=new JTextField();
		chGroup = new CheckboxGroup();
		  cbcon = new Checkbox("�����ͺ�",chGroup,false);
		  cbwing= new Checkbox("������", chGroup,false);
		
		//��ư
		bcarInsert=new JButton("���");
		bcarModify=new JButton("����");
		bcarDelete=new JButton("����");
		
		String[] cbcarSearch= {"�ڵ�����","������"};
		comcarSearch=new JComboBox(cbcarSearch);
		tfcarSearch=new JTextField(15);
		
		tbModelcar=new carTableModel();
		tablecar=new JTable(tbModelcar);
		tablecar.setModel(tbModelcar);
		
		//ȭ�鱸��
		//west �ǳ� ����
		JPanel p_west=new JPanel();
		p_west.setLayout(new BorderLayout());
		//���� ���
		JPanel p_west_center=new JPanel();
		p_west_center.setLayout(new BorderLayout());
		//���� ��� ����
		JPanel p_west_center_north=new JPanel();
		p_west_center_north.setLayout(new GridLayout(7,2));
		p_west_center_north.add(new JLabel("��� ��ȣ"));
		p_west_center_north.add(tfcarNum);
		
		p_west_center_north.add(new JLabel("�귣��"));
		p_west_center_north.add(comcarType);
		
		p_west_center_north.add(new JLabel("����"));
		p_west_center_north.add(tfcarName);
		
		p_west_center_north.add(new JLabel("������"));
		p_west_center_north.add(tfuser);
		
		p_west_center_north.add(new JLabel("����ó"));
		p_west_center_north.add(tfpNum);
		
		JPanel p_west_center_south=new JPanel();
		p_west_center_south.add(new JLabel("�߰��ɼ�"));
		p_west_center_south.add(cbcon);
		p_west_center_south.add(cbwing);
//		���� ��� ���
//		JPanel p_west_center_center=new JPanel();
//		p_west_center_center.setLayout(new GridLayout(0,2));
//		p_west_center_center.add(new JLabel("�߰��ɼ�"));
//		p_west_center_center.add(tacarContents);
		
		
		// ���� ��� �ǳڿ� �ΰ��� �ǳ��߰�
				p_west_center.add(p_west_center_north, BorderLayout.NORTH);
				p_west_center.add(p_west_center_south, BorderLayout.CENTER);

				p_west_center.setBorder(new TitledBorder("�ڵ��������Է�"));// ��輱 �����
		//���� �Ʒ��� ���� �ǳ�
		JPanel p_east_south = new JPanel();
		p_east_south.setLayout(new GridLayout(1,3));
		p_east_south.add(bcarInsert);
		p_east_south.add(bcarModify);
		p_east_south.add(bcarDelete);
		
		p_west.add(p_west_center,BorderLayout.NORTH);
//		p_west.add(p_west_center_south,BorderLayout.CENTER);
		
		// east �ǳ� ����
				JPanel p_east = new JPanel();
				p_east.setLayout(new BorderLayout());

				// �������� ����
				JPanel p_east_north = new JPanel();
				p_east_north.add(comcarSearch);
				p_east_north.add(tfcarSearch);
				// ��Լ� �����
				p_east_north.setBorder(new TitledBorder("�ڵ����˻�"));
				p_east.add(p_east_north, BorderLayout.NORTH);
				p_east.add(new JScrollPane(tablecar), BorderLayout.CENTER);
				p_east.add(p_east_south,BorderLayout.SOUTH);
				setLayout(new GridLayout(0, 1));
				add(p_west);
				add(p_east);
				
	}
	class carTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();
		String[] columnNames = { "��Ϲ�ȣ", "�귣��", "����","������" };

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			ArrayList temp = (ArrayList) data.get(row);
			return temp.get(col);
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt=e.getSource();
		if (evt==bcarInsert) {
			insertcar();
		}else if (evt==bcarModify) {
			modifycar();
		}else if (evt==bcarDelete) {
			deletecar();
		}else if (evt==tfcarSearch) {
			searchcar();
		}
		
	}
	private void searchcar() {
		int idx=comcarSearch.getSelectedIndex();
		String str=tfcarSearch.getText();
		
		try {
			ArrayList data = model.searchCar(idx, str);
			tbModelcar.data = data; // VideoTableModel
			tablecar.setModel(tbModelcar);
			tbModelcar.fireTableDataChanged();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	private void deletecar() {
		car c=new car();
		c.setCarcode(Integer.parseInt(tfcarNum.getText()));
		c.setName(tfcarName.getText());
		c.setBuyer(tfuser.getText());
		c.setDetail(tacarContents.getText());
		c.setKind((String)comcarType.getSelectedItem());
		
		try {
			model.deletecar(c);
			JOptionPane.showMessageDialog(null, "���ſϷ�");
		} catch (HeadlessException | SQLException e) {
			JOptionPane.showMessageDialog(null, "���ſϷ�");
			e.printStackTrace();
		}
	}
	private void modifycar() {
		car c=new car();
		c.setCarcode(Integer.parseInt(tfcarNum.getText()));
		c.setName(tfcarName.getText());
		c.setBuyer(tfuser.getText());
		c.setDetail(tacarContents.getText());
		c.setKind((String)comcarType.getSelectedItem());
		
		try {
			model.modifycar(c);
			JOptionPane.showMessageDialog(null, "�����Ϸ�");
		} catch (HeadlessException | SQLException e) {
			JOptionPane.showMessageDialog(null, "��������");
			e.printStackTrace();
		}
	}
	private void insertcar() {
		car c=new car();
		c.setKind((String)comcarType.getSelectedItem());
		c.setBuyer((String)tfuser.getText());
		c.setDetail((String)tacarContents.getText());
		c.setName((String)tfcarName.getText());
		
		try {
			model.insertcar(c);
			JOptionPane.showMessageDialog(null, "��� �Ϸ�");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��� ����");
			e.printStackTrace();
		}
		
	}

}

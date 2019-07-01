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
	
	
	//추가
	carModel model;
	carTableModel tbModelcar;
	public carView() {
		addLayout();
		InitStyle();
		eventproc();
		// db연결
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
				//클릭레코드
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
			System.out.println("연결");
		} catch (Exception e) {
			System.out.println("실패");
			e.printStackTrace();
		}
		
	}
	private void addLayout() {
		tfcarNum=new JTextField();
		tfcarName=new JTextField();
		tfuser=new JTextField();
		String[] cbTypeStr= {"밴츠","람보르기니","bmw","페라리"};
		comcarType=new JComboBox(cbTypeStr);
		tacarContents=new JTextArea();
		tfpNum=new JTextField();
		chGroup = new CheckboxGroup();
		  cbcon = new Checkbox("컨버터블",chGroup,false);
		  cbwing= new Checkbox("윙도어", chGroup,false);
		
		//버튼
		bcarInsert=new JButton("등록");
		bcarModify=new JButton("수정");
		bcarDelete=new JButton("삭제");
		
		String[] cbcarSearch= {"자동차명","구매자"};
		comcarSearch=new JComboBox(cbcarSearch);
		tfcarSearch=new JTextField(15);
		
		tbModelcar=new carTableModel();
		tablecar=new JTable(tbModelcar);
		tablecar.setModel(tbModelcar);
		
		//화면구성
		//west 판넬 구성
		JPanel p_west=new JPanel();
		p_west.setLayout(new BorderLayout());
		//왼쪽 가운데
		JPanel p_west_center=new JPanel();
		p_west_center.setLayout(new BorderLayout());
		//왼쪽 가운데 위쪽
		JPanel p_west_center_north=new JPanel();
		p_west_center_north.setLayout(new GridLayout(7,2));
		p_west_center_north.add(new JLabel("등록 번호"));
		p_west_center_north.add(tfcarNum);
		
		p_west_center_north.add(new JLabel("브랜드"));
		p_west_center_north.add(comcarType);
		
		p_west_center_north.add(new JLabel("차명"));
		p_west_center_north.add(tfcarName);
		
		p_west_center_north.add(new JLabel("구매자"));
		p_west_center_north.add(tfuser);
		
		p_west_center_north.add(new JLabel("연락처"));
		p_west_center_north.add(tfpNum);
		
		JPanel p_west_center_south=new JPanel();
		p_west_center_south.add(new JLabel("추가옵션"));
		p_west_center_south.add(cbcon);
		p_west_center_south.add(cbwing);
//		왼쪽 가운데 가운데
//		JPanel p_west_center_center=new JPanel();
//		p_west_center_center.setLayout(new GridLayout(0,2));
//		p_west_center_center.add(new JLabel("추가옵션"));
//		p_west_center_center.add(tacarContents);
		
		
		// 왼쪽 가운데 판넬에 두개의 판넬추가
				p_west_center.add(p_west_center_north, BorderLayout.NORTH);
				p_west_center.add(p_west_center_south, BorderLayout.CENTER);

				p_west_center.setBorder(new TitledBorder("자동차정보입력"));// 경계선 만들기
		//왼쪽 아래에 사용될 판넬
		JPanel p_east_south = new JPanel();
		p_east_south.setLayout(new GridLayout(1,3));
		p_east_south.add(bcarInsert);
		p_east_south.add(bcarModify);
		p_east_south.add(bcarDelete);
		
		p_west.add(p_west_center,BorderLayout.NORTH);
//		p_west.add(p_west_center_south,BorderLayout.CENTER);
		
		// east 판넬 구성
				JPanel p_east = new JPanel();
				p_east.setLayout(new BorderLayout());

				// 오른쪽의 위쪽
				JPanel p_east_north = new JPanel();
				p_east_north.add(comcarSearch);
				p_east_north.add(tfcarSearch);
				// 경게선 만들기
				p_east_north.setBorder(new TitledBorder("자동차검색"));
				p_east.add(p_east_north, BorderLayout.NORTH);
				p_east.add(new JScrollPane(tablecar), BorderLayout.CENTER);
				p_east.add(p_east_south,BorderLayout.SOUTH);
				setLayout(new GridLayout(0, 1));
				add(p_west);
				add(p_east);
				
	}
	class carTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();
		String[] columnNames = { "등록번호", "브랜드", "차명","구매자" };

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
			JOptionPane.showMessageDialog(null, "제거완료");
		} catch (HeadlessException | SQLException e) {
			JOptionPane.showMessageDialog(null, "제거완료");
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
			JOptionPane.showMessageDialog(null, "수정완료");
		} catch (HeadlessException | SQLException e) {
			JOptionPane.showMessageDialog(null, "수정실패");
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
			JOptionPane.showMessageDialog(null, "등록 완료");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "등록 실패");
			e.printStackTrace();
		}
		
	}

}

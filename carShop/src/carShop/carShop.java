package carShop;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import view.carView;

public class carShop extends JFrame{
	carView car;
	public carShop() {
		car=new carView();
		//탭추가
		JTabbedPane pane=new JTabbedPane();
		pane.add("자동차",car);
		pane.setSelectedIndex(0);
		add("Center",pane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("자동차 정보");
		setSize(800,600);
		setVisible(true);
	}
	public static void main(String[] args) {
		new carShop();
	}
}

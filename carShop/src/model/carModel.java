package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.car;

public class carModel {
	Connection con;
	public carModel() throws Exception {
		con=DBcon.getConnection();
	}
	public void insertcar(car c) throws Exception {
		con.setAutoCommit(false);// 자동커밋 해제

		// DB에 insert 시키기
		String sql1 = "insert into cinfo(carcode,name,kind,buyer,detail) "
				+ "values(seq_car_code.nextval,?,?,?,?)";

		String sql2 = "insert into car(ccode,carcode) " + "values(seq_c_code.nextval,seq_car_code.currval)";

		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps1.setString(1, c.getName());
		ps1.setString(2, c.getKind());
		ps1.setString(3, c.getBuyer());
		ps1.setString(4, c.getDetail());

		PreparedStatement ps2 = con.prepareStatement(sql2);

		int r1 = ps1.executeUpdate();// sql1실행
		int r2 = ps2.executeUpdate();// sql2실행
		if (r1 != 1 || r2 != 1) {// 두개의 sql문장중 하나라도 실패하면 롤백한다
			con.rollback();
			System.out.println("롤백 되었습니다");
		}
		con.commit();
		ps1.close();
		ps2.close();
		con.setAutoCommit(true);// 자동커밋 전환
		
	}
	public void modifycar(car c) throws SQLException {
		String sql = "update cinfo set name=?," + "kind=?, " + "buyer=?, " + "detail=? "
				+ "where carcode=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, c.getName());
		ps.setString(2, c.getKind());
		ps.setString(3, c.getBuyer());
		ps.setString(4, c.getDetail());
		ps.setInt(5, c.getCarcode());
		
		ps.executeUpdate();
		ps.close();
	}
	public void deletecar(car c) throws SQLException {
		String sql = "DELETE FROM cinfo where carcode=? ";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, c.getCarcode());
		

		ps.executeUpdate();
		ps.close();

		
	}
	public car selectbyPK(int no) throws SQLException {
		car c=new car();
		String sql = "select * from cinfo where carcode=" + no;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			c.setCarcode(Integer.parseInt(rs.getString("carcode")));
			c.setBuyer(rs.getString("buyer"));
			c.setKind(rs.getString("kind"));
			c.setName(rs.getString("name"));
			c.setDetail(rs.getString("detail"));

		}
		rs.close();
		ps.close();
		return c;
	}
	public ArrayList searchCar(int idx, String str) throws SQLException {
		// 검색기능
				String[] key = { "name","buyer"};

				String sql = "select carcode,name,kind,buyer " + "from cinfo " + "where " + key[idx] + " like '%"
						+ str + "%'";
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				ArrayList data = new ArrayList();
				while (rs.next()) {
					ArrayList temp = new ArrayList();

					temp.add(rs.getString("carcode"));

					temp.add(rs.getString("name"));

					temp.add(rs.getString("kind"));

					temp.add(rs.getString("buyer"));


					data.add(temp); // arraylist에 arraylist를 추가

				}

				rs.close();

				ps.close();

				return data;
	}
}

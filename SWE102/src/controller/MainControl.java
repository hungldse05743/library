package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class MainControl {

    View v;
    Connection con;

    public MainControl(View v) {
        this.v = v;
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            System.out.println("Error!!!!!!");
        }
    }

    DefaultTableModel dftm = new DefaultTableModel() {
        public Class getColumnClass(int col) {
            if (col == 3) {
                return Boolean.class;
            } else {
                return Object.class;
            }
        }
    };

    public void control() {
        v.setVisible(true);
        v.getBtnLoad().addActionListener((ae) -> {
            String[] tieude = {"a1", "a2", "a3", "a4", "a5"};
            dftm.setColumnIdentifiers(tieude);
            String sql = "select * from StudentInfo";
            try {
                Vector v1;
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    v1 = new Vector();
                    v1.add(rs.getString(1));
                    v1.add(rs.getString(2));
                    v1.add(rs.getString(3));
//                    v1.add("<html><input id='abc' type='checkbox' " + (rs.getBoolean(4) ? "checked" : "")+ "onclick = 'myclick()'>" + "<script></script></html>");
                    v1.add(rs.getBoolean(4));
                    v1.add(rs.getFloat(5));
                    dftm.addRow(v1);
                }
                v.getTblDipsplay().setModel(dftm);
            } catch (Exception ex) {
            }
        });
    }

    public static void main(String[] args) {
        new MainControl(new View()).control();
    }
}

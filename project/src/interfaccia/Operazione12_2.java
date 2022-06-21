package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.EliminaOrdine;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Operazione12_2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int codice = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione12_2 frame = new Operazione12_2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void refreshTable() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM ordinefornitore";
			rs = st.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
			rs.relative(-1);

			while (rs.next()) {

				table.setModel(DbUtils.resultSetToTableModel(rs));
			}

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public Operazione12_2() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 656, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 626, 307);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.getSelectedRow();

				codice = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Visualizza elenco ordini");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					Connection con = null;
					Statement st = null;
					ResultSet rs = null;

					try {
						con = DBConnectionPool.getConnection();
						st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

						String sql = "SELECT * FROM ordinefornitore";
						rs = st.executeQuery(sql);
						table.setModel(DbUtils.resultSetToTableModel(rs));
						rs.relative(-1);

						while (rs.next()) {

							table.setModel(DbUtils.resultSetToTableModel(rs));
						}

					} catch (SQLException s) {
						JOptionPane.showMessageDialog(null, s);
					} finally {
						try {
							if (rs != null)
								rs.close();
							if (st != null)
								st.close();
							DBConnectionPool.releaseConnection(con);
						} catch (SQLException s) {
							JOptionPane.showMessageDialog(null, s);
						}
					}
				}
	
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(52, 29, 226, 32);
		contentPane.add(btnNewButton);
		
		JButton btnElimina = new JButton("Elimina");
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
int risposta= JOptionPane.showConfirmDialog(null, "Vuoi procedere all'eliminazione?","Eliminazione",JOptionPane.YES_NO_OPTION);
				
				if (risposta==0)
				{
					EliminaOrdine op14= new EliminaOrdine();
					op14.EliminaOrdineFornitore(codice);
					refreshTable();
				
					
				}
			}
			
		});btnElimina.setBounds(368,29,216,32);contentPane.add(btnElimina);
}}

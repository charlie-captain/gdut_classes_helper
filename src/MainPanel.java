import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainPanel extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	DefaultTableModel model;
	 int nowPage=1;
	public static int totalPage=1;
	private JTextField tfSearch;
	
	
	
	public MainPanel() {
		setTitle("抢课界面");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		setSize(684, 573);
		setLocationRelativeTo(null);
		
		String[] columsName={"课程代码","课程名称","课程时间","课程教师","课程类型","限选人数","已选人数"};
		model=new DefaultTableModel();
		model.setColumnIdentifiers(columsName);
		getClassList(model, "1");
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		

		getClassList(model, "1");
		table=new JTable(model);
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JLabel label = new JLabel("New label");
		scrollPane.setColumnHeaderView(label);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 50));
		panel.setMaximumSize(new Dimension(500, 200));
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnGet = new JButton("\u62A2\u8BFE");
		btnGet.setPreferredSize(new Dimension(69, 23));
		btnGet.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnUp = new JButton("\u4E0A\u4E00\u9875");
		
		JButton btnDown = new JButton("\u4E0B\u4E00\u9875");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		 
		JButton btnRefresh = new JButton("\u5237\u65B0");
		btnRefresh.setPreferredSize(new Dimension(69, 23));
		btnRefresh.setMinimumSize(new Dimension(69, 23));
		btnRefresh.setMaximumSize(new Dimension(69, 23));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getClassList(model,"1");
			}
		});
		
		JButton btnOwn = new JButton("\u67E5\u770B\u5DF2\u9009\u8BFE\u7A0B");
		panel.add(btnOwn);
		panel.add(btnRefresh);
		panel.add(btnUp);
		panel.add(btnDown);
		
		tfSearch = new JTextField();
		tfSearch.setMinimumSize(new Dimension(10, 21));
		tfSearch.setPreferredSize(new Dimension(20, 21));
		panel.add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("\u641C\u7D22");
		panel.add(btnSearch);
		panel.add(btnGet);
		
		btnGet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectRow=table.getSelectedRow();
				String classNum=table.getValueAt(selectRow, 0).toString();
				String className=table.getValueAt(selectRow, 1).toString();
				new Thread(new Runnable() {
					@Override
					public void run() {
						String result=Http.selectClass(classNum,className);
						JOptionPane.showMessageDialog(null,result);
					}
				}).start();
			}
		});
		
		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nowPage>1){
					nowPage--;
					getClassList(model, nowPage+"");
				}
			}
		});
		
		
		btnDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nowPage<totalPage){
					nowPage++;
					getClassList(model, nowPage+"");
				}
			}
		});
		
		btnOwn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						resetModel();
						Vector<Class> vector=Http.getOwnClass();
						setModel(model, vector);
						
					}
				}).start();
			}
		});
		
		
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						resetModel();
						String searchValue=tfSearch.getText();
						System.out.println(searchValue);
						Vector<Class> vector=Http.searchClass(searchValue);
						setModel(model,vector);
					}
				}).start();
			}
		});
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void setModel(DefaultTableModel model, Vector<Class> vector) {
		for(int i=0;i<vector.size();i++){
			Vector vector2=new Vector<>();
			vector2.addElement(vector.get(i).getClassNum());
			vector2.addElement(vector.get(i).getClassName());				
			vector2.addElement(vector.get(i).getClassTime());
			vector2.addElement(vector.get(i).getClassTeacher());
			vector2.addElement(vector.get(i).getClassType());
			vector2.addElement(vector.get(i).getClassPeople());
			vector2.addElement(vector.get(i).getClassSelectPeople());
			model.addRow(vector2);
		}
	}
	
	
	public void getClassList(DefaultTableModel model,String page) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				resetModel();
					Vector<Class> vector=Http.getClassList(page);
					setModel(model, vector);
			}
		}).start();
	}
	
	
	public void resetModel(){
		
		while(model.getRowCount()>0){
			model.removeRow(model.getRowCount()-1);
		}
	}
}

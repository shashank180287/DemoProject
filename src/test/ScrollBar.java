package test;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;

public class ScrollBar {

	public static void main(String[] args) {
        final MyTableModel tm = new MyTableModel();
        tm.addData(new Data("R1C1", "R1C2"));

        JTable table = new JTable(tm);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout());
        frame.add(scrollPane);
        frame.pack();
        frame.setSize(400, 150);
        frame.setVisible(true);

        Thread t = new Thread(new Runnable() {
            private int count = 2;
            public void run() {
                for ( ; ; ) {
                    tm.addData(new Data("R" + count + "C1", "R" + count + "C2"));
                    count++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    private static class MyTableModel extends AbstractTableModel {
        private java.util.List<Data> dataList = new ArrayList<Data>();

        public int getColumnCount() {
            return 2;
        }

        public void addData(Data data) {
            dataList.add(data);
            fireTableRowsInserted(dataList.size()-1, dataList.size()-1);
        }

        public int getRowCount() {
            return dataList.size();
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Data data = dataList.get(rowIndex);
            return columnIndex == 0 ? data.data1 : data.data2;
        }
    }

    private static class Data {
        public String data1;
        public String data2;

        public Data(String data1, String data2) {
            this.data1 = data1;
            this.data2 = data2;
        }
    }
}

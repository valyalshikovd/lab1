import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class GUI extends JFrame {
    private Object[] columnHeaders = new String[]{ "Name", "Type", "Price", "Count"};
    private final DefaultTableModel dtm = new DefaultTableModel();
    private final JButton addButton = new JButton("Add");
    private final JButton editButton = new JButton("Edit");
    private final JButton removeButton = new JButton("Remove");
    private final JPanel productEditor = new JPanel();
    private final JTextField nameEditor = new JTextField();
    private final JTextField typeEditor = new JTextField();
    private final JTextField priceEditor = new JTextField();
    private final JTextField countEditor = new JTextField();
    private final JPanel panel = new JPanel();
    private final Container container;
    private final JTable table = new JTable(dtm);
    private final DataBase dataBase = new DataBase();

    public GUI() throws SQLException {
        super("Paper Market");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1280, 720);
        this.setResizable(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                int index = table.getSelectedRow();
                nameEditor.setText(table.getValueAt(index, 0) + "");
                typeEditor.setText(table.getValueAt(index, 1) + "");
                priceEditor.setText(table.getValueAt(index, 2) + "");
                countEditor.setText(table.getValueAt(index, 3) + "");
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataBase.add(new Product(nameEditor.getText(), typeEditor.getText(),
                            Integer.parseInt(priceEditor.getText()), Integer.parseInt(countEditor.getText())));
                    updateTable();
                } catch (Exception exception) {
                    System.err.println(exception);
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataBase.edit( new Product(nameEditor.getText(), typeEditor.getText(),
                            Integer.parseInt(priceEditor.getText()), Integer.parseInt(countEditor.getText())));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    updateTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataBase.delete(new Product(nameEditor.getText(), typeEditor.getText(),
                            Integer.parseInt(priceEditor.getText()), Integer.parseInt(countEditor.getText())));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    updateTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        container = this.getContentPane();
        container.setLayout(new GridLayout(3, 1, 1, 1));
        panel.add(addButton);
        panel.add(editButton);
        panel.add(removeButton);
        dtm.setColumnIdentifiers(columnHeaders);
        nameEditor.setPreferredSize(new Dimension(300, 40));
        typeEditor.setPreferredSize(new Dimension(300, 40));
        priceEditor.setPreferredSize(new Dimension(300, 40));
        countEditor.setPreferredSize(new Dimension(300, 40));
        productEditor.add(nameEditor);
        productEditor.add(typeEditor);
        productEditor.add(priceEditor);
        productEditor.add(countEditor);
        this.updateTable();
        container.add(table);
        container.add(new JScrollPane(table));
        container.add(productEditor);
        container.add(panel);
        this.setVisible(true);
    }

    private void updateTable() throws SQLException {
        dtm.setRowCount(0);
        List<Product> list = dataBase.showAll();
        for (Product p : list){
            Object[] data = new String[5];
            data[0] = p.getName();
            data[1] = p.getType();
            data[2] = p.getPrice() + "";
            data[3] = p.getCount() + "";
            dtm.addRow(data);
        }
        table.revalidate();
    }
}

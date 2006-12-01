package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class FrameTuring {

	private JTable table;
	JScrollPane scrollPane;
	private static final String TEXTO_FINAIS = "Entre aqui o(s) estado(s) final(is), separando-os por ','";
	private JTextField finaisTextField;
	private JTextField fitaTextField;
	private static final String TEXTO_ALFABETO = "Entre aqui o alfabeto, separando os caracteres por ','";
	private static final String TEXTO_ESTADOS = "Entre aqui os estados, separando os elementos por ','";
	private JComboBox inicialComboBox;
	private JTextField vazioTextField;
	private JTextField estadosTextField;
	private JTextField alfabetoTextField;
	private JFrame frame;
	
	JPanel panelConfiguracao;
	JPanel panelFuncoes;
	JPanel panelSimulacao;
	private Object[] header;
	private String[][] data;
	
	private Collection alfabeto;
	private String vazio;
	
	private Collection estados;
	private String inicial;
	private Collection finais;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			FrameTuring window = new FrameTuring();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application
	 */
	public FrameTuring() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Máquina de Turing");
		frame.setName("frmTuring");
		frame.setBounds(100, 100, 516, 421);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JTabbedPane tabbedPane = new JTabbedPane();
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		panelConfiguracao = new JPanel();
		panelConfiguracao.setLayout(null);
		tabbedPane.addTab("Configuração", null, panelConfiguracao, "Configure a Máquina de Turing");

		alfabetoTextField = new JTextField();
		alfabetoTextField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				if (alfabetoTextField.getText().equals(TEXTO_ALFABETO))
					alfabetoTextField.setText("");
			}
			
			public void focusLost(FocusEvent arg0) {
				if (alfabetoTextField.getText().equals(""))
					alfabetoTextField.setText(TEXTO_ALFABETO);
				else {
					Collection alfabeto = separaElementos(alfabetoTextField.getText());
					
					String novoAlfabeto = null;
					for (Object object : alfabeto) {
						String caractere = (String)object;
						if (caractere.length() != 1){
							
						}
						else
							if (novoAlfabeto == null)
								novoAlfabeto = caractere;
							else
								novoAlfabeto += ", ".concat(caractere);
					}
						
					
					alfabetoTextField.setText(novoAlfabeto);
					alfabeto = separaElementos(alfabetoTextField.getText());
					
					for (Object object : alfabeto) {
						String caractere = (String)object;
						if (caractere.equals(vazioTextField.getText()))
							vazioTextField.setText("");
					}
				}
			}
		});
		alfabetoTextField.setText(TEXTO_ALFABETO);
		alfabetoTextField.setBounds(20, 30, 339, 20);
		panelConfiguracao.add(alfabetoTextField);

		estadosTextField = new JTextField();
		estadosTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				inicialComboBox.removeAllItems();
				
				if (estadosTextField.getText().equals("")) {
					estadosTextField.setText(TEXTO_ESTADOS);
					finaisTextField.setText(TEXTO_FINAIS);
				}
				else {
					Collection estados = separaElementos(estadosTextField.getText());
					
					String novosEstados = null;
					for (Object object : estados) {
						String estado = (String)object;
						if (novosEstados == null)
							novosEstados = estado;
						else
							novosEstados += ", ".concat(estado);
					}
						
					
					estadosTextField.setText(novosEstados);
					estados = separaElementos(estadosTextField.getText());
					
					for (Object caractere : estados)
						inicialComboBox.addItem(caractere);
					
					////////////////////////
					Collection finais = separaElementos(finaisTextField.getText());
					
					String novosFinais = null;
					for (Object object : finais) {
						String estFinal = (String)object;
						if (estados.contains(estFinal))
							if (novosFinais == null)
								novosFinais = estFinal;
							else
								novosFinais += ", ".concat(estFinal);
					}
						
					if (novosFinais == null)
						finaisTextField.setText(TEXTO_FINAIS);
					else
						finaisTextField.setText(novosFinais);
					////////////////////////
				}
			}
			public void focusGained(FocusEvent arg0) {
				if (estadosTextField.getText().equals(TEXTO_ESTADOS))
					estadosTextField.setText("");
			}
		});
		estadosTextField.setText(TEXTO_ESTADOS);
		estadosTextField.setBounds(20, 95, 339, 20);
		panelConfiguracao.add(estadosTextField);

		final JLabel vazioLabel = new JLabel();
		vazioLabel.setText("Vazio:");
		vazioLabel.setBounds(375, 33, 54, 14);
		panelConfiguracao.add(vazioLabel);

		final JLabel inicialLabel = new JLabel();
		inicialLabel.setText("Inicial");
		inicialLabel.setBounds(375, 97, 54, 14);
		panelConfiguracao.add(inicialLabel);

		vazioTextField = new JTextField();
		vazioTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				if (vazioTextField.getText().length() > 1)
					vazioTextField.setText("");
				else if (!vazioTextField.getText().equals("")) {	
					Collection alfabeto = separaElementos(alfabetoTextField.getText());
					
					for (Object object : alfabeto) {
						String caractere = (String)object;
						if (caractere.equals(vazioTextField.getText())) {
							vazioTextField.setText("");
							return;
						}
					}
				}
			}
		});
		vazioTextField.setBounds(413, 30, 79, 20);
		panelConfiguracao.add(vazioTextField);

		inicialComboBox = new JComboBox();
		inicialComboBox.setBounds(413, 93, 79, 22);
		panelConfiguracao.add(inicialComboBox);

		fitaTextField = new JTextField();
		fitaTextField.setText("Entre aqui os caracteres complementares da Fita de Execução");
		fitaTextField.setBounds(20, 210, 472, 20);
		panelConfiguracao.add(fitaTextField);

		final JLabel finaisLabel = new JLabel();
		finaisLabel.setText("Final(is):");
		finaisLabel.setBounds(20, 129, 54, 14);
		panelConfiguracao.add(finaisLabel);

		finaisTextField = new JTextField();
		finaisTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				if (finaisTextField.getText().equals(""))
					finaisTextField.setText(TEXTO_FINAIS);
				else {
					Collection finais = separaElementos(finaisTextField.getText());
					Collection estados = separaElementos(estadosTextField.getText());
					
					String novosFinais = null;
					for (Object object : finais) {
						String estFinal = (String)object;
						if (estados.contains(estFinal))
							if (novosFinais == null)
								novosFinais = estFinal;
							else
								novosFinais += ", ".concat(estFinal);
					}
						
					if (novosFinais == null)
						finaisTextField.setText(TEXTO_FINAIS);
					else
						finaisTextField.setText(novosFinais);
				}
			}
			public void focusGained(FocusEvent arg0) {
				if (finaisTextField.getText().equals(TEXTO_FINAIS))
					finaisTextField.setText("");
			}
		});
		finaisTextField.setText(TEXTO_FINAIS);
		finaisTextField.setBounds(30, 149, 329, 20);
		panelConfiguracao.add(finaisTextField);

		final JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 260, 482, 20);
		panelConfiguracao.add(separator_3);

		final JLabel alfabetoLabel = new JLabel();
		alfabetoLabel.setText("Alfabeto:");
		alfabetoLabel.setBounds(10, 10, 54, 14);
		panelConfiguracao.add(alfabetoLabel);

		final JLabel estadosLabel = new JLabel();
		estadosLabel.setText("Estados:");
		estadosLabel.setBounds(10, 75, 54, 14);
		panelConfiguracao.add(estadosLabel);

		final JLabel fitaLabel = new JLabel();
		fitaLabel.setText("Fita:");
		fitaLabel.setBounds(10, 190, 54, 14);
		panelConfiguracao.add(fitaLabel);

		final JSeparator separator_3_1 = new JSeparator();
		separator_3_1.setBounds(10, 184, 482, 20);
		panelConfiguracao.add(separator_3_1);

		final JSeparator separator_3_2 = new JSeparator();
		separator_3_2.setBounds(10, 65, 482, 20);
		panelConfiguracao.add(separator_3_2);

		final JButton okConfigButton = new JButton();
		okConfigButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (alfabetoTextField.getText().equals(TEXTO_ALFABETO)) {
					JOptionPane.showMessageDialog(null, "Preencha o campo de alfabeto", "Configuração Incompleta",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				alfabeto = separaElementos(alfabetoTextField.getText());
				if (vazioTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Preencha o campo de símbolo Vazio", "Configuração Incompleta",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				vazio = vazioTextField.getText();
				if (estadosTextField.getText().equals(TEXTO_ESTADOS)) {
					JOptionPane.showMessageDialog(null, "Preencha o campo de estados", "Configuração Incompleta",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				estados = separaElementos(estadosTextField.getText());
				if (finaisTextField.getText().equals(TEXTO_FINAIS)) {
					JOptionPane.showMessageDialog(null, "Preencha o campo de estados finais", "Configuração Incompleta",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				finais = separaElementos(finaisTextField.getText());
				inicial = (String)inicialComboBox.getSelectedItem();
				
				Collection colHeader = new LinkedHashSet();
				colHeader.add("Simbolo\\Estado");
				colHeader.addAll(separaElementos(estadosTextField.getText()));
				header = colHeader.toArray();
				
				Collection tmpAlfabeto = separaElementos(alfabetoTextField.getText());
				tmpAlfabeto.add(vazioTextField.getText());
				Object arrayAlfabeto[] = tmpAlfabeto.toArray();
				
				data = new String[arrayAlfabeto.length][];
				for (int i = 0; i < arrayAlfabeto.length; ++i) {
					data[i] = new String[header.length];
					data[i][0] = (String)arrayAlfabeto[i];
					
					for (int j = 1; j < header.length; ++j)
						data[i][j] = new String();
				}
				
				table = new JTable(new AbstractTableModel() {
					public Object getValueAt(int row, int col) {
						return data[row][col];
					}
				
					public int getRowCount() {
						return data.length;
					}
				
					public int getColumnCount() {
						return header.length;
					}
				
					public void setValueAt(Object value, int row, int col) {
						Object entrada[] = separaElementos((String) value).toArray();
						
						if (!alfabeto.contains(entrada[0]) && !vazio.equals(entrada[0]))
							return;
						if (!estados.contains(entrada[1]))
							return;
						if (!"D".equalsIgnoreCase((String)entrada[2]) && !"E".equalsIgnoreCase((String)entrada[2]))
							return;
						
						data[row][col] = (String)value;
					}
				
					public boolean isCellEditable(int row, int col) {
						return (col != 0);
					}
				
					public String getColumnName(int col) {
						return (String)header[col];
					}
				
				});
				scrollPane.setViewportView(table);
				
				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setEnabledAt(2, false);
				tabbedPane.setSelectedIndex(1);
			}
		});
		okConfigButton.setText("Ok");
		okConfigButton.setBounds(428, 286, 65, 23);
		panelConfiguracao.add(okConfigButton);
		
		panelFuncoes = new JPanel();
		panelFuncoes.setLayout(null);
		tabbedPane.addTab("Funções", null, panelFuncoes, "");

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 483, 274);
		panelFuncoes.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		final JLabel explFuncoesLabel = new JLabel();
		explFuncoesLabel.setText("Entre com as funções no formato: [símbolo a escrever],  [prox. estado],  [D ou E].");
		explFuncoesLabel.setBounds(10, 290, 483, 32);
		panelFuncoes.add(explFuncoesLabel);

		final JButton okFuncoesButton = new JButton();
		okFuncoesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setEnabledAt(2, true);
				tabbedPane.setSelectedIndex(2);
			}
		});
		okFuncoesButton.setText("Ok");
		okFuncoesButton.setBounds(428, 328, 65, 23);
		panelFuncoes.add(okFuncoesButton);

		panelSimulacao = new JPanel();
		panelSimulacao.setLayout(null);
		tabbedPane.addTab("Simulação", null, panelSimulacao, "");

		final JLabel simularLabel = new JLabel();
		simularLabel.setText("SIMULAR");
		simularLabel.setBounds(237, 169, 54, 14);
		panelSimulacao.add(simularLabel);
		
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
	}
	
	private Collection separaElementos(String string) {
		String elementos[] = string.split(",");
		Collection colecao = new LinkedHashSet();
		for (int i = 0; i < elementos.length; ++i)
			colecao.add(elementos[i].trim());
		
		return colecao;
	}

}

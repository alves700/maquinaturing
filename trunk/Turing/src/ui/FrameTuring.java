package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.SSLEngineResult.Status;
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
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import turing.Fita;
import turing.Maquina;

public class FrameTuring {

	private static final String TEXTO_FUNCOES = "Entre com as funções no formato: [símbolo a escrever]  [prox. estado]  [D ou E].";
	private static final String TEXTO_FITA = "Entre aqui os caracteres complementares da Fita de Execução, separando-os por ' '";
	private JTable table;
	JScrollPane scrollPane;
	private static final String TEXTO_FINAIS = "Entre aqui o(s) estado(s) final(is), separando-os por ' '";
	private JTextField finaisTextField;
	private JTextField fitaTextField;
	private static final String TEXTO_ALFABETO = "Entre aqui o alfabeto, separando os caracteres por ' '";
	private static final String TEXTO_ESTADOS = "Entre aqui os estados, separando os elementos por ' '";
	private JComboBox inicialComboBox;
	private JTextField vazioTextField;
	private JTextField estadosTextField;
	private JTextField alfabetoTextField;
	private JLabel statusShowLabel;
	JTextPane simulacaoTextPane;
	private JFrame frame;
	JButton stepButton;
	JButton rodaButton;
	
	JPanel panelConfiguracao;
	JPanel panelFuncoes;
	JPanel panelSimulacao;
	private Object[] header;
	private String[][] data;
	Timer timer;
	
	private Collection alfabeto;
	private String vazio;
	
	private Collection estados;
	private String inicial;
	private Collection finais;
	protected Maquina maquina;
	protected String fita;
	protected String estadoInicial;

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
					Collection alfabeto = separaElementos(alfabetoTextField.getText(), false);
					
					String novoAlfabeto = null;
					for (Object object : alfabeto) {
						String caractere = (String)object;
						if (caractere.length() != 1){
							
						}
						else
							if (novoAlfabeto == null)
								novoAlfabeto = caractere;
							else
								novoAlfabeto += " ".concat(caractere);
					}
						
					
					alfabetoTextField.setText(novoAlfabeto);
					alfabeto = separaElementos(alfabetoTextField.getText(), false);
					
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
					Collection estados = separaElementos(estadosTextField.getText(), false);
					
					String novosEstados = null;
					for (Object object : estados) {
						String estado = (String)object;
						if (novosEstados == null)
							novosEstados = estado;
						else
							novosEstados += " ".concat(estado);
					}
						
					
					estadosTextField.setText(novosEstados);
					estados = separaElementos(estadosTextField.getText(), false);
					
					for (Object caractere : estados)
						inicialComboBox.addItem(caractere);
					
					////////////////////////
					Collection finais = separaElementos(finaisTextField.getText(), false);
					
					String novosFinais = null;
					for (Object object : finais) {
						String estFinal = (String)object;
						if (estados.contains(estFinal))
							if (novosFinais == null)
								novosFinais = estFinal;
							else
								novosFinais += " ".concat(estFinal);
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
					Collection alfabeto = separaElementos(alfabetoTextField.getText(), false);
					
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
		fitaTextField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				if (fitaTextField.getText().equals(TEXTO_FITA))
					fitaTextField.setText("");
			}
			public void focusLost(FocusEvent arg0) {
				if (fitaTextField.getText().equals(""))
					fitaTextField.setText(TEXTO_FITA);
				else {
					Collection alfabeto = separaElementos(alfabetoTextField.getText(), false);
					if (!vazioTextField.equals(""))
						alfabeto.add(vazioTextField.getText());
					
					Collection fita = separaElementos(fitaTextField.getText(), true);
					
					String novaFita = null;
					for (Object object : fita) {
						String letra = (String)object;
						if (alfabeto.contains(letra))
							if (novaFita == null)
								novaFita = letra;
							else
								novaFita += " ".concat(letra);
					}
						
					if (novaFita == null)
						fitaTextField.setText(TEXTO_FITA);
					else
						fitaTextField.setText(novaFita);
				}
			}
		});
		fitaTextField.setText(TEXTO_FITA);
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
					Collection finais = separaElementos(finaisTextField.getText(), false);
					Collection estados = separaElementos(estadosTextField.getText(), false);
					
					String novosFinais = null;
					for (Object object : finais) {
						String estFinal = (String)object;
						if (estados.contains(estFinal))
							if (novosFinais == null)
								novosFinais = estFinal;
							else
								novosFinais += " ".concat(estFinal);
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
				alfabeto = separaElementos(alfabetoTextField.getText(), false);
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
				estados = separaElementos(estadosTextField.getText(), false);
				if (finaisTextField.getText().equals(TEXTO_FINAIS)) {
					JOptionPane.showMessageDialog(null, "Preencha o campo de estados finais", "Configuração Incompleta",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(fitaTextField.getText().equals(TEXTO_FITA))
					fita = "";
				else
					fita = fitaTextField.getText().trim();
				
				finais = separaElementos(finaisTextField.getText(), false);
				inicial = (String)inicialComboBox.getSelectedItem();
				
				Collection colHeader = new LinkedHashSet();
				colHeader.add("Simbolo\\Estado");
				colHeader.addAll(separaElementos(estadosTextField.getText(), false));
				header = colHeader.toArray();
				
				Collection tmpAlfabeto = separaElementos(alfabetoTextField.getText(), false);
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
						if ( !value.equals("")) {
							Object entrada[] = separaElementos((String) value, true).toArray();
							if (entrada.length != 3)
								return;
							if (!alfabeto.contains(entrada[0]) && !vazio.equals(entrada[0]))
								return;
							if (!estados.contains(entrada[1]))
								return;
							if (!"D".equalsIgnoreCase((String)entrada[2]) && !"E".equalsIgnoreCase((String)entrada[2]))
								return;
						}
						
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
		explFuncoesLabel.setText(TEXTO_FUNCOES);
		explFuncoesLabel.setBounds(10, 290, 483, 32);
		panelFuncoes.add(explFuncoesLabel);

		final JButton okFuncoesButton = new JButton();
		okFuncoesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.editCellAt(0, 0);
				tabbedPane.setEnabledAt(2, true);
				tabbedPane.setSelectedIndex(2);
				stepButton.setEnabled(true);
				rodaButton.setEnabled(true);
				statusShowLabel.setText("");
				
				simulacaoTextPane.setText("");
				
				Object[] arrayEstados = estados.toArray();
				
				Collection alfa = new LinkedHashSet();
				alfa.addAll(alfabeto);
				alfa.add(vazio);
				Object[] arrayAlfabeto = alfa.toArray();
				
				maquina = new Maquina(fita.trim().replace(" ", ""), vazio.charAt(0), inicial);
				
				for (int i = data.length; --i >= 0;)
					for (int j = data[i].length; --j > 0;) {
						String value = data[i][j];
						if (!value.equals("")) {
							Object funcao[] = separaElementos(value, true).toArray();
							try{
							maquina.insereFuncao((String)arrayEstados[j-1], ((String)arrayAlfabeto[i]).charAt(0),
									(String)funcao[1], ((String)funcao[0]).charAt(0),
									(((String)funcao[2]).toUpperCase().equals("D")?Fita.DIREITA:Fita.ESQUERDA));
							} catch(Exception e){
								System.out.println(e.getMessage());
							}
						}
					}
				
				for (Object value : finais)
					maquina.insereEstadoFinal((String)value);
			}
		});
		okFuncoesButton.setText("Ok");
		okFuncoesButton.setBounds(428, 328, 65, 23);
		panelFuncoes.add(okFuncoesButton);

		panelSimulacao = new JPanel();
		panelSimulacao.setLayout(null);
		tabbedPane.addTab("Simulação", null, panelSimulacao, "");

		simulacaoTextPane = new JTextPane();
		simulacaoTextPane.setEditable(false);
		simulacaoTextPane.setBounds(10, 10, 483, 280);
		panelSimulacao.add(simulacaoTextPane);
		
		stepButton = new JButton();
		rodaButton = new JButton();
		rodaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long tempo = -1;
				while ( tempo < 0 ) {
					try {
						tempo = Long.parseLong(JOptionPane.showInputDialog(frame, String.valueOf(100), "Entre com o intervalo de tempo entre os steps automáticos",
						JOptionPane.PLAIN_MESSAGE));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame, "Parâmetro passado não é um número positivo",
								"Erro de entrada", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				TimerTask task = new TimerTask(){
					public void run() {
						stepMachine();
					}
				};
				
				timer = new Timer();
				timer.scheduleAtFixedRate( task, tempo, tempo );
			}
		});
		rodaButton.setText("Roda");
		rodaButton.setBounds(398, 336, 95, 23);
		panelSimulacao.add(rodaButton);
		
		stepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stepMachine();
			}
		});
		stepButton.setText("Step");
		stepButton.setBounds(291, 336, 95, 23);
		panelSimulacao.add(stepButton);

		final JLabel statusLabel = new JLabel();
		statusLabel.setText("Status:");
		statusLabel.setBounds(10, 296, 54, 14);
		panelSimulacao.add(statusLabel);

		statusShowLabel = new JLabel();
		statusShowLabel.setBounds(70, 296, 423, 14);
		panelSimulacao.add(statusShowLabel);
		
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
	}
	
	private void stepMachine() {
		int ret = maquina.step();
		
		Collection fita = maquina.pegaFita();
		String strFita = "";
		
		int i = 0;
		for (Object object : fita) {
			++i;
			
			if ( i != 1 )
				strFita += ".";
			
			if ( i == maquina.pegaLeitor() )
				strFita += "[" + (Character)object + "]";
			else
				strFita += " " + (Character)object + " ";
		}
		
		if (!simulacaoTextPane.getText().equals(""))
			simulacaoTextPane.setText(simulacaoTextPane.getText() + "\n" + strFita);
		else
			simulacaoTextPane.setText(strFita);
		
		statusShowLabel.setText(Maquina.RETORNOS[ret]);
		
		if (ret != 0) {
			stepButton.setEnabled(false);
			rodaButton.setEnabled(false);
			if ( timer != null )
				timer.cancel();
		}
	}
	
	private Collection separaElementos(String string, boolean repeticao) {
		String elementos[] = string.split(" ");
		Collection colecao = (repeticao? new LinkedList():new LinkedHashSet());
		for (int i = 0; i < elementos.length; ++i)
			colecao.add(elementos[i].trim());
		
		return colecao;
	}

}

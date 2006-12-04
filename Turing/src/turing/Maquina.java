package turing;

import java.util.Collection;


public class Maquina {
	public static final String[] RETORNOS = {"Pode continuar executando",
						 "A máquina parou! O estado não é final!",
						 "A máquina parou! o estado é final!",
						 "Não foi possível escrever! o leitor é negativo",
						 "Entrou em loop..."};
	
	public static final int ESTADO_VALIDO = 0;
	public static final int ESTADO_NAO_FINAL = 1;
	public static final int ESTADO_FINAL = 2;
	public static final int POSICAO_INVALIDA = 3;
	public static final int LOOP_INFINITO = 4;

	private Fita fita;
	private TabelaEstados estados;
	private TabelaLoop loop;
	
	private Estado estadoAtual;
	
	public Maquina(String fita, char vazio, String inicial) {
		this.fita = new Fita(fita, vazio);
		estados = new TabelaEstados();
		loop = new TabelaLoop();
		estadoAtual = new Estado("pre-estado", ' ', inicial, 0);
	}
	
	public void insereFuncao( String estadoAtual, char caracterLido, String estadoNovo,
						 char caracterNovo, int direcao) {
		estados.insereProducao(estadoAtual, caracterLido, estadoNovo, caracterNovo,  direcao);
	}
	
	public void insereEstadoFinal(String nomeEstado) {
		estados.insereEstadoFinal(nomeEstado);
	}
	
	/**
	 * one step at the TM
	 * @return true if you should continue stepping, false in case of break
	 */
	public int step() {
		
		String nomeEstado = estadoAtual.pegaProximoEstado();

		estadoAtual = estados.pegaEstado(estadoAtual.pegaProximoEstado(), fita.pegaCaracterApontado());

		
		if (estadoAtual == null) {
			if (estados.estadoEFinal(nomeEstado))
				return 2;
			return 1;
		}
		
		if( isLooping() )
			return 4;
		
		
		if ( !fita.escreve( estadoAtual.pegaCaracter(), estadoAtual.pegaDirecao() ) ){
			return 3;
		}		
		return 0;
	}

	private boolean isLooping() {
		return loop.verificaTemDuplicado(estadoAtual.pegaNome(), fita.pegaLeitor(), fita.pegaLista().toString());
	}

	public Collection pegaFita() {
		return fita.pegaLista();
	}

	public String pegaEstado() {
		return estadoAtual.pegaNome();
	}

	public int pegaLeitor() {
		return fita.pegaLeitor();
	}
}

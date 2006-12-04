package turing;

import java.util.HashMap;
import java.util.HashSet;


public class TabelaEstados {

	HashMap tabelaEstados;
	HashSet estadosFinais;
	
	public TabelaEstados() {
		tabelaEstados = new HashMap();
		estadosFinais = new HashSet();
	}
	
	public void insereProducao( String estadoAtual, char caracterAtual,
								String estadoNovo, char caracterNovo, int direcao) {
		
		HashMap tabelaAux;
		if ( (tabelaAux = (HashMap)tabelaEstados.get(estadoAtual)) == null)
			tabelaEstados.put(estadoAtual, tabelaAux = new HashMap());
		
		if (!tabelaAux.containsKey(new Character(caracterAtual)) ) {
			Estado e = new Estado( estadoAtual, caracterNovo, estadoNovo,
					 direcao);
			tabelaAux.put(new Character(caracterAtual), e);
		}
			
	}
	
	public void insereEstadoFinal(String nomeEstado){
		estadosFinais.add(nomeEstado);
	}
	
	public boolean estadoEFinal( String nomeEstado ) {
		return estadosFinais.contains(nomeEstado);
	}
	
	public Estado pegaEstado( String estadoAtual, char caracterAtual) {
		
		if (tabelaEstados.containsKey(estadoAtual)) {
			return (Estado) ((HashMap) tabelaEstados.get(estadoAtual) ).get(new Character(caracterAtual));
		}
		
		return null;
	}
}

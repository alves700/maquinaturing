package turing;

import java.util.HashMap;
import java.util.HashSet;

public class TabelaLoop {
	private HashMap tabelaNome;

	public TabelaLoop() {
		tabelaNome = new HashMap();
	}
	
	public boolean verificaTemDuplicado(String nomeEstado, int posicao, String fita) {
		
		HashMap tabelaPosicao;
		if ( tabelaNome.containsKey(nomeEstado) ) 
			tabelaPosicao = (HashMap) tabelaNome.get(nomeEstado);
		else 
			tabelaNome.put(nomeEstado, tabelaPosicao = new HashMap()); 
		
		HashSet tabelaFita;
		if ( tabelaPosicao.containsKey(new Integer(posicao)) ) 
			tabelaFita = (HashSet) tabelaPosicao.get(new Integer(posicao));
		else 
			tabelaPosicao.put(new Integer(posicao), tabelaFita = new HashSet()); 
		
		if (tabelaFita.contains(fita))
			return true;
		
		tabelaFita.add(fita);
		return false;
	}
}

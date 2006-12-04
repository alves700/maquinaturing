package turing;

import java.util.Collection;
import java.util.Vector;


public class Fita {

	private Vector lista;
	private int leitor;
	private char vazio;
	
	public static final int DIREITA = 1;
	public static final int ESQUERDA = -1;

	public Fita(String _lista, char vazio) {	
		leitor = 0;
		this.vazio = vazio;
		lista = new Vector();
		for ( int  i = 0; i < _lista.length(); i++ ) {
			lista.add(new Character(_lista.charAt(i)));
		}
	}
	
	public boolean escreve(char novoDado, int direcao) {
		if (leitor < 0)
			return false;
		if (lista.size() > leitor)
			lista.remove(leitor);
		lista.add(leitor, new Character(novoDado));
		leitor += direcao;
		return true;
	}

	public int pegaLeitor() {
		return leitor;
	}
	
	public Collection pegaLista() {
		return lista;
	}

	public char pegaPrimeiroCaracter() {
		return ((Character)lista.get(0)).charValue() ;
	}
	
	public char pegaCaracterApontado() {
		if (lista.size() > leitor) 
			return ((Character)lista.get(leitor)).charValue() ;
		else
			return vazio;
	}
}

package turing;

public class Estado {
	
	
	private String nomeEstado;
	private char caracter;
	private String proximoEstado;	
	private int direcao;
	
	private boolean estadoFinal;
	
	public Estado() {
		caracter = ' ';
		direcao = Fita.DIREITA;
		estadoFinal = false;
	}
	
	public Estado(String _nome, char _caracter, String _estado, int _direcao) {
		nomeEstado = _nome;
		caracter = _caracter;
		proximoEstado = _estado;
		direcao = _direcao;
	}
	
	public char pegaCaracter() {
		return caracter;
	}
	
	public String pegaNome() {
		return nomeEstado;
	}
	
	public String pegaProximoEstado() {
		return proximoEstado;
	}

	public int pegaDirecao() {
		return direcao;
	}
	
	private boolean isfinal() {
		return estadoFinal;
	}
	
}

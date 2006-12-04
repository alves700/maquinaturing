package turing;

public class SeiLah {

	public static void main(String[] args) {
		Maquina turing;
		String fita = "Iab#abab";
		turing = new Maquina(fita, ' ', "q0");
		int msg;
		
		/*
		turing.insereFuncao ("q0", 'I', "q0", 'I', Fita.DIREITA, false);
		turing.insereFuncao ("q0", 'a', "q1", 'b', Fita.DIREITA, false);
		turing.insereFuncao ("q0", 'b', "q1", 'a', Fita.DIREITA, false);
		turing.insereFuncao ("q1", 'b', "q0", 'a', Fita.ESQUERDA, false);
		turing.insereFuncao ("q1", 'a', "q0", 'b', Fita.ESQUERDA, false);
		*/
		turing.insereFuncao("q0", 'I', "q0", 'I', Fita.DIREITA);
		turing.insereFuncao("q0", 'a', "q0", 'a', Fita.DIREITA);
		turing.insereFuncao("q0", 'b', "q0", 'b', Fita.DIREITA);
		turing.insereFuncao("q0", '#', "q0", '#', Fita.DIREITA);
		turing.insereFuncao("q0", ' ', "q1", ' ', Fita.ESQUERDA);
		
		turing.insereFuncao("q1", 'a', "q2", ' ', Fita.ESQUERDA);
		turing.insereFuncao("q1", 'b', "q3", ' ', Fita.ESQUERDA);
		turing.insereFuncao("q1", '#', "q4", ' ', Fita.ESQUERDA);
		
		turing.insereFuncao("q2", 'a', "q2", 'a', Fita.ESQUERDA);
		turing.insereFuncao("q2", 'b', "q3", 'a', Fita.ESQUERDA);
		turing.insereFuncao("q2", '#', "q4", 'a', Fita.ESQUERDA);
		
		turing.insereFuncao("q3", 'a', "q2", 'b', Fita.ESQUERDA);
		turing.insereFuncao("q3", 'b', "q3", 'b', Fita.ESQUERDA);
		turing.insereFuncao("q3", '#', "q4", 'b', Fita.ESQUERDA);
		
		turing.insereEstadoFinal("q4");
		
		do {
			msg = turing.step();
			if (msg == 0)
				System.out.println( Maquina.RETORNOS[msg] + " " + turing.pegaFita() + " " + turing.pegaEstado()
									+ " " + turing.pegaLeitor());
			else
				System.out.println(Maquina.RETORNOS[msg] + " "+  turing.pegaFita() + " " + turing.pegaLeitor());
		} while (msg == 0);
	}

}

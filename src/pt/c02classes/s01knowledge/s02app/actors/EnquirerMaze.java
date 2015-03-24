package pt.c02classes.s01knowledge.s02app.actors;

import java.util.ArrayList;

import java.util.Stack;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {
	
	/* Classe Posicao guarda os valores de linha e coluna */
	class Posicao {
	    int linha;
	    int coluna;

	    Posicao(int lin, int col) {
	        linha = lin;
	        coluna = col;
	    }
	}
	
	IResponder responder;
	
	/* posicao guardara as posicoes em que o usuario ja passou */
	ArrayList<Posicao> posicao = new ArrayList<Posicao>();
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	/* Resolvendo o labirinto */
	public boolean discover() {
		ArrayList<String> Move = new ArrayList<String>();
		
		/* Pilha que guarda o caminho percorrido pelo usuario */
		Stack<Posicao> pilha = new Stack<Posicao>();
		
		boolean saida = false, moveu = false;
		
		/* posAtual sempre guarda a posicao em que o usuario se encontra */
		Posicao posAtual = new Posicao(0, 0);
		Posicao posAux;
		
		posicao.add(posAtual);
		
		int i;
		
		Move.add("leste");
		Move.add("sul");
		Move.add("oeste");
		Move.add("norte");
		
		/* Encontrando a saida */
		while (!saida) {
			/* Tentando andar em todas as direcoes */
			for (i = 0; (i < 4) && (!saida); i++) {
				/* Verificando se ainda nao passou pela posicao na qual estamos
				 * tentando andar */
				if (!Passou(Move.get(i), posAtual, posicao)) {
					
					/* Tentando se mover para algum dos lados */
					moveu = responder.move(Move.get(i));
					
					/* Caso tenhamos conseguido se mover, sua posicao atual sera
					 * atualizada */
					if (moveu) {
						switch (Move.get(i)) {
							case "norte":
								posAtual = new Posicao(posAtual.linha-1, posAtual.coluna);
								posicao.add(posAtual);
								break;
							case "sul":
								posAtual = new Posicao(posAtual.linha+1, posAtual.coluna);
								posicao.add(posAtual);
								break;
							case "leste":
								posAtual = new Posicao(posAtual.linha, posAtual.coluna+1);
								posicao.add(posAtual);
								break;
							case "oeste":
								posAtual = new Posicao(posAtual.linha, posAtual.coluna-1);
								posicao.add(posAtual);
								break;
						}
						
						System.out.println(Move.get(i));
						
						/* Colocando a posicao atual no topo da pilha */
						pilha.push(posAtual);
						
						i = -1;
						
						/* Verificando o caso de ter alcancado a saida */
						if (responder.ask("aqui") == "saida") {
							if (responder.finalAnswer("cheguei"))
								System.out.println("Você encontrou a saida!");
							else
								System.out.println("Fuém fuém fuém!");
							
							return true;
						}
					}
				}
			}
			
			/* Caso nao tenhamos andado em nenhuma direcao, tiramos o
			 * valor do topo da pilha e atualizamos a posicao atual */
			if ((i == 4) && (!moveu)) {
				posAux = new Posicao(posAtual.linha, posAtual.coluna);
				pilha.pop();
				posAtual = pilha.peek();
				Volta(posAtual.linha - posAux.linha, posAtual.coluna - posAux.coluna);
			}
		}
		
		return true;
		
	}
	
	/* Verificanso se o usuario ja passou por determinada posicao */
	Boolean Passou(String sentido, Posicao posAtual, ArrayList<Posicao> posicao) {
		int i;
		
		/* Atualizando a futura posicao e verificando se ja foi passado por ela */
		switch (sentido) {
			case "norte":
				Posicao posFutura = new Posicao(posAtual.linha-1, posAtual.coluna);
				for (i = 0; i < posicao.size(); i++)
					if ((posicao.get(i).linha == posFutura.linha)
						&& (posicao.get(i).coluna == posFutura.coluna))
						return true;
				break;

			case "sul":
				posFutura = new Posicao(posAtual.linha+1, posAtual.coluna);
				for (i = 0; i < posicao.size(); i++)
					if ((posicao.get(i).linha == posFutura.linha)
						&& (posicao.get(i).coluna == posFutura.coluna))
						return true;
				break;

			case "leste":
				posFutura = new Posicao(posAtual.linha, posAtual.coluna+1);
				for (i = 0; i < posicao.size(); i++)
					if ((posicao.get(i).linha == posFutura.linha)
						&& (posicao.get(i).coluna == posFutura.coluna))
						return true;
				break;

			case "oeste":
				posFutura = new Posicao(posAtual.linha, posAtual.coluna-1);
				for (i = 0; i < posicao.size(); i++)
					if ((posicao.get(i).linha == posFutura.linha)
						&& (posicao.get(i).coluna == posFutura.coluna))
						return true;
				break;
		}
		
		return false;
	}
	
	void Volta(int posVert, int posHoriz) {
		String movimento = null;
		
		switch (posVert) {
			case 1:
				movimento = "norte";
				break;
			case -1:
				movimento = "sul";
				break;
		}
		
		switch (posHoriz) {
			case 1:
				movimento = "leste";
				break;
			case -1:
				movimento = "oeste";
				break;
		}
		
		System.out.println(movimento);
		
		responder.move(movimento);
	}
}
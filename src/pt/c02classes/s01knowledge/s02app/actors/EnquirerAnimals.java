package pt.c02classes.s01knowledge.s02app.actors;

import java.util.Hashtable;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IDeclaracao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerAnimals implements IEnquirer {

	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		/* Criando hashtable que armazenara as perguntas ja feitas e suas
	     * respectivas respostas */
		Hashtable<String, String> PergResp = new Hashtable<String, String>();
	    
		IBaseConhecimento bc = new BaseConhecimento();
	    
	    IObjetoConhecimento obj;
		
		bc.setScenario("animals");
		
        /* Obtem a lista dos animais da base de dados */
        String listaAnimais[] = bc.listaNomes();
        
        /* Quando o animal pensado for descoberto "acertei" se torna verdadeiro */
        boolean acertei = false;
    	
        /* decl guarda as perguntas e respostas sobre o animal chutado pelo
         * entrevistador */
		IDeclaracao decl;
        
		/* Descobrindo qual o animal pensado pelo entrevistado */
        for (int animal = 0; (!acertei) && (animal < listaAnimais.length); animal++) {
			obj = bc.recuperaObjeto(listaAnimais[animal]);
			
			/* Obtendo informacoes da primeira pergunta referente ao animal */
			decl = obj.primeira();
			
			/* animalEsperado é utilizado como flag. Caso o animal esperado nao seja
	         * o animal pensado seu valor se torna falso para sair do loop e tentar
	         * outro animal da lista */
	        boolean animalEsperado = true;
	        
	        /* Analisando todas as perguntas referentes ao animal esperado */
			while ((decl != null) && (animalEsperado)) {
				String pergunta = decl.getPropriedade();
				String respostaEsperada = decl.getValor();
				
				boolean jaRespondeu = false;
				
				/* Analisando se a proxima pergunta referente ao animal ja foi feita
				 * anteriormente */
				if (PergResp.get(pergunta) != null)
					jaRespondeu = true;
				
				/* Adicionando a pergunta feita e sua resposta a Hashtable */
				if (jaRespondeu == false) {
					String resposta = responder.ask(pergunta);
					PergResp.put(pergunta, respostaEsperada);
					
					/* Observando se a resposta dada eh a mesma que a resposta esperada */
					if (resposta.equalsIgnoreCase(respostaEsperada))
						decl = obj.proxima();
					else
						animalEsperado = false;
				}
				
				/* Analisando o caso de a pergunta ja ter sido feita */
				else {
					if (PergResp.get(pergunta).equalsIgnoreCase(respostaEsperada))
						decl = obj.proxima();
					else
						animalEsperado = false;
				}
			}
			
			/* Sendo o animal esperado o mesmo que o animal pensado pelo entrevistador,
			 * esse animal é dado como resposta final */
			if (animalEsperado == true)
				acertei = responder.finalAnswer(listaAnimais[animal]);
		
        }
        
        /* Analisando os casos de ter acetado ou nao a resposta final */
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");
		
		return acertei;
	}

}
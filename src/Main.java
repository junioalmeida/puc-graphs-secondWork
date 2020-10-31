public class Main {

	public static void main(String[] args) {
		
		Graph graph;
		String vi, vf;
		int value;
		int op = 0;
		boolean condition;
		
		try {
			graph = new Graph();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		while (op != 16) {

			try {
				op = UI.menu();

				UI.printNewLine();

				switch (op) {
					case 1 -> {
						vi = UI.readString("Nome do novo v�rtice: ");
						
						graph.insertVertex(vi);
						UI.print("V�rtice inserido com sucesso!");
					}
	
					case 2 -> {
						vi = UI.readString("Nome do v�rtice: ");
						
						graph.removeVertex(vi);
						UI.print("V�rtice removido com sucesso!");
					}
	
					case 3 -> {
						vi = UI.readString("V�rtice inicial: ");
						vf = UI.readString("V�rtice final: ");
						value = UI.readInt("Peso: ");
	
						graph.insertEdge(vi, vf, value);
						UI.print("Aresta inserida com sucesso!");
					}
	
					case 4 -> {
						vi = UI.readString("V�rtice inicial: ");
						vf = UI.readString("V�rtice final: ");
	
						graph.removeEdge(vi, vf);
						UI.print("Aresta removida com sucesso!");
					}
	
					case 5 -> {
						vi = UI.readString("V�rtice inicial: ");
						vf = UI.readString("V�rtice final: ");
	
						if (graph.edgeExists(vi, vf)) {
							UI.printf("Os v�rtices %s e %s s�o adjacentes.", vi, vf);
						} else {
							UI.printf("Os v�rtices %s e %s n�o s�o adjacentes.", vi, vf);
						}
					}
	
					case 6 -> {
						graph.showDegrees();
					}
	
					case 7 -> {
						graph.showGraph();
					}
	
					case 8 -> {
						condition = graph.isEulerian();
						UI.printf("O grafo%s � Euleriano.", condition ? "" : " N�O");
					}
	
					case 9 -> {
						condition = graph.isComplete();
						UI.printf("O grafo%s � completo.", condition ? "" : " N�O");
					}
	
					case 10 -> {
						condition = graph.isNull();
						UI.printf("O grafo%s � totalmente desconexo.", condition ? "" : " N�O");
					}
	
					case 11 -> {
						graph.changeComplement();
						UI.print("Grafo complementar gerado com sucesso!");
					}
	
					case 12 -> {
						vi = UI.readString("V�rtice inicial: ");
						vf = UI.readString("V�rtice final: ");
						value = UI.readInt("Novo peso: ");
						
						graph.changeEdgeWeigth(vi, vf, value);
						UI.print("Peso alterado com sucesso!");
					}
	
					case 13 -> {
						graph.load();
						UI.print("Grafo carregado com sucesso!");
					}
	
					case 14 -> {
						graph.save();
						UI.print("Grafo salvo com sucesso!");
					}
	
					case 15 -> {
						graph.coloringHeuristic();
						UI.print("Heur�stica da colora��o do grafo executada com sucesso!");
					}
					
					case 16 -> {
						UI.println("Saindo da aplica��o...");
						Thread.sleep(2 * 1000);
					}
				}
				
				UI.printNewLine();

			} catch (Exception e) {
				UI.printf("\nTipo: %s\nErro: %s\n", e.getClass().getName(), e.getMessage());
				e.printStackTrace();
			}
		}

	}

}

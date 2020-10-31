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
						vi = UI.readString("Nome do novo vértice: ");
						
						graph.insertVertex(vi);
						UI.print("Vértice inserido com sucesso!");
					}
	
					case 2 -> {
						vi = UI.readString("Nome do vértice: ");
						
						graph.removeVertex(vi);
						UI.print("Vértice removido com sucesso!");
					}
	
					case 3 -> {
						vi = UI.readString("Vértice inicial: ");
						vf = UI.readString("Vértice final: ");
						value = UI.readInt("Peso: ");
	
						graph.insertEdge(vi, vf, value);
						UI.print("Aresta inserida com sucesso!");
					}
	
					case 4 -> {
						vi = UI.readString("Vértice inicial: ");
						vf = UI.readString("Vértice final: ");
	
						graph.removeEdge(vi, vf);
						UI.print("Aresta removida com sucesso!");
					}
	
					case 5 -> {
						vi = UI.readString("Vértice inicial: ");
						vf = UI.readString("Vértice final: ");
	
						if (graph.edgeExists(vi, vf)) {
							UI.printf("Os vértices %s e %s são adjacentes.", vi, vf);
						} else {
							UI.printf("Os vértices %s e %s não são adjacentes.", vi, vf);
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
						UI.printf("O grafo%s É Euleriano.", condition ? "" : " NÃO");
					}
	
					case 9 -> {
						condition = graph.isComplete();
						UI.printf("O grafo%s É completo.", condition ? "" : " NÃO");
					}
	
					case 10 -> {
						condition = graph.isNull();
						UI.printf("O grafo%s É totalmente desconexo.", condition ? "" : " NÃO");
					}
	
					case 11 -> {
						graph.changeComplement();
						UI.print("Grafo complementar gerado com sucesso!");
					}
	
					case 12 -> {
						vi = UI.readString("Vértice inicial: ");
						vf = UI.readString("Vértice final: ");
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
						UI.print("Heurística da coloração do grafo executada com sucesso!");
					}
					
					case 16 -> {
						UI.println("Saindo da aplicação...");
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

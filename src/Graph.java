import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Graph {

	private static final int MAX_SIZE = 10;
	private static final String FILE_NAME = "grafo.txt";

	private int[][] graph;
	private String[] vertex;
	private int[] colors;

	private int size;
	private boolean needCalculateColors = true;
	private int amountColors;

	public Graph() throws MaximumVextexException, IOException {
		load();
	}

	public void insertEdge(String nameInitialVertex, String nameFinalVertex, int value)
			throws EdgeException, NotFoundVextexException {
		int initialVertex, finalVertex;

		initialVertex = getIndexVertex(nameInitialVertex);
		finalVertex = getIndexVertex(nameFinalVertex);

		if (initialVertex == -1 || finalVertex == -1) {
			throw new NotFoundVextexException(String.format("O vértice \"%s\" não foi encontrado.",
					(initialVertex == -1 ? nameInitialVertex : nameFinalVertex)));
		}

		if (edgeExists(initialVertex, finalVertex)) {
			throw new EdgeException(String.format("Os vértices \"%s\" e \"%s\" já são adjacentes.",
					vertex[initialVertex], vertex[finalVertex]));
		}

		setValueAtPosition(initialVertex, finalVertex, value);
		needCalculateColors = true;
	}

	public void removeEdge(String nameInitialVertex, String nameFinalVertex)
			throws EdgeException, NotFoundVextexException {

		int initialVertex, finalVertex;

		initialVertex = getIndexVertex(nameInitialVertex);
		finalVertex = getIndexVertex(nameFinalVertex);

		if (initialVertex == -1 || finalVertex == -1) {
			throw new NotFoundVextexException(String.format("O vértice \"%s\" não foi encontrado.",
					(initialVertex == -1 ? nameInitialVertex : nameFinalVertex)));
		}

		if (!edgeExists(initialVertex, finalVertex)) {
			throw new EdgeException(String.format("Os vértices \"%s\" e \"%s\" não são adjacentes.",
					vertex[initialVertex], vertex[finalVertex]));
		}

		setValueAtPosition(initialVertex, finalVertex, 0);
		needCalculateColors = true;
	}

	public void changeEdgeWeigth(String nameInitialVertex, String nameFinalVertex, int value)
			throws EdgeException, NotFoundVextexException {

		int initialVertex, finalVertex;

		initialVertex = getIndexVertex(nameInitialVertex);
		finalVertex = getIndexVertex(nameFinalVertex);

		if (initialVertex == -1 || finalVertex == -1) {
			throw new NotFoundVextexException(String.format("O vértice \"%s\" não foi encontrado.",
					(initialVertex == -1 ? nameInitialVertex : nameFinalVertex)));
		}

		if (!edgeExists(initialVertex, finalVertex)) {
			throw new EdgeException(String.format("Os vértices \"%s\" e \"%s\" não são adjacentes.",
					vertex[initialVertex], vertex[finalVertex]));
		}

		if (value == 0) {
			throw new EdgeException("O peso da aresta não pode ser 0.");
		}

		setValueAtPosition(initialVertex, finalVertex, value);
	}

	private void setValueAtPosition(int initialVertex, int finalVertex, int value) {
		setValueJustAtPosition(initialVertex, finalVertex, value);
		setValueJustAtPosition(finalVertex, initialVertex, value);
	}

	private void setValueJustAtPosition(int initialVertex, int finalVertex, int value) {
		graph[initialVertex][finalVertex] = value;
	}

	public boolean edgeExists(String nameInitialVertex, String nameFinalVertex) throws NotFoundVextexException {
		int initialVertex, finalVertex;

		initialVertex = getIndexVertex(nameInitialVertex);
		finalVertex = getIndexVertex(nameFinalVertex);

		if (initialVertex == -1 || finalVertex == -1) {
			throw new NotFoundVextexException(String.format("O vértice \"%s\" não foi encontrado.",
					(initialVertex == -1 ? nameInitialVertex : nameFinalVertex)));
		}

		return edgeExists(initialVertex, finalVertex);
	}

	private boolean edgeExists(int initialVertex, int finalVertex) {
		return graph[initialVertex][finalVertex] > 0;
	}

	public void insertVertex(String name) throws MaximumVextexException {

		if (size >= MAX_SIZE) {
			throw new MaximumVextexException(
					"Não é possível inserir mais um vértice, tamanho máximo atingido: " + MAX_SIZE);
		}
		vertex[size++] = name;
		needCalculateColors = true;
	}

	public void removeVertex(String name) throws NotFoundVextexException {

		int index = getIndexVertex(name);

		if (index == -1) {
			throw new NotFoundVextexException("O vértice " + name + " não foi encontrado no grafo.");
		}

		size--;
		int j = index;

		for (int i = index; i < size; i++) {
			vertex[i] = vertex[++j];
		}

		vertex[j] = null;

		if (index != size) {
			for (int i = 0; i <= size; i++) {
				for (j = index; j < size; j++) {
					setValueJustAtPosition(i, j, graph[i][j + 1]);
				}
			}

			for (int i = index; i < size; i++) {
				for (j = 0; j <= size; j++) {
					setValueJustAtPosition(i, j, graph[i + 1][j]);
				}
			}
		}

		for (int i = 0; i < MAX_SIZE; i++) {
			setValueAtPosition(i, size, 0);
		}

		needCalculateColors = true;
	}

	private int getIndexVertex(String name) {

		for (int i = 0; i < size; i++) {
			if (vertex[i].equals(name)) {
				return i;
			}
		}

		return -1;
	}

	public void showDegrees() {
		int degree;

		for (int i = 0; i < size; i++) {

			degree = 0;

			for (int j = 0; j < size; j++) {
				if (edgeExists(i, j)) {
					degree++;
				}
			}

			UI.printf("Grau do vértice \"%s\": %d\n", vertex[i], degree);
		}
	}

	public void showGraph() throws NotFoundVextexException {

		if (size == 0) {
			throw new NotFoundVextexException("O Grafo não possui nenhum vértice para ser exibido.");
		}

		int i, j;
		boolean first;

		for (i = 0; i < size; i++) {
			UI.printf("Vértices adjacentes a \"%s\"%s: ", vertex[i],
					!needCalculateColors ? String.format(" (Cor %d)", colors[i]) : "");
			first = true;

			for (j = 0; j < size; j++) {
				if (edgeExists(i, j)) {
					UI.printf("%s \"%s\" (peso: %d)", first ? "" : ",", vertex[j], graph[i][j]);
					first = false;
				}
			}

			UI.printNewLine();
		}

		UI.printNewLine();

		if (needCalculateColors) {
			UI.print("Para exibir as cores de cada vértice, por favor, escolha a opção 15 primeiro.");
		} else {
			UI.printf("Este grafo é %d-Colorível através da heurística da coloração.", amountColors);
		}
	}

	public void load() throws IOException, MaximumVextexException {

		List<String> lines = FileUtils.readFileAsList(Paths.get(FILE_NAME));

		size = Integer.parseInt(lines.get(0));

		if (size > MAX_SIZE) {
			throw new MaximumVextexException(
					"Arquivo inválido. Não é possível inserir mais que " + MAX_SIZE + "vértices");
		}

		graph = new int[MAX_SIZE][MAX_SIZE];
		vertex = new String[MAX_SIZE];
		colors = new int[MAX_SIZE];

		for (int i = 1; i <= size; i++) {
			vertex[i - 1] = lines.get(i);
		}

		int amountEdges = Integer.parseInt(lines.get(size + 1));

		for (int i = size + 2; i < amountEdges + size + 2; i++) {
			String[] tokens = lines.get(i).split(" ");
			int j = Integer.parseInt(tokens[0]) - 1;
			int k = Integer.parseInt(tokens[1]) - 1;
			int value = Integer.parseInt(tokens[2]);

			graph[j][k] = value;
			graph[k][j] = value;
		}

		needCalculateColors = true;
	}

	public void save() throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append(size + "\n");

		for (int i = 0; i < size; i++) {
			sb.append(vertex[i] + "\n");
		}

		sb.append(getAmountEdges() + "\n");

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (edgeExists(i, j)) {
					if (i > j) {
						continue;
					}

					sb.append(String.format("%d %d %d \n", i + 1, j + 1, graph[i][j]));
				}
			}
		}

		FileUtils.save(sb.toString(), Paths.get(FILE_NAME));
	}

	public int getAmountEdges() {

		int edges = 0;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (edgeExists(i, j)) {
					edges++;
				}
			}
		}

		return edges /= 2;
	}

	public boolean isComplete() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) {
					continue;
				}

				if (!edgeExists(i, j)) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean isEulerian() {

		if (isNull()) {
			return false;
		}

		int degree;

		for (int i = 0; i < size; i++) {

			degree = 0;

			for (int j = 0; j < size; j++) {
				if (edgeExists(i, j)) {
					degree++;
				}
			}

			if (degree % 2 != 0) {
				return false;
			}
		}

		return true;
	}

	public boolean isNull() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) {
					continue;
				}

				if (edgeExists(i, j)) {
					return false;
				}
			}
		}

		return true;
	}

	public void showComplement() throws NotFoundVextexException {

		if (size == 0) {
			throw new NotFoundVextexException("O Grafo não possui nenhum vértice para ser exibido.");
		}

		int i, j;
		boolean first;

		for (i = 0; i < size; i++) {
			UI.printf("Vértices adjacentes a \"%s\": ", vertex[i]);
			first = true;

			for (j = 0; j < size; j++) {

				if (i == j) {
					continue;
				}

				if (!edgeExists(i, j)) {
					UI.printf("%s \"%s\" (peso: %d)", first ? "" : ",", vertex[j], 1);
					first = false;
				}
			}

			UI.printNewLine();
		}
	}

	public void coloringHeuristic() {

		int[] forbbidenColors = new int[size];
		int r;
		amountColors = 0;

		for (int i = 0; i < size; i++) {
			forbbidenColors[i] = -1;
			colors[i] = 0;
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (edgeExists(i, j)) {
					if (colors[j] != 0) {
						forbbidenColors[colors[j] - 1] = i;
					}
				}
			}

			r = 1;

			while (colors[i] == 0) {
				if (forbbidenColors[r - 1] != i) {
					colors[i] = r;

					if (r > amountColors) {
						amountColors = r;
					}
				} else {
					r++;
				}
			}
		}

		needCalculateColors = false;
	}
}

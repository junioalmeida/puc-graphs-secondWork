import java.util.Scanner;

public class UI {
	
	private static Scanner keyboard = new Scanner(System.in);
	
	public static int menu() {
		System.out.println("\nMenu de opções:");
        System.out.println("1 - Inserir vértice");
        System.out.println("2 - Remover vértice");
        System.out.println("3 - Inserir aresta");
        System.out.println("4 - Remover aresta");
        System.out.println("5 - Verificar adjacências");
        System.out.println("6 - Graus dos vértices");
        System.out.println("7 - Exibir grafo");
        System.out.println("8 - Verificar euleriano");
        System.out.println("9 - Verificar completo");
        System.out.println("10 - Verificar totalmente desconexo");
        System.out.println("11 - Grafo complementar");
        System.out.println("12 - Alterar peso aresta");
        System.out.println("13 - Carregar grafo");
        System.out.println("14 - Salvar grafo");
        System.out.println("15 - Heurística da Coloração");
        System.out.println("16 - Sair");
        System.out.print("\nOpção: ");

        return Integer.parseInt(keyboard.nextLine());
	}
	
	public static void printf(String format, Object... args) {
		System.out.printf(format, args);
	}
	
	public static void println(Object obj) {
		System.out.println(obj);
	}

	public static void print(Object obj) {
		System.out.print(obj);
	}

	public static void printNewLine() {
		System.out.println();
	}
	
	public static String readString(String str) {
		print(str);
		return keyboard.nextLine();
	}
	
	public static int readInt(String str) {
		print(str);
		return Integer.parseInt(keyboard.nextLine());
	}
}

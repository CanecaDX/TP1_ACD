package TP1.src;

public class Heap {

    public void ordenar(int[] vetor) {
        int tamanho = vetor.length;

        for (int pai = tamanho / 2 - 1; pai >= 0; pai--) {
            ajustarHeap(vetor, tamanho, pai);
        }

        for (int fim = tamanho - 1; fim > 0; fim--) {
            trocar(vetor, 0, fim);
            ajustarHeap(vetor, fim, 0);
        }
    }

    private void ajustarHeap(int[] vetor, int tamanho, int raiz) {
        int maior = raiz;
        int filhoEsquerdo = 2 * raiz + 1;
        int filhoDireito = 2 * raiz + 2;

        if (filhoEsquerdo < tamanho && vetor[filhoEsquerdo] > vetor[maior]) {
            maior = filhoEsquerdo;
        }

        if (filhoDireito < tamanho && vetor[filhoDireito] > vetor[maior]) {
            maior = filhoDireito;
        }

        if (maior != raiz) {
            trocar(vetor, raiz, maior);
            ajustarHeap(vetor, tamanho, maior);
        }
    }

    private void trocar(int[] vetor, int i, int j) {
        int temporario = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temporario;
    }

    public static void main(String[] args) {
        int[] vetor1 = {10, 20, 50, 60, 70, 80, 95, 110, 130, 150, 200, 220};
        int[] vetor2 = {220, 200, 150, 130, 110, 95, 85, 70, 60, 50, 20, 10};
        int[] vetor3 = {4, 7, 2, 9, 1, 5, 3, 8, 6, 40, 12, 19, 200, 311, 233};
        int[] vetor4 = {10, 10, 10, 10, 11, 11, 11, 110, 130, 150, 150, 10};

        if (args.length != 1) {
            System.out.println("Uso: java TP1.src.Heap <tipo>");
            System.out.println("Tipos disponíveis: 1, 2, 3 ou 4");
            return;
        }

        int[] vetor;

        switch (args[0]) {
            case "1":
                vetor = vetor1;
                break;
            case "2":
                vetor = vetor2;
                break;
            case "3":
                vetor = vetor3;
                break;
            case "4":
                vetor = vetor4;
                break;
            default:
                System.out.println("Tipo inválido. Escolha entre 1, 2, 3 ou 4.");
                return;
        }

        System.out.println("Antes: " + java.util.Arrays.toString(vetor));
        new Heap().ordenar(vetor);
        System.out.println("Depois: " + java.util.Arrays.toString(vetor));
    }
}

package TP1.src;

public class Merge {
    
    public static void ordenar(int v[], int p, int n){
        int q;

        if (p < n){
            q = (p + n) / 2;
            ordenar(v, p, q);
            ordenar(v, q+1, n);
            merge(v, p, q, n);
        }
    }


    public static void merge(int v[], int p, int q, int n) {
    int nL = q - p + 1;
    int nR = n - q;

    int[] L = new int[nL];
    int[] R = new int[nR];

    for (int i = 0; i < nL; i++) {
        L[i] = v[p + i];
    }

    for (int j = 0; j < nR; j++) {
        R[j] = v[q + 1 + j];
    }

    int i = 0;
    int j = 0;
    int k = p;

    while (i < nL && j < nR) {
        if (L[i] <= R[j]) {
            v[k] = L[i];
            i++;
        } else {
            v[k] = R[j];
            j++;
        }
        k++;
    }

    while (i < nL) {
        v[k] = L[i];
        i++;
        k++;
    }

    while (j < nR) {
        v[k] = R[j];
        j++;
        k++;
    }
}

    public static void main(String[] args) {
        int[] vetor1 = {10, 20, 50, 60, 70, 80, 95, 110, 130, 150, 200, 220};
        int[] vetor2 = {220, 200, 150, 130, 110, 95, 85, 70, 60, 50, 20, 10};
        int[] vetor3 = {4, 7, 2, 9, 1, 5, 3, 8, 6, 40, 12, 19, 200, 311, 233};
        int[] vetor4 = {10, 10, 10, 10, 11, 11, 11, 110, 130, 150, 150, 10};

        if (args.length != 1) {
            System.out.println("Uso: java TP1.src.Merge <tipo>");
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
        ordenar(vetor, 0, vetor.length - 1);
        System.out.println("Depois: " + java.util.Arrays.toString(vetor));
    }
}

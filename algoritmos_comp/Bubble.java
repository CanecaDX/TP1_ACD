package TP1.src;

public class Bubble {
    
    public static void ordenar(int v[], int n){
        boolean troca = true;
        int fim = n - 1;

        while(troca){
            troca = false;

            for(int i = 0; i < fim; i++){ // i < fim porque o ultimo elemento já estará ordenado 

                if (v[i] > v[i+1]){

                    swap(v, i, i+1);
                    troca=true;

                }
            }
            fim --;
        }

    }

    public static void swap(int v[], int i, int j){

        int temp = v[i];
        v[i] = v[j];
        v[j] = temp;        

    }

    public static void main(String[] args) {
        int[] vetor1 = {10, 20, 50, 60, 70, 80, 95, 110, 130, 150, 200, 220}; // vetor ordenado
        int[] vetor2 = {220, 200, 150, 130, 110, 95, 85, 70, 60, 50, 20, 10}; // vetor ordenado de forma decrescente
        int[] vetor3 = {4, 7, 2, 9, 1, 5, 3, 8, 6, 40, 12, 19, 200, 311, 233}; // vetor desordenado
        int[] vetor4 = {10, 10, 10, 10, 11, 11, 11, 110, 130, 150, 150, 10}; // vetor com elementos repetidos

        if (args.length != 1) {
            System.out.println("Uso: java TP1.src.Bubble <tipo>");
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

        ordenar(vetor, vetor.length);

        System.out.println("Depois: " + java.util.Arrays.toString(vetor));
    }

}
package Ordenacao;

import java.util.ArrayList;

public class Bucket implements Ordenador {
    @Override
    public void ordenar(int[] vetor) {
        if (vetor == null) {
            System.out.println("Bucket Sort: vetor nulo.");
            return;
        }

        if (vetor.length < 2) {
            System.out.println("Bucket Sort: vetor com menos de 2 elementos.");
            return;
        }

        for (int valor : vetor) {
            if (valor < 0) {
                System.out.println("Bucket Sort: exige valores inteiros não negativos.");
                return;
            }
        }

        int menor = vetor[0];
        int maior = vetor[0];

        for (int i = 1; i < vetor.length; i++) {
            if (vetor[i] < menor) {
                menor = vetor[i];
            }

            if (vetor[i] > maior) {
                maior = vetor[i];
            }
        }

        int quantidadeBuckets = (int) Math.sqrt(vetor.length);

        if (quantidadeBuckets < 1) {
            quantidadeBuckets = 1;
        }

        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>();

        for (int i = 0; i < quantidadeBuckets; i++) {
            buckets.add(new ArrayList<Integer>());
        }

        long intervalo = (long) maior - menor + 1;

        for (int i = 0; i < vetor.length; i++) {
            int indice = (int) (((long) vetor[i] - menor)
                    * quantidadeBuckets / intervalo);

            if (indice >= quantidadeBuckets) {
                indice = quantidadeBuckets - 1;
            }

            buckets.get(indice).add(vetor[i]);
        }

        Counting counting = new Counting();
        int posicao = 0;

        for (int i = 0; i < quantidadeBuckets; i++) {
            ArrayList<Integer> bucket = buckets.get(i);

            int[] valores = new int[bucket.size()];

            for (int j = 0; j < bucket.size(); j++) {
                valores[j] = bucket.get(j);
            }

            counting.ordenar(valores);

            for (int j = 0; j < valores.length; j++) {
                vetor[posicao] = valores[j];
                posicao++;
            }
        }
    }

    public static void main(String[] args) {
        int[] vetor1 = {10, 20, 50, 60, 70, 80, 95, 110, 130, 150, 200, 220};
        int[] vetor2 = {220, 200, 150, 130, 110, 95, 85, 70, 60, 50, 20, 10};
        int[] vetor3 = {4, 7, 2, 9, 1, 5, 3, 8, 6, 40, 12, 19, 200, 311, 233};
        int[] vetor4 = {10, 10, 10, 10, 11, 11, 11, 110, 130, 150, 150, 10};

        if (args.length != 1) {
            System.out.println("Uso: java TP1.src.Bucket <tipo>");
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
        new Bucket().ordenar(vetor);
        System.out.println("Depois: " + java.util.Arrays.toString(vetor));
    }
}

package Scripts;

import java.util.Random;

public class GeradorVetores {
    private static final Random RANDOM = new Random();

    public static int[] gerarAleatorio(int n) {
        int[] vetor = new int[n];
        for (int i = 0; i < n; i++) {
            vetor[i] = RANDOM.nextInt(1000000);
        }
        return vetor;
    }
}

package Scripts;

import java.util.Random;

public class GeradorVetores {
    public static final int VALOR_MAXIMO = 999_999;
    public static final int QUANTIDADE_DIGITOS = 6;
    private static final Random RANDOM = new Random(123456789L);

    public static int[] gerarAleatorio(int n) {
        int[] vetor = new int[n];
        for (int i = 0; i < n; i++) {
            vetor[i] = RANDOM.nextInt(VALOR_MAXIMO + 1);
        }
        return vetor;
    }
}

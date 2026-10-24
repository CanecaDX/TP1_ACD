package Scripts;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Vetores {

    public static Map<String, int[]> geraVetores(int n) {
        int[] original = GeradorVetores.gerarAleatorio(n);

        int[] vetorOrdenado = original.clone();
        Arrays.sort(vetorOrdenado);

        int[] vetorAleatorio = original.clone();

        int[] vetorDecrescente = original.clone();
        Arrays.sort(vetorDecrescente);
        for (int i = 0; i < vetorDecrescente.length / 2; i++) {
            int aux = vetorDecrescente[i];
            vetorDecrescente[i] = vetorDecrescente[vetorDecrescente.length - 1 - i];
            vetorDecrescente[vetorDecrescente.length - 1 - i] = aux;
        }

        Map<String, int[]> vetores = new LinkedHashMap<>();
        vetores.put("ordenado", vetorOrdenado);
        vetores.put("aleatorio", vetorAleatorio);
        vetores.put("decrescente", vetorDecrescente);

        return vetores;
    }
}

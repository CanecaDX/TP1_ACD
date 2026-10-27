package Scripts;

import Ordenacao.Bubble;
import Ordenacao.Bucket;
import Ordenacao.Counting;
import Ordenacao.Heap;
import Ordenacao.Insertion;
import Ordenacao.Merge;
import Ordenacao.Ordenador;
import Ordenacao.Quick;
import Ordenacao.Radix;
import Ordenacao.Selection;
import Ordenacao.Shell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Testes {
    private static final int[] TAMANHOS = {1000, 10000, 100000, 1000000};
    private static final String[] CONFIGURACOES = {"ordenado", "aleatorio", "decrescente"};

    public static void main(String[] args) throws IOException {
        Ordenador[] algoritmos = {
            new Bubble(),
            new Insertion(),
            new Selection(),
            new Shell(),
            new Quick(),
            new Merge(),
            new Heap(),
            new Counting(),
            new Radix(),
            new Bucket()
        };

        for (int n : TAMANHOS) {
            Map<String, int[]> vetores = Vetores.geraVetores(n);

            for (String configuracao : CONFIGURACOES) {
                int[] vetorBase = vetores.get(configuracao);

                for (Ordenador algoritmo : algoritmos) {
                    String nomeAlgoritmo = algoritmo.getClass().getSimpleName();

                    File diretorio = new File(obterDiretorioResultados(), nomeAlgoritmo + File.separator + n + File.separator);
                    if (!diretorio.exists() && !diretorio.mkdirs()) {
                        throw new IOException("Não foi possível criar o diretório: " + diretorio.getAbsolutePath());
                    }

                    File arquivo = new File(diretorio, configuracao + ".txt");
                    try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
                        for (int execucao = 0; execucao < 10; execucao++) {
                            System.out.println("[TESTE] Algoritmo: " + nomeAlgoritmo + " | tamanho: " + n + " | configuracao: " + configuracao + " | execucao: " + (execucao + 1) + "/10");

                            int[] copia = vetorBase.clone();
                            long inicio = System.nanoTime();
                            algoritmo.ordenar(copia);
                            long fim = System.nanoTime();
                            long tempo = fim - inicio;
                            writer.println(tempo);
                        }
                    }
                }
            }
        }
    }

    private static File obterDiretorioResultados() {
        File caminho = new File("../Resultados");
        if (!caminho.exists()) {
            caminho = new File("Resultados");
        }
        return caminho;
    }
}

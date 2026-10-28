package Scripts;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Calcula estatísticas das medições produzidas por Testes.java
 * e gera CSVs para análise e para os gráficos do GNUPlot.
 *
 * Cada arquivo .txt deve conter uma medição em nanossegundos por linha.
 */
public class AnaliseResultados {
    private static final String DIRETORIO_RESULTADOS = "Resultados";
    private static final String DIRETORIO_SAIDA = "Resultados/Analise";

    public static void main(String[] args) throws IOException {
        Path pastaResultados = localizarResultados(args);

        Path pastaSaida = args.length > 1
                ? Paths.get(args[1]).toAbsolutePath().normalize()
                : Paths.get(DIRETORIO_SAIDA).toAbsolutePath().normalize();

        Files.createDirectories(pastaSaida);

        List<Resultado> resultados = new ArrayList<>();

        List<Path> arquivos = new ArrayList<>();
        try (Stream<Path> caminhos = Files.walk(pastaResultados)) {
            caminhos.filter(Files::isRegularFile)
                    .filter(caminho -> caminho.toString().toLowerCase(Locale.ROOT).endsWith(".txt"))
                    .forEach(arquivos::add);
        }
        Collections.sort(arquivos);

        for (Path arquivo : arquivos) {
            Resultado resultado = analisarArquivo(arquivo, pastaResultados);
            if (resultado != null) {
                resultados.add(resultado);
            }
        }

        if (resultados.isEmpty()) {
            throw new IOException("Nenhum arquivo .txt com medições foi encontrado em " + pastaResultados);
        }

        escreverEstatisticas(resultados, pastaSaida.resolve("estatisticas.csv"));
        escreverCsvParaGnuplot(resultados, pastaSaida);

        System.out.println("Análise concluída.");
        System.out.println("Estatísticas e CSVs do GNUPlot salvos em: " + pastaSaida);
    }

    private static Path localizarResultados(String[] args) throws IOException {
        if (args.length > 0) {
            Path pasta = Paths.get(args[0]).toAbsolutePath().normalize();
            validarDiretorio(pasta);
            return pasta;
        }

        Path pastaAtual = Paths.get(DIRETORIO_RESULTADOS).toAbsolutePath().normalize();
        if (Files.isDirectory(pastaAtual)) {
            return pastaAtual;
        }

        Path pastaPai = Paths.get("..", DIRETORIO_RESULTADOS).toAbsolutePath().normalize();
        validarDiretorio(pastaPai);
        return pastaPai;
    }

    private static void validarDiretorio(Path pasta) throws IOException {
        if (!Files.isDirectory(pasta)) {
            throw new IOException("Diretório de resultados não encontrado: " + pasta);
        }
    }

    private static Resultado analisarArquivo(Path arquivo, Path raiz) throws IOException {
        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
        List<Double> tempos = new ArrayList<>();

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i).trim();
            if (linha.isEmpty()) {
                continue;
            }

            try {
                // Os arquivos de teste guardam os tempos em nanossegundos.
                double segundos = Long.parseLong(linha) / 1_000_000_000.0;
                tempos.add(segundos);
            } catch (NumberFormatException e) {
                throw new IOException("Valor inválido em " + arquivo + ", linha " + (i + 1) + ": " + linha, e);
            }
        }

        if (tempos.isEmpty()) {
            System.err.println("Aviso: arquivo vazio, ignorado: " + arquivo);
            return null;
        }

        List<Double> temposOrdenados = new ArrayList<>(tempos);
        Collections.sort(temposOrdenados);

        double soma = 0.0;
        for (double tempo : tempos) {
            soma += tempo;
        }
        double media = soma / tempos.size();

        double mediana;
        int meio = temposOrdenados.size() / 2;
        if (temposOrdenados.size() % 2 == 0) {
            mediana = (temposOrdenados.get(meio - 1) + temposOrdenados.get(meio)) / 2.0;
        } else {
            mediana = temposOrdenados.get(meio);
        }

        // Desvio padrão populacional: divide pela quantidade total de medições.
        double somaDiferencas = 0.0;
        for (double tempo : tempos) {
            somaDiferencas += Math.pow(tempo - media, 2);
        }
        double desvioPadrao = Math.sqrt(somaDiferencas / tempos.size());

        Path relativo = raiz.relativize(arquivo);
        String[] partes = relativo.toString().split("[/\\\\]");

        String configuracao = removerExtensao(partes[partes.length - 1]);
        String tamanho = partes.length >= 2 ? partes[partes.length - 2] : "desconhecido";
        String algoritmo = partes.length >= 3 ? partes[partes.length - 3] : "desconhecido";

        String tipoEntrada = identificarTipoEntrada(configuracao);
        if (tipoEntrada == null) {
            System.err.println("Aviso: tipo de entrada não reconhecido, arquivo ignorado: " + arquivo);
            return null;
        }

        int tamanhoNumero;
        try {
            tamanhoNumero = Integer.parseInt(tamanho);
        } catch (NumberFormatException e) {
            throw new IOException("Tamanho de entrada inválido no caminho: " + arquivo);
        }

        return new Resultado(relativo.toString(), algoritmo, tamanhoNumero, tipoEntrada,
                tempos.size(), media, mediana, desvioPadrao);
    }

    private static String identificarTipoEntrada(String nome) {
        String texto = nome.toLowerCase(Locale.ROOT)
                .replace("á", "a")
                .replace("ã", "a")
                .replace("é", "e")
                .replace("ó", "o")
                .replace("í", "i");

        if (texto.contains("ordenado") && !texto.contains("decrescente") && !texto.contains("decresc")) {
            return "ordenado";
        }
        if (texto.contains("aleatorio") || texto.contains("random")) {
            return "aleatorio";
        }
        if (texto.contains("decrescente") || texto.contains("decresc")) {
            return "decrescente";
        }
        return null;
    }

    private static String removerExtensao(String nome) {
        if (nome.toLowerCase(Locale.ROOT).endsWith(".txt")) {
            return nome.substring(0, nome.length() - 4);
        }
        return nome;
    }

    private static void escreverEstatisticas(List<Resultado> resultados, Path arquivo) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivo, StandardCharsets.UTF_8)) {
            writer.write("arquivo;algoritmo;tamanho;tipo_entrada;execucoes;media_s;mediana_s;desvio_padrao_s");
            writer.newLine();

            for (Resultado r : resultados) {
                writer.write(String.format(Locale.US, "%s;%s;%d;%s;%d;%.12f;%.12f;%.12f",
                        r.arquivo, r.algoritmo, r.tamanho, r.tipoEntrada,
                        r.execucoes, r.media, r.mediana, r.desvioPadrao));
                writer.newLine();
            }
        }
    }

    private static void escreverCsvParaGnuplot(List<Resultado> resultados, Path pastaSaida) throws IOException {
        String[] tipos = {"ordenado", "aleatorio", "decrescente"};

        for (String tipo : tipos) {
            Map<String, Map<Integer, Double>> dados = new TreeMap<>();
            Map<Integer, Map<String, Double>> porTamanho = new TreeMap<>();

            for (Resultado r : resultados) {
                if (r.tipoEntrada.equals(tipo)) {
                    dados.computeIfAbsent(r.algoritmo, chave -> new TreeMap<>()).put(r.tamanho, r.media);
                    porTamanho.computeIfAbsent(r.tamanho, chave -> new TreeMap<>()).put(r.algoritmo, r.media);
                }
            }

            if (porTamanho.isEmpty()) {
                System.err.println("Aviso: não há resultados para o tipo " + tipo);
                continue;
            }

            Path arquivoCsv = pastaSaida.resolve("dados_" + tipo + ".csv");
            try (BufferedWriter writer = Files.newBufferedWriter(arquivoCsv, StandardCharsets.UTF_8)) {
                writer.write("Tamanho");
                for (String algoritmo : dados.keySet()) {
                    writer.write(";" + algoritmo);
                }
                writer.newLine();

                for (Map.Entry<Integer, Map<String, Double>> linha : porTamanho.entrySet()) {
                    writer.write(Integer.toString(linha.getKey()));

                    for (String algoritmo : dados.keySet()) {
                        Double media = linha.getValue().get(algoritmo);
                        writer.write(";");
                        if (media != null) {
                            writer.write(String.format(Locale.US, "%.12f", media));
                        }
                    }
                    writer.newLine();
                }
            }
            System.out.println("CSV para GNUPlot: " + arquivoCsv);
        }
    }

    private static class Resultado {
        String arquivo;
        String algoritmo;
        int tamanho;
        String tipoEntrada;
        int execucoes;
        double media;
        double mediana;
        double desvioPadrao;

        Resultado(String arquivo, String algoritmo, int tamanho, String tipoEntrada,
                  int execucoes, double media, double mediana, double desvioPadrao) {
            this.arquivo = arquivo;
            this.algoritmo = algoritmo;
            this.tamanho = tamanho;
            this.tipoEntrada = tipoEntrada;
            this.execucoes = execucoes;
            this.media = media;
            this.mediana = mediana;
            this.desvioPadrao = desvioPadrao;
        }
    }

}

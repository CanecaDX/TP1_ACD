# GNUPlot - TP1 ACD
# Espera tres CSVs, separados por ponto e virgula (;):
#   dados_ordenado.csv
#   dados_aleatorio.csv
#   dados_decrescente.csv
#
# Formato de cada CSV:
# Tamanho;Bubble Sort;Insertion Sort;Selection Sort;Shell Sort;Heap Sort;Merge Sort;Quick Sort;Counting Sort;Radix Sort;Bucket Sort
# 1000;0.001;0.002;... (tempos MEDIOS em segundos para cada tamanho)
#
# Cada ponto representa a media das execucoes para aquela combinacao.
# Mantenha os tempos individuais guardados separadamente para analise estatistica.

set terminal pngcairo size 1280,720 enhanced font "Arial,10"
set datafile separator ";"
set grid xtics ytics lw 1 lc rgb "#dddddd"
set border lw 1.5
set key top left box opaque
set xlabel "Tamanho do vetor (elementos)"
set ylabel "Tempo medio de execucao (segundos)"
set logscale x 10
set logscale y 10
set xrange [1000:*]
set format x "10^{%L}"
set format y "10^{%L}"
set pointsize 1.2

# Cores/estilos para os dez algoritmos.
set style line 1  lw 2 lc rgb "#e6194B" pt 7
set style line 2  lw 2 lc rgb "#f58231" pt 5
set style line 3  lw 2 lc rgb "#4363d8" pt 9
set style line 4  lw 2 lc rgb "#42d4f4" pt 11
set style line 5  lw 2 lc rgb "#3cb44b" pt 13
set style line 6  lw 2 lc rgb "#911eb4" pt 3
set style line 7  lw 2 lc rgb "#f032e6" pt 1
set style line 8  lw 2 lc rgb "#a9a9a9" pt 2
set style line 9  lw 2 lc rgb "#800000" pt 4
set style line 10 lw 2 lc rgb "#008080" pt 8

# Gera um grafico para cada tipo de entrada.
do for [i=1:3] {
    if (i == 1) {
        arquivo = "../Resultados/Analise/dados_ordenado.csv"
        titulo = "Entradas em ordem crescente"
        saida = "../Resultados/Analise/grafico_ordenado.png"
    } else if (i == 2) {
        arquivo = "../Resultados/Analise/dados_aleatorio.csv"
        titulo = "Entradas em ordem aleatoria"
        saida = "../Resultados/Analise/grafico_aleatorio.png"
    } else {
        arquivo = "../Resultados/Analise/dados_decrescente.csv"
        titulo = "Entradas em ordem decrescente"
        saida = "../Resultados/Analise/grafico_decrescente.png"
    }

    set output saida
    set title sprintf("TP1 ACD - %s", titulo) font ",14 bold"

    plot for [col=2:11] arquivo using 1:col with linespoints ls (col-1) \
        title columnhead(col)

    unset output
}

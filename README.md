# TP1 - Algoritmos de Ordenação

Projeto para comparar diferentes algoritmos de ordenação em Java, incluindo algoritmos de comparação e algoritmos de tempo linear.

## Ambiente experimental para análise prática de complexidade de algoritmos

- Gera vetores de diferentes tamanhos
- Cria variações de entrada:
  - aleatória
  - ordenada
  - decrescente
- Executa vários algoritmos de ordenação sobre os mesmos dados
- Mede o tempo de execução de cada algoritmo
- Salva os resultados em arquivos dentro da pasta Resultados/

## Algoritmos incluídos

- Bubble Sort
- Insertion Sort
- Selection Sort
- Shell Sort
- Quick Sort
- Merge Sort
- Heap Sort
- Counting Sort
- Radix Sort
- Bucket Sort

## Como rodar

1. Abra o terminal na pasta do projeto
2. Compile os arquivos Java:

```bash
javac Ordenacao/*.java Scripts/*.java
```

3. Execute a classe principal de testes:

```bash
java Scripts.Testes
```

## Saída

Os tempos de execução serão gravados em arquivos na pasta:

```bash
Resultados/
```

Estrutura aproximada:

```bash
Resultados/
  Bubble/
    1000/
      aleatorio.txt
  ...
```

## Observação

Os algoritmos de tempo linear exigem entradas com valores inteiros não negativos e o projeto foi estruturado para manter uma comparação justa usando a mesma base de dados para todos os algoritmos.

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
}


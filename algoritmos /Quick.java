package TP1.src;

public class Quick {

    public void ordenar (int v[], int p, int f) {
        if (p < f) {
            int q = particiona(v, p, f);
            ordenar(v, p, q - 1);
            ordenar(v, q+1, f);
        }
    }

    public int particiona (int v[], int p, int fim){
        int x = v[fim];
        int i = p - 1;

        for (int j = p; j < fim; j++){
            if(v[j] <= x){
                i = i+1;
                swap(v, i, j);
            }

        }
        swap(v, i+1, fim);
        return i+1;
    }

    public static void swap(int v[], int i, int j){
        int temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }
    
}

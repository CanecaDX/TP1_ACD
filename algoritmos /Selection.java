package TP1.src;

public class Selection {
    
    public static void ordenar(int v[], int n){
        int min;
        for(int i  = 0; i < n; i++){
            min = i;

            for(int j = i+1; j <= n; i++){
                if(v[j] < v[min]){
                    min = j;
                }
                swap(v, i, min);
            }
        }
    }

    public static void swap(int v[], int i, int min){
        int temp = v[i];
        v[i] = v[min];
        v[min] = temp;
    }
}

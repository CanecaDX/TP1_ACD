package TP1.src;

public class Bubble {
    
    public static void ordenar(int v[], int n){
        boolean troca = true;
        int fim = n - 1;

        while(troca){
            troca = false;

            for(int i = 0; i < fim; i++){ // i < fim porque o ultimo elemento já estará ordenado 

                if (v[i] > v[i+1]){

                    swap(v, i, i+1);
                    troca=true;

                }
            }
            fim --;
        }

    }

    public static void swap(int v[], int i, int j){

        int temp = v[i];
        v[i] = v[j];
        v[j] = temp;        

    }
}

package TP1.src;

public class Insertion {
    
    public static void ordenar(int v[], int n){

        for(int i = 1; i < n; i++){
            int chave = v[i];
            int j = i-1;

            while(j >= 0 && v[j] > chave){
                v[j+1] = v[j];
                j = j -1;
            }

            v[j+1] = chave;
        }

        //for(int i = 0; i < n; i++){
        //System.out.printf("[%d] ", v[i]);
    //}
    }

    // main só pra testar o algoritmo

public static void main(String[] args) {

        //vetor para teste:
        int v[] = {105, 95, 6, 0};
        int n = 4;

        System.out.print("\nElementos do vetor original:\n");

        for (int i = 0; i <= n-1; i++){
            System.out.printf("%d ", v[i]);
        }

        System.out.printf("\n");

        ordenar(v,n);
    }



}

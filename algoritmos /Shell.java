package TP1.src;

public class Shell {

    public static void ordenar(int v[], int n) {
        int h = 1;
        int x = 0;
        int j = 0;

        do {
            h = h * 3 + 1;
        } while (h < n);

        do {
            h = h / 3;

            for (int i = h; i < n; i++) {
                x = v[i];
                j = i;

                while (j >= h && v[j - h] > x) {
                    v[j] = v[j - h];
                    j = j - h;
                }
                
                v[j] = x;
            }
        } while (h != 1);
    }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        String[][] matrizPercorrer = leTexto("teste1.txt");
        printaMatriz(matrizPercorrer);
        calculaCaminho(matrizPercorrer, matrizPercorrer.length - 1, 0);
    }

    public static void printaMatriz(String[][] matriz){
        for(int i = 0; i < matriz.length; i++ ){
            System.out.println();
            for(int j = 0; j< matriz.length; j++){
               System.out.print(matriz[i][j] + " | ");
            }
        }
        System.out.println();
    }

    public static int calculaCaminho(String[][] percurso, int x, int y){
        if(x == 0 && y == (percurso.length - 1)){
            return Integer.parseInt(percurso[x][y]);
        }
        else if( x == 0 ){
            return calculaCaminho(percurso, x, y+1) + verificaValor(percurso[x][y]);
        }
        else if(y + 1 >= percurso.length){
            return calculaCaminho(percurso, x + 1, y) + verificaValor(percurso[x][y]);
        }
        else{
            int valor = Math.max(calculaCaminho(percurso, x-1, y), calculaCaminho(percurso, x-1, y+1));
            int maiorValor = Math.max(valor, calculaCaminho(percurso, x, y+1));
            return maiorValor + verificaValor(percurso[x][y]);
        }
    }

    public static int verificaValor(String valor){
        if(valor.equals("x")){
            return -999999;
        }
        else{
            return Integer.valueOf(valor).intValue();
        }
    }

    public static String[][] leTexto(String nomeArquivo) throws IOException {
        String[][] matriz = new String[0][0];
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String line = br.readLine();
            boolean isFirstLine = false;
            int index = 0;
            while (line != null) {
                if (!isFirstLine) {
                    isFirstLine = true;
                    line.trim();
                    matriz = new String[Integer.parseInt(line)][Integer.parseInt(line)];
                    line = br.readLine();
                } else {
                    String[] splited = line.trim().split("\\s+");
                    for (int i = 0; i < matriz.length; i++) {
                        matriz[index][i] = splited[i];
                    }
                    index++;
                    line = br.readLine();
                }
            }
            br.close();
            return matriz;
        } catch (IOException e) {
            System.out.println(e);
        }
        return matriz;
    }
}

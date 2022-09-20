import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            System.out.println("Nenhum arquivo texto inserido. Leia o README.md");
            return;
        }
        String arquivo = args[0];
        String[][] matrizPercorrer = leTexto(arquivo);
        ArrayList<String> caminhoPercorrido = new ArrayList<>();
        caminhoPercorridoQuantidadeOuro calculaValor = calculaCaminho(matrizPercorrer, matrizPercorrer.length - 1, 0, caminhoPercorrido);
        System.out.println("Valor de ouro acumulado: " + calculaValor.valor);
        System.out.println("Caminho percorrido: " + String.join("," , calculaValor.caminhoPercorrido));
    }

    public static void printaMatriz(String[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            System.out.println();
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(matriz[i][j] + " | ");
            }
        }
        System.out.println();
    }


    public static void printaCaminho(ArrayList<String> caminhoPercorrido){
        for(String percorrido: caminhoPercorrido){
          System.out.println(percorrido + " - "  + "ESTOU AQ");
        } 
    }

    public static caminhoPercorridoQuantidadeOuro calculaCaminho(String[][] percurso, int x, int y, ArrayList<String> caminhoPercorrido){
        if(x == 0 && y == (percurso.length - 1)){
            return new caminhoPercorridoQuantidadeOuro(Integer.parseInt(percurso[x][y]), caminhoPercorrido);
        }
        else if( x == 0 ){
            ArrayList<String> novoCaminhoPercorrido = new ArrayList<>(caminhoPercorrido);
            novoCaminhoPercorrido.add("E");
            caminhoPercorridoQuantidadeOuro caminhoSelecionado = calculaCaminho(percurso, x, y + 1, novoCaminhoPercorrido);
            return new caminhoPercorridoQuantidadeOuro( caminhoSelecionado.valor + verificaValor(percurso[x][y]), caminhoSelecionado.caminhoPercorrido );
        }
        else if(y == percurso.length - 1){
            ArrayList<String> novoCaminhoPercorrido = new ArrayList<>(caminhoPercorrido);
            novoCaminhoPercorrido.add("N");
            caminhoPercorridoQuantidadeOuro caminhoSelecionado = calculaCaminho(percurso, x-1, y, novoCaminhoPercorrido);
            return new caminhoPercorridoQuantidadeOuro((caminhoSelecionado.valor + verificaValor(percurso[x][y])), caminhoSelecionado.caminhoPercorrido);
        }
        else{
            ArrayList<String> novoCaminhoPercorridoNE = new ArrayList<>(caminhoPercorrido);
            novoCaminhoPercorridoNE.add("NE");
            ArrayList<String> novoCaminhoPercorridoN = new ArrayList<>(caminhoPercorrido);
            novoCaminhoPercorridoN.add("N");
            ArrayList<String> novoCaminhoPercorridoE = new ArrayList<>(caminhoPercorrido);
            novoCaminhoPercorridoE.add("E");
            caminhoPercorridoQuantidadeOuro caminhoSelecionado = retornaCaminhoEscolhido(calculaCaminho(percurso, x-1, y+1, novoCaminhoPercorridoNE), calculaCaminho(percurso, x-1, y, novoCaminhoPercorridoN), calculaCaminho(percurso, x, y+1, novoCaminhoPercorridoE));
            return new caminhoPercorridoQuantidadeOuro(caminhoSelecionado.valor + verificaValor(percurso[x][y]), caminhoSelecionado.caminhoPercorrido);
        }
    }

    public static caminhoPercorridoQuantidadeOuro retornaCaminhoEscolhido(caminhoPercorridoQuantidadeOuro NE, caminhoPercorridoQuantidadeOuro N, caminhoPercorridoQuantidadeOuro E){
        if(NE.valor >= N.valor && NE.valor >= E.valor){
            return NE;
        }
        if(N.valor >= NE.valor && N.valor >= E.valor){
            return N;
        }
        else{
            return E;
        }
    }

    public static int verificaValor(String valor) {
        if (valor.equals("x")) {
            return -999999;
        } else {
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

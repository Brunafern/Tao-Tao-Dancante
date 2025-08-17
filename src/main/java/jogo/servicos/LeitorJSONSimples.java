package jogo.servicos;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LeitorJSONSimples {

    private static final String PASTA_JSON = "/dados-fase/";
    private static final String MUSICA_PADRAO = "/assets/musica/song1.mp3";
    private static final ConfiguracoesTempo TEMPO_PADRAO = new ConfiguracoesTempo(6000.0, 3000.0, 28000.0);

    /**
     * @param numeroFase número da fase
     * @return array de inteiros representando a sequência de setas; array vazio se não encontrado
     */
    public static int[] carregarSequenciaSetas(int numeroFase) {
        String json = lerJSON(numeroFase);
        return json.isEmpty() ? new int[0] : extrairSequenciaSetas(json);
    }

    /**
     * @param numeroFase número da fase
     * @return caminho da música; retorna caminho padrão se não encontrado
     */
    public static String carregarCaminhoMusica(int numeroFase) {
        String json = lerJSON(numeroFase);
        return json.isEmpty() ? MUSICA_PADRAO : extrairString(json, "caminhoMusica");
    }

    /**
     * @param numeroFase número da fase
     * @return objeto ConfiguracoesTempo com os valores da fase; retorna valores padrão se não encontrado
     */
    public static ConfiguracoesTempo carregarConfiguracoesTempo(int numeroFase) {
        String json = lerJSON(numeroFase);
        return json.isEmpty() ? TEMPO_PADRAO : extrairConfiguracoesTempo(json);
    }

    /**
     * @param numeroFase número da fase
     * @return conteúdo do JSON como String; retorna vazio se não encontrado ou erro
     */
    private static String lerJSON(int numeroFase) {
        String nomeArquivo = "fase" + numeroFase + ".json";
        try (InputStream inputStream = LeitorJSONSimples.class.getResourceAsStream(PASTA_JSON + nomeArquivo)) {
            if (inputStream == null) {
                System.err.println("Arquivo não encontrado: " + nomeArquivo);
                return "";
            }
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler JSON da fase " + numeroFase + ": " + e.getMessage());
            return "";
        }
    }

    /**
     * @param json conteúdo JSON da fase
     * @return array de inteiros; array vazio se não encontrado
     */
    private static int[] extrairSequenciaSetas(String json) {
        int inicio = json.indexOf("\"sequenciaSetas\"");
        if (inicio == -1) return new int[0];

        int inicioArray = json.indexOf('[', inicio);
        int fimArray = json.indexOf(']', inicioArray);
        if (inicioArray == -1 || fimArray == -1) return new int[0];

        String[] numeros = json.substring(inicioArray + 1, fimArray).split(",");
        int[] setas = new int[numeros.length];
        for (int i = 0; i < numeros.length; i++) {
            setas[i] = Integer.parseInt(numeros[i].trim());
        }
        return setas;
    }

    /**
     * @param json  conteúdo JSON
     * @param campo campo a ser extraído
     * @return valor do campo como String; vazio se não encontrado
     */
    private static String extrairString(String json, String campo) {
        int inicio = json.indexOf("\"" + campo + "\"");
        if (inicio == -1) return "";

        int inicioValor = json.indexOf('"', inicio + campo.length() + 3);
        int fimValor = json.indexOf('"', inicioValor + 1);
        if (inicioValor == -1 || fimValor == -1) return "";

        return json.substring(inicioValor + 1, fimValor);
    }

    /**
     * @param json conteúdo JSON
     * @return ConfiguracoesTempo com os valores da fase
     */
    private static ConfiguracoesTempo extrairConfiguracoesTempo(String json) {
        double inicial = extrairDouble(json, "duracaoSetasInicial");
        double final_ = extrairDouble(json, "duracaoSetasFinal");
        double aceleracao = extrairDouble(json, "tempoAceleracao");
        return new ConfiguracoesTempo(inicial, final_, aceleracao);
    }

    /**
     * @param json  conteúdo JSON
     * @param campo campo a ser extraído
     * @return valor double; 0.0 se não encontrado
     */
    private static double extrairDouble(String json, String campo) {
        int inicio = json.indexOf("\"" + campo + "\"");
        if (inicio == -1) return 0.0;

        int inicioValor = json.indexOf(':', inicio) + 1;
        int fimValor = json.indexOf(',', inicioValor);
        if (fimValor == -1) fimValor = json.indexOf('}', inicioValor);
        if (inicioValor == -1 || fimValor == -1) return 0.0;

        return Double.parseDouble(json.substring(inicioValor, fimValor).trim());
    }

    public static class ConfiguracoesTempo {
        public final double duracaoInicial;
        public final double duracaoFinal;
        public final double tempoAceleracao;

        /**
         * @param duracaoInicial duração inicial das setas
         * @param duracaoFinal   duração final das setas
         * @param tempoAceleracao tempo para acelerar de inicial para final
         */
        public ConfiguracoesTempo(double duracaoInicial, double duracaoFinal, double tempoAceleracao) {
            this.duracaoInicial = duracaoInicial;
            this.duracaoFinal = duracaoFinal;
            this.tempoAceleracao = tempoAceleracao;
        }

        /**
         * @param tempoAtual tempo atual em milissegundos
         * @return duração interpolada
         */
        public double calcularDuracao(double tempoAtual) {
            if (tempoAtual >= tempoAceleracao) return duracaoFinal;
            double progresso = Math.min(1.0, tempoAtual / tempoAceleracao);
            return duracaoInicial - ((duracaoInicial - duracaoFinal) * progresso);
        }
    }
}

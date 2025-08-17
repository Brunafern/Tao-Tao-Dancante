package jogo.servicos;

import jogo.modelo.DadosFase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeitorDadosJSON {

    private static final String PASTA_JSON_RESOURCES = "/dados-fase/";
    private static final String PASTA_JSON_USUARIO = System.getProperty("user.home") + "/.taotao-dancante/dados-fase/";

    /**
     * @param numeroFase número da fase
     * @return objeto DadosFase contendo todas as informações da fase
     */
    public static DadosFase carregarFaseDoJSON(int numeroFase) {
        String nomeArquivo = String.format("fase%d.json", numeroFase);

        try (InputStream inputStream = LeitorDadosJSON.class.getResourceAsStream(PASTA_JSON_RESOURCES + nomeArquivo)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo JSON da fase " + numeroFase + " não encontrado!");
            }

            String conteudoJSON = lerInputStream(inputStream);
            DadosJSON dadosJSON = parseJSON(conteudoJSON);

            System.out.printf("✅ Fase %d carregada: %s [%s] | Peso: %.2fx | Setas: %d%n",
                    numeroFase, dadosJSON.nomeFase, dadosJSON.dificuldade, dadosJSON.pesoPontuacao, dadosJSON.sequenciaSetas.length);

            return criarDadosFaseDoJSON(dadosJSON);

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar JSON da fase " + numeroFase + ": " + e.getMessage());
            throw new RuntimeException("Falha ao carregar dados da fase " + numeroFase, e);
        }
    }

    /**
     * @param dados objeto DadosFase a ser salvo
     * @return true se salvamento foi bem-sucedido, false caso contrário
     */
    public static boolean salvarFaseEmJSON(DadosFase dados) {
        String nomeArquivo = String.format("fase%d_backup.json", dados.getNumeroFase());

        try {
            Files.createDirectories(Paths.get(PASTA_JSON_USUARIO));
            String json = gerarJSON(dados);
            Files.write(Paths.get(PASTA_JSON_USUARIO + nomeArquivo), json.getBytes());
            System.out.println("💾 Backup JSON salvo: " + PASTA_JSON_USUARIO + nomeArquivo);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Erro ao salvar JSON: " + e.getMessage());
            return false;
        }
    }

    private static String lerInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultado = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                resultado.append(linha).append("\n");
            }
        }
        return resultado.toString();
    }

    /** Parse manual do JSON (sem bibliotecas externas) */
    private static DadosJSON parseJSON(String json) {
        try {
            json = json.replaceAll("\\s+", " ");
            DadosJSON dados = new DadosJSON();

            dados.numeroFase = extrairInteiro(json, "numeroFase");
            dados.nomeFase = extrairString(json, "nomeFase");
            dados.dificuldade = extrairString(json, "dificuldade");
            dados.pesoPontuacao = extrairDouble(json, "pesoPontuacao");

            dados.duracaoSetasInicial = extrairDoubleAninhado(json, "configuracoesTempo", "duracaoSetasInicial");
            dados.duracaoSetasFinal = extrairDoubleAninhado(json, "configuracoesTempo", "duracaoSetasFinal");
            dados.tempoAceleracao = extrairDoubleAninhado(json, "configuracoesTempo", "tempoAceleracao");

            dados.caminhoMusica = extrairStringAninhada(json, "assets", "caminhoMusica");
            dados.imagemBardo = extrairStringAninhada(json, "assets", "imagemBardo");
            dados.imagemLorde = extrairStringAninhada(json, "assets", "imagemLorde");
            dados.imagemBackground = extrairStringAninhada(json, "assets", "imagemBackground");

            dados.sequenciaSetas = extrairArrayInteiros(json, "sequenciaSetas");

            return dados;

        } catch (Exception e) {
            System.err.println("❌ Erro no parse JSON: " + e.getMessage());
            throw new RuntimeException("Falha no parse do JSON", e);
        }
    }

    private static DadosFase criarDadosFaseDoJSON(DadosJSON dados) {
        return DadosFase.criarDoJSON(
                dados.numeroFase,
                dados.nomeFase,
                dados.dificuldade,
                dados.pesoPontuacao,
                dados.sequenciaSetas,
                dados.caminhoMusica,
                dados.imagemBardo,
                dados.imagemLorde,
                dados.imagemBackground,
                dados.duracaoSetasInicial,
                dados.duracaoSetasFinal,
                dados.tempoAceleracao
        );
    }

    private static String gerarJSON(DadosFase dados) {
        return "{\n" +
                "  \"numeroFase\": " + dados.getNumeroFase() + ",\n" +
                "  \"nomeFase\": \"" + dados.getNomeFase() + "\",\n" +
                "  \"dificuldade\": \"" + dados.getDificuldade() + "\",\n" +
                "  \"pesoPontuacao\": " + dados.getPesoPontuacao() + ",\n" +
                "  \"configuracoesTempo\": {\n" +
                "    \"duracaoSetasInicial\": " + dados.getDuracaoSetasInicial() + ",\n" +
                "    \"duracaoSetasFinal\": " + dados.getDuracaoSetasFinal() + ",\n" +
                "    \"tempoAceleracao\": " + dados.getTempoAceleracao() + "\n" +
                "  },\n" +
                "  \"assets\": {\n" +
                "    \"caminhoMusica\": \"" + dados.getCaminhoMusica() + "\",\n" +
                "    \"imagemBardo\": \"" + dados.getImagemBardo() + "\",\n" +
                "    \"imagemLorde\": \"" + dados.getImagemLorde() + "\",\n" +
                "    \"imagemBackground\": \"" + dados.getImagemBackground() + "\"\n" +
                "  },\n" +
                "  \"sequenciaSetas\": " + Arrays.toString(dados.getSequenciaSetas()) + ",\n" +
                "  \"backup\": true\n" +
                "}";
    }

    private static int extrairInteiro(String json, String campo) {
        Pattern pattern = Pattern.compile("\"" + campo + "\":\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    private static double extrairDouble(String json, String campo) {
        Pattern pattern = Pattern.compile("\"" + campo + "\":\\s*([\\d.]+)");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? Double.parseDouble(matcher.group(1)) : 0.0;
    }

    private static String extrairString(String json, String campo) {
        Pattern pattern = Pattern.compile("\"" + campo + "\":\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static double extrairDoubleAninhado(String json, String objeto, String campo) {
        Pattern pattern = Pattern.compile("\"" + objeto + "\":\\s*\\{[^}]*\"" + campo + "\":\\s*([\\d.]+)");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? Double.parseDouble(matcher.group(1)) : 0.0;
    }

    private static String extrairStringAninhada(String json, String objeto, String campo) {
        Pattern pattern = Pattern.compile("\"" + objeto + "\":\\s*\\{[^}]*\"" + campo + "\":\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static int[] extrairArrayInteiros(String json, String campo) {
        Pattern pattern = Pattern.compile("\"" + campo + "\":\\s*\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            String[] elementos = matcher.group(1).split(",");
            int[] resultado = new int[elementos.length];
            for (int i = 0; i < elementos.length; i++) {
                resultado[i] = Integer.parseInt(elementos[i].trim());
            }
            return resultado;
        }

        return new int[0];
    }

    private static class DadosJSON {
        int numeroFase;
        String nomeFase;
        String dificuldade;
        double pesoPontuacao;
        double duracaoSetasInicial;
        double duracaoSetasFinal;
        double tempoAceleracao;
        String caminhoMusica;
        String imagemBardo;
        String imagemLorde;
        String imagemBackground;
        int[] sequenciaSetas;
    }

}

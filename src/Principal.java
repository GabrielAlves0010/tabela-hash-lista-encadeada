import java.util.Random;

public class Principal {

    public static void main(String[] args) {
        int tamanhoTabela = 100;
        int quantidadeNumeros = 1000;
        int limiteSuperior = 100001; // gera de 0 a 100000

        // vetor de 100 posicoes, cada uma guarda o inicio de uma lista
        No[] tabela = new No[tamanhoTabela];
        Random gerador = new Random(1090);

        // guarda os tres ultimos valores que cairam em colisao
        int antepenultimoValor = 0;
        int antepenultimaPosicao = 0;
        int penultimoValor = 0;
        int penultimaPosicao = 0;
        int ultimoValor = 0;
        int ultimaPosicao = 0;
        int totalColisoes = 0;

        // gera os 1000 numeros e insere cada um na posicao valor % 100
        for (int i = 0; i < quantidadeNumeros; i++) {
            int valor = gerador.nextInt(limiteSuperior);
            int posicao = valor % tamanhoTabela;
            No novo = new No(valor);

            if (tabela[posicao] == null) {
                // posicao vazia, o numero fica direto nela
                tabela[posicao] = novo;
            } else {
                // colisao: encadeia no fim da lista
                No atual = tabela[posicao];
                while (atual.proximo != null) {
                    atual = atual.proximo;
                }
                atual.proximo = novo;
                totalColisoes++;

                // anda os tres ultimos uma casa para tras
                antepenultimoValor = penultimoValor;
                antepenultimaPosicao = penultimaPosicao;
                penultimoValor = ultimoValor;
                penultimaPosicao = ultimaPosicao;
                ultimoValor = valor;
                ultimaPosicao = posicao;
            }
        }

        // imprime a tabela e ja conta vazias, listas e a maior
        System.out.println("=== TABELA COMPLETA (100 posicoes) ===");
        int somaTamanhos = 0;
        int posicoesVazias = 0;
        int listasEncadeadas = 0;
        int posicaoMaior = 0;
        int tamanhoMaior = 0;

        for (int p = 0; p < tamanhoTabela; p++) {
            int tamanho = 0;
            String texto = "[posicao " + p + "] ";
            No atual = tabela[p];
            if (atual == null) {
                texto = texto + "vazia";
            } else {
                while (atual != null) {
                    if (tamanho > 0) {
                        texto = texto + ", ";
                    }
                    texto = texto + atual.valor;
                    tamanho++;
                    atual = atual.proximo;
                }
            }
            System.out.println(texto);

            somaTamanhos = somaTamanhos + tamanho;
            if (tamanho == 0) {
                posicoesVazias++;
            }
            if (tamanho >= 2) {
                listasEncadeadas++;
            }
            if (tamanho > tamanhoMaior) {
                tamanhoMaior = tamanho;
                posicaoMaior = p;
            }
        }

        System.out.println();
        System.out.println("=== RESUMO ===");

        // monta o texto da maior lista
        String textoMaior = "[posicao " + posicaoMaior + "] ";
        No cursor = tabela[posicaoMaior];
        boolean primeiro = true;
        while (cursor != null) {
            if (!primeiro) {
                textoMaior = textoMaior + ", ";
            }
            textoMaior = textoMaior + cursor.valor;
            primeiro = false;
            cursor = cursor.proximo;
        }
        System.out.println("Maior lista encadeada: posicao " + posicaoMaior + ", tamanho " + tamanhoMaior);
        System.out.println(textoMaior);
        System.out.println("Quantidade de listas encadeadas: " + listasEncadeadas);
        System.out.println("Quantidade de posicoes vazias: " + posicoesVazias);
        System.out.println("Tres ultimos valores inseridos em listas encadeadas:");
        System.out.println("  1) valor " + antepenultimoValor + " na posicao " + antepenultimaPosicao);
        System.out.println("  2) valor " + penultimoValor + " na posicao " + penultimaPosicao);
        System.out.println("  3) valor " + ultimoValor + " na posicao " + ultimaPosicao);
        System.out.println("Total de colisoes: " + totalColisoes);

        // confere se todos os 1000 numeros entraram na tabela
        String conferencia = " (NAO confere com 1000)";
        if (somaTamanhos == quantidadeNumeros) {
            conferencia = " (confere com 1000)";
        }
        System.out.println("Soma dos tamanhos de todas as listas = " + somaTamanhos + conferencia);
    }
}

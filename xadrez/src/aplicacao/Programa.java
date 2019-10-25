package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import camadaJogo.Partida;
import camadaJogo.jogoException;
import camadaJogo.jogoPosicao;
import camadaJogo.PecaXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner entrada = new Scanner(System.in);
		Partida partida = new Partida();
		List<PecaXadrez> capturado = new ArrayList<>();
		
		while (!partida.isCheckMate()) {
			try {
				UI.limpaTela();
				UI.imprimePartida(partida, capturado);
				System.out.println();
				System.out.print("Mover de: ");
				jogoPosicao origem = UI.leJogoPosicao(entrada);
				
				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.limpaTela();
				UI.imprimeTabuleiro(partida.getPecas(), movimentosPossiveis);
				System.out.println();
				System.out.print("Mover para: ");
				jogoPosicao alvo = UI.leJogoPosicao(entrada);
				
				PecaXadrez pecaCapturada = partida.fazMovimentoXadrez(origem, alvo);
				
				if (pecaCapturada != null) {
					capturado.add(pecaCapturada);
				}
				
				if (partida.getPromovido() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String tipo = entrada.nextLine();
					partida.subsPecaPromovida(tipo);
				}
			}
			catch (jogoException e) {
				System.out.println(e.getMessage());
				entrada.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				entrada.nextLine();
			}
		}
		UI.limpaTela();
		UI.imprimePartida(partida, capturado);
	}

}

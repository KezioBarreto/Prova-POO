package camadaJogo;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.King;
import xadrez.pecas.Rook;
import xadrez.pecas.Queen;
import xadrez.pecas.Knight;
import xadrez.pecas.Pawn;
import xadrez.pecas.Bishop;

public class Partida {
	private Tabuleiro tab;
	private int turno;
	private Cor jogadorAtual;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassantVulneravel;
	private PecaXadrez promovido;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public Partida() {
		tab = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		initialSetup();
	}
	
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	public PecaXadrez getEnPassantVulneravel() {
		return enPassantVulneravel;
	}

	public PecaXadrez getPromovido() {
		return promovido;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tab.getLinhas()][tab.getColunas()];
		
		for(int x=0; x < tab.getLinhas(); x++) {
			for(int y=0; y < tab.getColunas(); y++) {
				mat[x][y] = (PecaXadrez) tab.peca(x, y);
			}
		}
		return mat;
	}
	

	public boolean[][] movimentosPossiveis(jogoPosicao origem) {
		Posicao pos = origem.toPosition();
		validaOrigem(pos);
		return tab.peca(pos).movimentosPossiveis();
	}
	
	public PecaXadrez fazMovimentoXadrez(jogoPosicao origem, jogoPosicao alvo) {
		Posicao source = origem.toPosition();
		Posicao target = alvo.toPosition();
		validaOrigem(source);
		validaAlvo(source, target);
		Peca pecaCapturada = movimento(source, target);
		
		if (testaCheck(jogadorAtual)) {
			dezfazMovimento(source, target, pecaCapturada);
			throw new jogoException("Voce nao pode se por em Check");
		}
		
		PecaXadrez movedPiece = (PecaXadrez)tab.peca(target);
		
		// #specialmove promotion
		promovido = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getCor() == Cor.WHITE && target.getLinha() == 0) || (movedPiece.getCor() == Cor.BLACK && target.getLinha() == 7)) {
				promovido = (PecaXadrez)tab.peca(target);
				promovido = subsPecaPromovida("Q");
			}
		}
		
		check = (testaCheck(opponent(jogadorAtual))) ? true : false;

		if (testaCheckMate(opponent(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proximoTurno();
		}
		
		// #mov. especial - en passant
		if (movedPiece instanceof Pawn && (target.getLinha() == source.getLinha() - 2 || target.getLinha() == source.getLinha() + 2)) {
			enPassantVulneravel = movedPiece;
		}
		else {
			enPassantVulneravel = null;
		}
		
		return (PecaXadrez)pecaCapturada;
	}
	
	public PecaXadrez subsPecaPromovida(String tipo) {
		if (promovido == null) {
			throw new IllegalStateException("Nao ha peca a ser promovida");
		}
		if (!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("R") & !tipo.equals("Q")) {
			throw new InvalidParameterException("Tipo invalido para promocao");
		}
		
		Posicao pos = promovido.getJogoPosicao().toPosition();
		Peca p = tab.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promovido.getCor());
		tab.colocaPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if (tipo.equals("B")) return new Bishop(tab, cor);
		if (tipo.equals("N")) return new Knight(tab, cor);
		if (tipo.equals("Q")) return new Queen(tab, cor);
		return new Rook(tab, cor);
	}
	
	private void colocaNovaPeca(char col, int lin, Peca p) {
		tab.colocaPeca(p, new jogoPosicao(col, lin).toPosition());
	}

	private Peca movimento(Posicao origem, Posicao alvo) {
		PecaXadrez p = (PecaXadrez)tab.removePeca(origem);
		p.incrementaContMovimento();
		Peca pecaCapturada = tab.removePeca(alvo);
		tab.colocaPeca(p, alvo);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		// #specialmove castling kingside rook
		if (p instanceof King && alvo.getColuna() == origem.getColuna() + 2) {
			Posicao sourceT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao targetT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez rook = (PecaXadrez)tab.removePeca(sourceT);
			tab.colocaPeca(rook, targetT);
			rook.incrementaContMovimento();
		}

		// #specialmove castling queenside rook
		if (p instanceof King && alvo.getColuna() == origem.getColuna() - 2) {
			Posicao sourceT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao targetT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez rook = (PecaXadrez)tab.removePeca(sourceT);
			tab.colocaPeca(rook, targetT);
			rook.incrementaContMovimento();
		}		
		
		// #specialmove en passant
		if (p instanceof Pawn) {
			if (origem.getColuna() != alvo.getColuna() && pecaCapturada == null) {
				Posicao pawnPosition;
				if (p.getCor() == Cor.WHITE) {
					pawnPosition = new Posicao(alvo.getLinha() + 1, alvo.getColuna());
				}
				else {
					pawnPosition = new Posicao(alvo.getLinha() - 1, alvo.getColuna());
				}
				pecaCapturada = tab.removePeca(pawnPosition);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		
		return pecaCapturada;
	}
	
	private void dezfazMovimento(Posicao origem, Posicao alvo, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tab.removePeca(alvo);
		p.decrementaContMovimento();
		tab.colocaPeca(p, origem);
		
		if (pecaCapturada != null) {
			tab.colocaPeca(pecaCapturada, alvo);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// #specialmove castling kingside rook
		if (p instanceof King && alvo.getColuna() == origem.getColuna() + 2) {
			Posicao sourceT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao targetT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez rook = (PecaXadrez)tab.removePeca(targetT);
			tab.colocaPeca(rook, sourceT);
			rook.decrementaContMovimento();
		}

		// #specialmove castling queenside rook
		if (p instanceof King && alvo.getColuna() == origem.getColuna() - 2) {
			Posicao sourceT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao targetT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez rook = (PecaXadrez)tab.removePeca(targetT);
			tab.colocaPeca(rook, sourceT);
			rook.decrementaContMovimento();
		}
		
		// #specialmove en passant
		if (p instanceof Pawn) {
			if (origem.getColuna() != alvo.getColuna() && pecaCapturada == enPassantVulneravel) {
				PecaXadrez pawn = (PecaXadrez)tab.removePeca(alvo);
				Posicao pawnPosition;
				if (p.getCor() == Cor.WHITE) {
					pawnPosition = new Posicao(3, alvo.getColuna());
				}
				else {
					pawnPosition = new Posicao(4, alvo.getColuna());
				}
				tab.colocaPeca(pawn, pawnPosition);
			}
		}
	}

	private void validaOrigem(Posicao pos) {
		if (!tab.temPeca(pos)) {
			throw new jogoException("Nao ha peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaXadrez)tab.peca(pos)).getCor()) {
			throw new jogoException("A peca escolhida nao eh sua");
		}
		if (!tab.peca(pos).existeMovimentoPossivel()) {
			throw new jogoException("Nao ha movimentos disponiveis para a peca");
		}
	}
	
	private void validaAlvo(Posicao source, Posicao target) {
		if (!tab.peca(source).movimentoPossivel(target)) {
			throw new jogoException("A peca nao pode ir ao alvo selecionado");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private Cor opponent(Cor cor) {
		return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private PecaXadrez king(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof King) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Nao ha nenhum rei " + cor + " no tabuleiro");
	}
	
	private boolean testaCheck(Cor cor) {
		Posicao kingPosition = king(cor).getJogoPosicao().toPosition();
		List<Peca> pecasInimigas = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == opponent(cor)).collect(Collectors.toList());
		for (Peca p : pecasInimigas) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[kingPosition.getLinha()][kingPosition.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testaCheckMate(Cor cor) {
		if (!testaCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<tab.getLinhas(); i++) {
				for (int j=0; j<tab.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao source = ((PecaXadrez)p).getJogoPosicao().toPosition();
						Posicao target = new Posicao(i, j);
						Peca pecasCapturadas = movimento(source, target);
						boolean testCheck = testaCheck(cor);
						dezfazMovimento(source, target, pecasCapturadas);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void initialSetup() {
        colocaNovaPeca('a', 1, new Rook(tab, Cor.WHITE));
        colocaNovaPeca('b', 1, new Knight(tab, Cor.WHITE));
        colocaNovaPeca('c', 1, new Bishop(tab, Cor.WHITE));
        colocaNovaPeca('d', 1, new Queen(tab, Cor.WHITE));
        colocaNovaPeca('e', 1, new King(tab, Cor.WHITE, this));
        colocaNovaPeca('f', 1, new Bishop(tab, Cor.WHITE));
        colocaNovaPeca('g', 1, new Knight(tab, Cor.WHITE));
        colocaNovaPeca('h', 1, new Rook(tab, Cor.WHITE));
        colocaNovaPeca('a', 2, new Pawn(tab, Cor.WHITE, this));
        colocaNovaPeca('b', 2, new Pawn(tab, Cor.WHITE, this));
        colocaNovaPeca('c', 2, new Pawn(tab, Cor.WHITE, this));
        colocaNovaPeca('d', 2, new Pawn(tab, Cor.WHITE, this));
        colocaNovaPeca('e', 2, new Pawn(tab, Cor.WHITE, this));
        colocaNovaPeca('f', 2, new Pawn(tab, Cor.WHITE, this));
        colocaNovaPeca('g', 2, new Pawn(tab, Cor.WHITE, this));
        colocaNovaPeca('h', 2, new Pawn(tab, Cor.WHITE, this));

        colocaNovaPeca('a', 8, new Rook(tab, Cor.BLACK));
        colocaNovaPeca('b', 8, new Knight(tab, Cor.BLACK));
        colocaNovaPeca('c', 8, new Bishop(tab, Cor.BLACK));
        colocaNovaPeca('d', 8, new Queen(tab, Cor.BLACK));
        colocaNovaPeca('e', 8, new King(tab, Cor.BLACK, this));
        colocaNovaPeca('f', 8, new Bishop(tab, Cor.BLACK));
        colocaNovaPeca('g', 8, new Knight(tab, Cor.BLACK));
        colocaNovaPeca('h', 8, new Rook(tab, Cor.BLACK));
        colocaNovaPeca('a', 7, new Pawn(tab, Cor.BLACK, this));
        colocaNovaPeca('b', 7, new Pawn(tab, Cor.BLACK, this));
        colocaNovaPeca('c', 7, new Pawn(tab, Cor.BLACK, this));
        colocaNovaPeca('d', 7, new Pawn(tab, Cor.BLACK, this));
        colocaNovaPeca('e', 7, new Pawn(tab, Cor.BLACK, this));
        colocaNovaPeca('f', 7, new Pawn(tab, Cor.BLACK, this));
        colocaNovaPeca('g', 7, new Pawn(tab, Cor.BLACK, this));
        colocaNovaPeca('h', 7, new Pawn(tab, Cor.BLACK, this));
	}
}

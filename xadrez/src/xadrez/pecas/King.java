package xadrez.pecas;

import camadaJogo.Cor;
import camadaJogo.Partida;
import camadaJogo.PecaXadrez;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public class King extends PecaXadrez {

	private Partida partida;

	public King(Tabuleiro tab, Cor cor, Partida partida) {
		super(tab, cor);
		this.partida = partida;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTab().peca(pos);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testRookCastling(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTab().peca(pos);
		return p != null && p instanceof Rook && p.getCor() == getCor() && p.getContMovimentos() == 0;
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		// above
		p.setValues(pos.getLinha() - 1, pos.getColuna());
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// below
		p.setValues(pos.getLinha() + 1, pos.getColuna());
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// left
		p.setValues(pos.getLinha(), pos.getColuna() - 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// right
		p.setValues(pos.getLinha(), pos.getColuna() + 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nw
		p.setValues(pos.getLinha() - 1, pos.getColuna() - 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// ne
		p.setValues(pos.getLinha() - 1, pos.getColuna() + 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sw
		p.setValues(pos.getLinha() + 1, pos.getColuna() - 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// se
		p.setValues(pos.getLinha() + 1, pos.getColuna() + 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// #specialmove castling
		if (getContMovimentos() == 0 && !partida.isCheck()) {
			// #specialmove castling kingside rook
			Posicao posT1 = new Posicao(pos.getLinha(), pos.getColuna() + 3);
			if (testRookCastling(posT1)) {
				Posicao p1 = new Posicao(pos.getLinha(), pos.getColuna() + 1);
				Posicao p2 = new Posicao(pos.getLinha(), pos.getColuna() + 2);
				if (getTab().peca(p1) == null && getTab().peca(p2) == null) {
					mat[pos.getLinha()][pos.getColuna() + 2] = true;
				}
			}
			// #specialmove castling queenside rook
			Posicao posT2 = new Posicao(pos.getLinha(), pos.getColuna() - 4);
			if (testRookCastling(posT2)) {
				Posicao p1 = new Posicao(pos.getLinha(), pos.getColuna() - 1);
				Posicao p2 = new Posicao(pos.getLinha(), pos.getColuna() - 2);
				Posicao p3 = new Posicao(pos.getLinha(), pos.getColuna() - 3);
				if (getTab().peca(p1) == null && getTab().peca(p2) == null && getTab().peca(p3) == null) {
					mat[pos.getLinha()][pos.getColuna() - 2] = true;
				}
			}
		}
		
		return mat;
	}
}

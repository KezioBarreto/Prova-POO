package xadrez.pecas;

import camadaJogo.Cor;
import camadaJogo.PecaXadrez;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public class Knight extends PecaXadrez {
    public Knight(Tabuleiro tab, Cor cor) {
		super(tab, cor);
	}

	@Override
	public String toString() {
		return "N";
	}

	private boolean canMove(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTab().peca(pos);
		return p == null || p.getCor() != getCor();
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		p.setValues(pos.getLinha() - 1, pos.getColuna() - 2);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(pos.getLinha() - 2, pos.getColuna() - 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(pos.getLinha() - 2, pos.getColuna() + 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(pos.getLinha() - 1, pos.getColuna() + 2);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(pos.getLinha() + 1, pos.getColuna() + 2);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(pos.getLinha() + 2, pos.getColuna() + 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(pos.getLinha() + 2, pos.getColuna() - 1);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValues(pos.getLinha() + 1, pos.getColuna() - 2);
		if (getTab().posExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}

package xadrez.pecas;

import camadaJogo.Cor;
import camadaJogo.PecaXadrez;
import tabuleiro.Tabuleiro;
import tabuleiro.Posicao;

public class Rook extends PecaXadrez {
	public Rook(Tabuleiro tab, Cor cor) {
		super(tab, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		// above
		p.setValues(pos.getLinha() - 1, pos.getColuna());
		while (getTab().posExists(p) && !getTab().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTab().posExists(p) && temPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// left
		p.setValues(pos.getLinha(), pos.getColuna() - 1);
		while (getTab().posExists(p) && !getTab().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTab().posExists(p) && temPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// right
		p.setValues(pos.getLinha(), pos.getColuna() + 1);
		while (getTab().posExists(p) && !getTab().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTab().posExists(p) && temPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// below
		p.setValues(pos.getLinha() + 1, pos.getColuna());
		while (getTab().posExists(p) && !getTab().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTab().posExists(p) && temPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}

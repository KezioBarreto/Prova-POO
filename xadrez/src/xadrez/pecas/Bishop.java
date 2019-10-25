package xadrez.pecas;

import tabuleiro.Tabuleiro;
import tabuleiro.Posicao;
import camadaJogo.PecaXadrez;
import camadaJogo.Cor;

public class Bishop extends PecaXadrez {

	public Bishop(Tabuleiro tab, Cor cor) {
		super(tab, cor);
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		// nw
		p.setValues(pos.getLinha() - 1, pos.getColuna() - 1);
		while (getTab().posExists(p) && !getTab().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTab().posExists(p) && temPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// ne
		p.setValues(pos.getLinha() - 1, pos.getColuna() + 1);
		while (getTab().posExists(p) && !getTab().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTab().posExists(p) && temPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// se
		p.setValues(pos.getLinha() + 1, pos.getColuna() + 1);
		while (getTab().posExists(p) && !getTab().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTab().posExists(p) && temPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// sw
		p.setValues(pos.getLinha() + 1, pos.getColuna() - 1);
		while (getTab().posExists(p) && !getTab().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTab().posExists(p) && temPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}

package xadrez.pecas;

import camadaJogo.Cor;
import camadaJogo.PecaXadrez;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public class Queen extends PecaXadrez {

	public Queen(Tabuleiro tab, Cor cor) {
		super(tab, cor);
	}

	@Override
	public String toString() {
		return "Q";
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

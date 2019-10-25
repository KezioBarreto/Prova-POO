package xadrez.pecas;

import camadaJogo.Cor;
import camadaJogo.Partida;
import camadaJogo.PecaXadrez;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public class Pawn extends PecaXadrez {
    private Partida partida;

    public Pawn(Tabuleiro tab, Cor cor, Partida partida) {
		super(tab, cor);
		this.partida = partida;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTab().getLinhas()][getTab().getColunas()];
		
		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.WHITE) {
			p.setValues(pos.getLinha() - 1, pos.getColuna());
			if (getTab().posExists(p) && !getTab().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(pos.getLinha() - 2, pos.getColuna());
			Posicao p2 = new Posicao(pos.getLinha() - 1, pos.getColuna());
			if (getTab().posExists(p) && !getTab().temPeca(p) && getTab().posExists(p2) && !getTab().temPeca(p2) && getContMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(pos.getLinha() - 1, pos.getColuna() - 1);
			if (getTab().posExists(p) && temPecaInimiga(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.setValues(pos.getLinha() - 1, pos.getColuna() + 1);
			if (getTab().posExists(p) && temPecaInimiga(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}	
			
			// #specialmove en passant white
			if (pos.getLinha() == 3) {
				Posicao left = new Posicao(pos.getLinha(), pos.getColuna() - 1);
				if (getTab().posExists(left) && temPecaInimiga(left) && getTab().peca(left) == partida.getEnPassantVulneravel()) {
					mat[left.getLinha() - 1][left.getColuna()] = true;
				}
				Posicao right = new Posicao(pos.getLinha(), pos.getColuna() + 1);
				if (getTab().posExists(right) && temPecaInimiga(right) && getTab().peca(right) == partida.getEnPassantVulneravel()) {
					mat[right.getLinha() - 1][right.getColuna()] = true;
				}
			}
		}
		else {
			p.setValues(pos.getLinha() + 1, pos.getColuna());
			if (getTab().posExists(p) && !getTab().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(pos.getLinha() + 2, pos.getColuna());
			Posicao p2 = new Posicao(pos.getLinha() + 1, pos.getColuna());
			if (getTab().posExists(p) && !getTab().temPeca(p) && getTab().posExists(p2) && !getTab().temPeca(p2) && getContMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(pos.getLinha() + 1, pos.getColuna() - 1);
			if (getTab().posExists(p) && temPecaInimiga(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.setValues(pos.getLinha() + 1, pos.getColuna() + 1);
			if (getTab().posExists(p) && temPecaInimiga(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// #specialmove en passant black
			if (pos.getLinha() == 4) {
				Posicao left = new Posicao(pos.getLinha(), pos.getColuna() - 1);
				if (getTab().posExists(left) && temPecaInimiga(left) && getTab().peca(left) == partida.getEnPassantVulneravel()) {
					mat[left.getLinha() + 1][left.getColuna()] = true;
				}
				Posicao right = new Posicao(pos.getLinha(), pos.getColuna() + 1);
				if (getTab().posExists(right) && temPecaInimiga(right) && getTab().peca(right) == partida.getEnPassantVulneravel()) {
					mat[right.getLinha() + 1][right.getColuna()] = true;
				}
			}			
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}

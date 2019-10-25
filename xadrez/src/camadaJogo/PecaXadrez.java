package camadaJogo;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {
	
	private Cor cor;
	private int contMovimentos;

	public PecaXadrez(Tabuleiro tab, Cor cor) {
		super(tab);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	public int getContMovimentos() {
		return contMovimentos;
	}
	
	public void incrementaContMovimento() {
		contMovimentos++;
	}
	
	public void decrementaContMovimento() {
		contMovimentos--;
	}
	
	public jogoPosicao getJogoPosicao() {
		return jogoPosicao.fromPosition(pos);
	}
	
	protected boolean temPecaInimiga(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTab().peca(pos);
		return p != null && p.getCor() != cor;
	}
}

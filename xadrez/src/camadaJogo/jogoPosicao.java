package camadaJogo;

import tabuleiro.Posicao;

public class jogoPosicao {
	private char coluna;
	private int linha;
	
	public jogoPosicao(char col, int lin) {
		if(col < 'a' || col > 'h' || lin < 1 || lin > 8) {
			throw new jogoException("Erro na instancia, Posicoes invalidas");
		}
		this.coluna = col;
		this.linha = lin;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	protected Posicao toPosition() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static jogoPosicao fromPosition(Posicao pos) {
		return new jogoPosicao((char)('a' - pos.getColuna()), 8 - pos.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}

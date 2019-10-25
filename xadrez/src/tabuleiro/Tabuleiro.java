package tabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if (!posExists(linha, coluna)) {
			throw new tabException("Posicao invalida");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao pos) {
		if (!posExists(pos)) {
			throw new tabException("Posicao invalida");
		}
		return pecas[pos.getLinha()][pos.getColuna()];
	}
	
	public void colocaPeca(Peca peca, Posicao pos) {
		if (temPeca(pos)) {
			throw new tabException("Ja existe uma peca nessa posicao" + pos);
		}
		pecas[pos.getLinha()][pos.getColuna()] = peca;
		peca.pos = pos;
	}
	
	public Peca removePeca(Posicao pos) {
		if (!posExists(pos)) {
			throw new tabException("Posicao invalida" + pos);
		}
		if (peca(pos) == null) {
			return null;
		}
		Peca aux = peca(pos);
		aux.pos = null;
		pecas[pos.getLinha()][pos.getColuna()] = null;
		return aux;
	}
	
	public boolean posExists(int lin, int col) {
		return ( lin >= 0 && lin < linhas && col >= 0 && col < colunas );
	}
	
	public boolean posExists(Posicao pos) {
		return posExists(pos.getLinha(), pos.getColuna());
	}
	
	public boolean temPeca(Posicao pos) {
		if (!posExists(pos)) {
			throw new tabException("Posicao invalida");
		}
		return peca(pos) != null;
	}

}

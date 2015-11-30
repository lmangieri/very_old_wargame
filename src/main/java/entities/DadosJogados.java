package entities;

import java.util.ArrayList;
import java.util.List;

public class DadosJogados {
	public int atqLost;
	public int defLost;
	public List<Integer> dadosAtaque;
	public List<Integer> dadosDefense;
	
	
	public void setDadosAtaque(List<Integer> dadosAtaque) {
		this.dadosAtaque = dadosAtaque;
	}
	public void setDadosDefense(List<Integer> dadosDefense) {
		this.dadosDefense = dadosDefense;
	}
	
	public DadosJogados() {
		dadosAtaque = new ArrayList<>();
		dadosDefense = new ArrayList<>();
		this.atqLost = 0;
		this.defLost = 0;
	}
}

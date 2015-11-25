package algorithm;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import auxiliaryEntities.DadosJogados;

public class MathAlgorithm {
	public static int getRandomInt(int maxValue) {
		SecureRandom secRandom = new SecureRandom();

		long rand = secRandom.nextLong();
		long timeNano = System.nanoTime();


		int result = (int) ((rand+timeNano)%maxValue);
		
		if(result < 0) {
			result = result * -1;
		}
		return result;
	}
	
	public static int distanceBetween(int x1, int y1, int x2, int y2) {
		float dist = (float) Math.sqrt( Math.pow( (x1 - x2),2 ) +
                Math.pow( (y1 - y2),2 ) );
		
		return (int) dist;
	}
	
	public static DadosJogados war(int natt, int ndef) {
		int count;
		List<Integer> dadosAtaque = new ArrayList<>();
		if(natt > 3) {
			natt = 3;
		}
		if(ndef > 3) {
			ndef = 3;
		}
		
		for(count = 0; count < natt; count++) {
			dadosAtaque.add(getRandomInt(6));
		}
		
		
		List<Integer> dadosDefense = new ArrayList<>();
		for(count = 0; count < ndef; count++) {
			dadosDefense.add(getRandomInt(6));
		}
		
		
		Collections.sort(dadosAtaque, new Comparator<Integer>() {
			public int compare(Integer u1, Integer u2) {
				if(u1 > u2) {
					return -1;
				} else
				if(u2 > u1) {
					return 1;
				}
				return 0;
			}
		});
		
		Collections.sort(dadosDefense, new Comparator<Integer>() {
			public int compare(Integer u1, Integer u2) {
				if(u1 > u2) {
					return -1;
				} else
				if(u2 > u1) {
					return 1;
				}
				return 0;
			}
		});
		
		DadosJogados d = new DadosJogados();
		
		
		for(count = 0; count < natt && count < ndef ; count++) {
			if(!(dadosAtaque.get(count) > dadosDefense.get(count))) {
				d.atqLost = d.atqLost + 1;
			} else {
				d.defLost = d.defLost + 1;
			}
		}
		return d;
	}
	
	
}

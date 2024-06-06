package it.uniroma3.diadia.comandi;

public enum Direzione {
	NORD(0) {
		
	},
	
	EST(90) {},
	
	SUD(180) {},
	
	OVEST(270) {};
	
	private final int gradi;

	private Direzione(int gradi) {
		this.gradi = gradi;
	}
	
	public int getGradi() {
		return this.gradi;
	}
	
	public Direzione opposta(Direzione direzione) {
		switch (direzione) {
		
		case NORD: return SUD;
		
		case EST: return OVEST;
		
		case SUD: return NORD;
		
		case OVEST: return EST;
		
		default: return null;
		}
	}
}

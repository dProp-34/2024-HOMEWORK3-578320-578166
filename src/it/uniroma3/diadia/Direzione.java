package it.uniroma3.diadia;

public enum Direzione {
	nord(0) {
	},
	est(90) {
	},
	sud(180) {
	},
	ovest(270) {
	};

	private final int gradi;

	private Direzione(int gradi) {
		this.gradi = gradi;
	}

	public int getGradi() {
		return this.gradi;
	}

	public static Direzione controllaSeEsiste(String s) {
		Direzione out = null;
		for (Direzione d : values()) {
			if (d.name().equals(s)) {
				out = d;
				break;
			}
		}
		return out;
	}

	public Direzione opposta(Direzione direzione) {
		switch (direzione) {
		case nord:
			return sud;
		case est:
			return ovest;
		case sud:
			return nord;
		case ovest:
			return est;
		default:
			return null;
		}
	}
}

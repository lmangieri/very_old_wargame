package enums;

public enum ContinentNames {
	AMERICA_DO_SUL("América do Sul"),
	AMERICA_DO_NORTE("América do Norte"),
	AFRICA("África"),
	EUROPA("Europa"),
	ASIA("Ásia"),
	OCEANIA("Oceania");
	
	private String name;
	
	ContinentNames(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}	
}

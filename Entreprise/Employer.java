//package Entreprise;

public abstract class Employer {
	public
	String nom;
	double salaire;
	int numP;
	String langage;

	Entreprise entreprise;

	Employer(String nom, int numP, double salaire) {
		this.nom=nom;
		this.numP=numP;
		this.salaire=salaire;
		this.langage="";
	}
	Employer() {
		this.nom="";
		this.salaire=0;
		this.numP=0;
		this.langage="";
	}

	public
	void addEntreprise(Entreprise companie){
		if(this.entreprise==null){
			this.entreprise = companie;
		}
	}

	// ACCESSEURS
	void setLangage(String langage) {
		this.langage = langage;
	}
	String getLangage(){
		return this.langage;
	}
	String getNom(){
		return this.nom;
	}
	int getNumPaye(){
		return this.numP;
	}
	//abstract double getSalaire();
	double getSalaire(){
		double bonusJava=0, salaire = this.salaire;
		if(this.langage.equals("Java")){
			bonusJava = (salaire*0.1);
		}
		return salaire+bonusJava;
	}
	public abstract void maj();
	public abstract String toString();

}

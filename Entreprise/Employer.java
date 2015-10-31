//package Entreprise;

public abstract class Employer {
	public
	String nom;
	double salaire;
	int numP;
	String langage;
	
	Entreprise entreprise;
	protected
	Employer(String nom, int numP, double salaire) {
		this.nom=nom;
		this.numP=numP;
		this.salaire=salaire;
	}
	Employer() {}

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
	abstract double getSalaire();
	public
	abstract String toString();

}

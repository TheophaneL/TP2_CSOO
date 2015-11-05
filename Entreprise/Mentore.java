//package Entreprise;

public class Mentore extends Employer {

	public
	Mentor mentor;

	Mentore(String nom, int numP, double salaire) {
		super(nom, numP, salaire);
	}

	Mentore(){
		super();
	}

	void addMentor(Mentor mentor) {
		// Ajout du mentor seulment sur le langage de programmation correspond
		this.mentor = mentor;
	}

	double getSalaire() {
		double bonusJava=0, salaire = this.salaire;
		if(this.langage.equals("Java")){
			bonusJava = (salaire*0.1);
		}
		return salaire+bonusJava;
	}

	public String toString(){
		String buff,buff2;
		if(mentor==null){
			buff ="Nom : "+this.nom+" // Employé qui n'a pas de mentoré";
			buff2="";
		}else{
			buff ="Nom : "+this.nom+" // Mentoré";
			buff2="\nSon mentor est "+this.mentor.getNom()+" avec le numero de paie "+this.mentor.getNumPaye();
		}
		buff += "\nNumero de paie : "+this.numP
				+"\nLangage : "+this.langage
				+"\nSalaire de base : "+this.salaire
				+buff2
				+"\nSalaire du mois : "+this.getSalaire();

		return buff;
	}


}
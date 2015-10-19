package Entreprise;

import java.util.HashSet;

public class Mentor extends Employer {

	public
	HashSet<Mentore> mentees;

	public
	Mentor(String nom, int numP, double salaire) {
		super(nom, numP, salaire);
		mentees = new HashSet<Mentore>();
	}
	
	Mentor(){
		super();
	}

	void addMentee(Mentore mentee) {
		// Ajout du mentee seulement si le langage de programmation correpond
		mentees.add(mentee);
	}

	double getSalaire() {
		double salaire, bonusJava=0, bonusMentore=0;
		salaire = this.salaire;
		if(this.langage =="Java"){
			bonusJava = (salaire*0.1);
		}
		if(!this.mentees.isEmpty()){
			bonusMentore = salaire*(0.05*this.mentees.size());
		}
		return salaire+bonusJava+bonusMentore;
	}
	
	public String toString(){
		String buff,buff2;
		buff2="";
		if(!mentees.isEmpty()){			
			for (Mentore mentore : mentees) {
				buff2+="\n"+mentore.getNom()+" avec le numero de paie "+mentore.getNumPaye();
			}
		}
		buff = "Nom : "+this.nom+" // Mentor"
				+"\nNumero de paie : "+this.numP
				+"\nLangage : "+this.langage
				+"\nSalaire de base : "+this.salaire
				+buff2
				+"\nSalaire du mois : "+this.getSalaire();

		return buff;
	}

}
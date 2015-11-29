//package Entreprise;

import java.util.HashSet;
import java.util.Iterator;

public class Mentor extends Employer {

	public HashSet<Mentore> mentees;

	public	Mentor(String nom, int numP, double salaire) {
		super(nom, numP, salaire);
		mentees = new HashSet<Mentore>();
	}

	public Mentor(){
		this("",0,0.0);
	}

	public void addMentee(Mentore mentee) {
		// Ajout du mentee seulement si le langage de programmation correpond
		mentees.add(mentee);
	}

	public void setLangage(String langage){
		super.setLangage(langage);
		if(!this.mentees.isEmpty()){
			Iterator<Mentore> i=this.mentees.iterator();
			while(i.hasNext()){
				i.next().maj();
			}
			this.maj();
		}
	}

	public void maj(){
		Mentore m = null;
		Iterator<Mentore> i=this.mentees.iterator();
		while(i.hasNext()){
			m = i.next();
			if(!m.getLangage().equals(this.langage)){
				i.remove();
			}
		}
	}

	public double getSalaire() {
		double salairebonusJava=0, bonusMentore=0;
		salairebonusJava = super.getSalaire();

		if(!this.mentees.isEmpty()){
			bonusMentore = this.salaire*(0.05*this.mentees.size());
		}
		return salairebonusJava+bonusMentore;
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
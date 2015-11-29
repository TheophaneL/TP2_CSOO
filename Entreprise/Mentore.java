//package Entreprise;

public class Mentore extends Employer {

	public Mentor mentor;

	public Mentore(String nom, int numP, double salaire) {
		super(nom, numP, salaire);
	}

	public Mentore(){
		this("",0,0.0);
	}

	public void addMentor(Mentor mentor) {
		// Ajout du mentor seulment sur le langage de programmation correspond
		this.mentor = mentor;
	}

	public void setLangage(String langage){
		super.setLangage(langage);
		if(this.mentor!=null){
			this.mentor.maj();
			this.maj();
		}
	}

	public void maj(){
		if(!mentor.getLangage().equals(this.langage)){
			this.mentor=null;
		}
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
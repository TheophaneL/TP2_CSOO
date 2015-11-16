package Entreprise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;


public class Entreprise {

	private
	String nom;
	HashSet<Employer> employers;

	public
	Entreprise(String nom) {
		this.nom = nom;
		employers = new HashSet<Employer>();
	}

	void addEmployer(Employer employer) {
		Employer emp = null;
		if(!employers.isEmpty()){
			emp = this.getEmployee(employer.getNumPaye());
		}
		if(emp == null){
			employers.add(employer);
			employer.addEntreprise(this);
		}else{
			if(employer.getNom().equals(emp.getNom())){
				System.err.println("L'employer "+employer.getNom()+" au matricule "+employer.getNumPaye()+" est déjà enregistré");
			}else{
				System.err.println("Le matricule "+employer.getNumPaye()+" est déjà utilisé");
			}
			System.err.println("Renseignez un autre numéro de paye");
		}
	}

	void addEmployer(Employer employer, int mentorNumpay) {
		// Un mentoree doit pratiquer le même langage de prog que le mentor
		// Dans le cas contraire il est simplement ajouté a la liste des employer
		// S'il existe déjà, la fonction fait l'opération de setup Mentor/Mentoré
		Employer mentor = null;
		Employer menteS = null;
		if(!employers.isEmpty()){
			mentor = this.getEmployee(mentorNumpay);
			menteS = this.getEmployee(employer.getNumPaye());
		}
		if(mentor == null){
			System.err.println("Il n'existe pas de mentor avec le numéro "+mentorNumpay);
		}else if(menteS == null){
			employers.add(employer);
			if( employer.getLangage().equals(mentor.getLangage())){
				if(mentor instanceof Mentore){
					this.changeStatut(mentor);
					mentor = this.getEmployee(mentorNumpay);
				}
				((Mentore)employer).addMentor((Mentor)mentor);
				((Mentor)mentor).addMentee((Mentore)employer);
			}else{
				System.err.println("L'employer "+employer.getNom()+" ne pratique pas le même langage que le mentor "+mentor.getNom()+"\n");
			}
		}else{
			if( menteS.getLangage().equals(mentor.getLangage())){
				if(mentor instanceof Mentore){
					this.changeStatut(mentor);
					mentor = this.getEmployee(mentorNumpay);
				}
				((Mentore)menteS).addMentor((Mentor)mentor);
				((Mentor)mentor).addMentee((Mentore)menteS);
				//System.out.println("L'employer "+employer.getNom()+" au matricule "+employer.getNumPaye()+" est déjà enregistré");
			}else{
				System.err.println("L'employer "+menteS.getNom()+" ne pratique pas le même langage que le mentor "+mentor.getNom()+"\n");
				//System.out.println("Le matricule "+employer.getNumPaye()+" est déjà utilisé");
			}
			//System.out.println("Renseignez un autre numéro de paye");
		}
	}


	void displayEmployees() {
		System.out.println("Calculs des salaires mensuels des employés de "+this.nom+"\n");
		for (Employer employer : employers) {
			System.out.println(employer.toString()+"\n");
		}
	}

	void displayReport(String file) {
		PrintWriter fich = null;
		try{
			fich = new PrintWriter(new FileWriter(file));

		}catch(Exception e){
			System.err.println(e);
		}
		try{
			for (Employer employer : employers) {
				fich.println(employer.toString());
				fich.println();
				//System.out.println(employer.toString()+"\n");
			}
		}catch(Exception e){
			System.err.println(e);
		}
		try{
			fich.close();
		}catch(Exception e){
			System.err.println(e);
		}
	}

	public Employer getEmployee(int num_paye) {
		Employer efound= null,ebuff = null;
		boolean employee_found = false;
		Iterator<Employer> i=this.employers.iterator();
		while(i.hasNext() | !employee_found){
			ebuff = i.next();
			if (ebuff.getNumPaye() == num_paye)
				efound =ebuff;
			employee_found = true;
		}
		return efound;
	}

	public Employer getEmployee(String name) {
		Employer efound= null,ebuff = null;
		boolean employee_found = false;
		Iterator<Employer> i=this.employers.iterator();
		while(i.hasNext() | !employee_found){
			ebuff = i.next();
			if (ebuff.getNom().equals(name))
				efound =ebuff;
			employee_found = true;
		}
		return efound;
	}

	void changeStatut(Employer employer){
		if(this.employers.contains(employer)){
			this.employers.remove(employer);
			Employer buff = null;//employer;
			if(employer instanceof Mentor){
				//System.out.println("C'est un mentor");
				// Employer de type Mentor a muter en Mentore
				buff = new Mentore(employer.getNom(),employer.getNumPaye(),employer.getSalaire());
				buff.setLangage(employer.getLangage());

			}else{
				//System.out.println("C'est un mentoré");
				// Employer de type Mentore a muter en Mentor
				buff = new Mentor(employer.getNom(),employer.getNumPaye(),employer.getSalaire());
				buff.setLangage(employer.getLangage());
			}
			this.employers.add(buff);
		}else{
			System.out.println("pas dans la liste");
		}
	}

	void correctStatus(){
		if(!this.employers.isEmpty()){
			Employer ebuff = null;
			boolean stop = false;
			while(!stop){
				Iterator<Employer> it=this.employers.iterator();
				while(it.hasNext()){
					ebuff = it.next();
					if (ebuff instanceof Mentor){
						if(((Mentor)ebuff).mentees.isEmpty()){
							this.changeStatut(ebuff);
							break;
						}
					}
					stop = true;
				}
			}
		}
	}

	public String getNom(){
		return this.nom;
	}

	

}
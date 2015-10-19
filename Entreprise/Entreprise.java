package Entreprise;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;

import sun.misc.Sort;

public class Entreprise {

	public
	String nom;
	HashSet<Employer> employers;

	Entreprise(String nom) {
		this.nom = nom;
		employers = new HashSet<Employer>();
	}

	void addEmployer(Employer employer) {
		// Ajout de l'employer a la liste de l'entreprise et ajout de l'entreprise à l'employer
		employers.add(employer);
		employer.addEntreprise(this);
	}

	void addEmployer(Mentore employer, Mentor mentor) {
		// Un mentoree doit pratiquer le même langage de prog que le mentor
		employers.add(employer);
		if( employer.getLangage() == mentor.getLangage()){
			employer.addMentor(mentor);
			mentor.addMentee(employer);
		}else{
			System.out.println("L'employer "+employer.getNom()+" ne pratique pas le même langage que le mentor "+mentor.getNom()+"\n");
		}
		// Dans le cas contraire il est simplement ajouté a la liste des employer
		// And display error log
	}

	void displayEmployees() {
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


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TODO TODO PETIT
		
		// Create One company
		Entreprise enterprise = new Entreprise("FulguroCode");
		
		// Create all employees
		Mentor mentor1 = new Mentor("Roger", 1, 4000);
		Mentor mentor2 = new Mentor("Huguette", 2, 3500);
		mentor1.setLangage("C++");
		mentor2.setLangage("caml");
		
		Mentore mentoree1 = new Mentore("Kevin", 10, 1500);
		mentoree1.setLangage("C++");
		Mentore mentoree2 = new Mentore("Kevina", 11, 1200);
		mentoree2.setLangage("C++");
		Mentore mentoree3 = new Mentore("Jhonny", 12, 1000);
		mentoree3.setLangage("caml");
		
		enterprise.addEmployer(mentor1);
		enterprise.addEmployer(mentor2);
		
		enterprise.addEmployer(mentoree1, mentor1);
		enterprise.addEmployer(mentoree2, mentor2);
		enterprise.addEmployer(mentoree3, mentor2);
		
		// Display salary of all employees
		enterprise.displayEmployees();
		enterprise.displayReport("FileTest.txt");
		
		// On console and in file.txt

	}

}
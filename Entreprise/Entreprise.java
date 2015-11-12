//package Entreprise;

import java.awt.List;
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
				System.out.println("L'employer "+employer.getNom()+" au matricule "+employer.getNumPaye()+" est déjà enregistré");
			}else{
				System.out.println("Le matricule "+employer.getNumPaye()+" est déjà utilisé");
			}
			System.out.println("Renseignez un autre numéro de paye");
		}
	}

	void addEmployer(Employer employer, int mentorNumpay /*Employer mentor*/) {
		// Un mentoree doit pratiquer le même langage de prog que le mentor
		// Dans le cas contraire il est simplement ajouté a la liste des employer
		// And display error log
		Employer mentor = null;
		Employer menteS = null;
		if(!employers.isEmpty()){
			mentor = this.getEmployee(mentorNumpay);
			menteS = this.getEmployee(employer.getNumPaye());
		}
		if(mentor == null){
			System.out.println("Il n'existe pas de mentor avec le numéro "+mentorNumpay);
		}else if(menteS == null){
			employers.add(employer);
			if( employer.getLangage().equals(mentor.getLangage())){
				((Mentore)employer).addMentor((Mentor)mentor);
				((Mentor)mentor).addMentee((Mentore)employer);
			}else{
				System.out.println("L'employer "+employer.getNom()+" ne pratique pas le même langage que le mentor "+mentor.getNom()+"\n");
			}
		}else{
			if(employer.getNom().equals(menteS.getNom())){
				System.out.println("L'employer "+employer.getNom()+" au matricule "+employer.getNumPaye()+" est déjà enregistré");
			}else{
				System.out.println("Le matricule "+employer.getNumPaye()+" est déjà utilisé");
			}
			System.out.println("Renseignez un autre numéro de paye");
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



	public static void main(String[] args) {
		// TODO List
		/*
		 * - Factoriser getSalaire - Done
		 * - Gérer le changement Mentor-Mentoree quand un mentor change de langage
		 * 
		 */

		// Create One company
		Entreprise enterprise = new Entreprise("FulguroCode");

		// Lecture Fichier d'init
		BufferedReader in = null;
		try{

			in= new BufferedReader(new FileReader("../init.txt"));

			String ligne =null;
			String[] tokens=null;

			String nom;
			int numPaye;
			double paye;
			String langage;
			int numMentor;
			Mentor mentor;

			while((ligne=in.readLine())!=null){
				tokens = ligne.split(",");
				if(tokens.length==5){
					// If 5 elem, mentee with mentor number
					nom = tokens[0];
					numPaye = Integer.parseInt(tokens[1]);
					paye = Double.parseDouble(tokens[2]);
					langage = tokens[3];
					numMentor = Integer.parseInt(tokens[4]);
					Mentore mentore = new Mentore(nom, numPaye, paye);
					mentore.setLangage(langage);
					mentor = (Mentor)enterprise.getEmployee(numMentor);
					if( mentor != null){
						//System.out.println("Langage mentee : "+mentore.getLangage()+" Langage mentor : "+mentor.getLangage());
						enterprise.addEmployer(mentore, numMentor);
					}
				}else{
					// 4 elem for mentor
					nom = tokens[0];
					numPaye = Integer.parseInt(tokens[1]);
					paye = Double.parseDouble(tokens[2]);
					langage = tokens[3];
					mentor = new Mentor(nom, numPaye, paye);
					mentor.setLangage(langage);
					enterprise.addEmployer(mentor);
				}

			}

			in.close();
		}catch(Exception e){
			System.err.println(e);
		}

		// Une fois création de l'entreprise par lecture du fichier
		// Afficher console de controle :
		int exit=0;
		BufferedReader IN = null;

		do{
			int select=-1;
			String ligne=null;
			String[] tokens =null ;
			String employer = null;
			int mentorN = 0;
			int numPaye = 0;
			double salaire = 0;
			String langage = null;

			System.out.println("\n==== Panel de controle de l'entreprise "+enterprise.nom+" ===");
			System.out.println("- Afficher  la liste des employer, saisir 1");
			System.out.println("- Générer le rapport, saisir 2");
			System.out.println("- Chercher un employé en particulier, saisir 3");
			System.out.println("- Modifier qualification d'un employer, saisir 4");
			System.out.println("- Ajouter un employer, saisir 5");
			System.out.println("- Quitter le programme, saisir 0");
			try{
				IN= new BufferedReader(new InputStreamReader(System.in));
			}catch(Exception e){
				System.err.println(e);
			}
			try {
				ligne=IN.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try{
				select = Integer.parseInt(ligne);
			}catch(NumberFormatException nfe){
				System.err.println(nfe);
				System.out.println("Merci de saisir un des numéro indiqué!");
				continue;
			}
			switch (select) {
			// Quitter le programme
			case 0:
				exit=1;
				//System.exit(0);
				break;

			case 1: // Afficher la liste des employers
				enterprise.displayEmployees();
				break;

			case 2: // Générer le rapport
				System.out.println("Indiquer le nom du rapport (rapport.txt par défaut) : ");
				try{
					ligne = IN.readLine();
				}catch(Exception e){
					System.err.println(e);
				}
				if(ligne.isEmpty()) ligne = "rapport.txt";
				enterprise.displayReport("../"+ligne);
				break;

			case 3: // Afficher un employé particulier séléctionné par son numéro de paye
				System.out.println("Saisir numéro de paye de l'employer cherché :");
				try{
					ligne = IN.readLine();
				}catch(Exception e){
					System.err.println(e);
				}
				Employer employ = enterprise.getEmployee(Integer.parseInt(ligne));
				if(employ != null){
					System.out.println(employ);
				}else {
					System.out.println("Il n'existe pas d'employé avec le numéro "+ligne);
				}

				break;

			case 4: // Modifier le langage d'un employer
				System.out.println("Saisir le nom de l'employé a modifier et le nouveau langage :");
				try{
					tokens = IN.readLine().split(" ");
				}catch(Exception e){
					System.err.println(e);
				}

				if(tokens.length !=2){
					System.err.println("Mauvais arguments");
				}else{
					employer =tokens[0];
					langage = tokens[1];
					Employer empl= enterprise.getEmployee(employer);
					empl.setLangage(langage);
				}
				break;

			case 5: // Ajouter un employer
				System.out.println("Renseigner le nouvel employer sous la forme :\n"
						+ "Nom,Numéro de paye,Salaire,Langage,Numero Mentor(optionnel)");
				try{
					tokens = IN.readLine().split(",");
				}catch(Exception e){
					System.err.println(e);
				}
				if(tokens.length==4){
					employer = tokens[0];
					numPaye = Integer.parseInt(tokens[1]);
					salaire = Double.parseDouble(tokens[2]);
					langage = tokens[3];
					Mentor m = new Mentor(employer,numPaye,salaire);
					m.setLangage(langage);
					enterprise.addEmployer(m);

				}else if(tokens.length==5){
					employer = tokens[0];
					numPaye = Integer.parseInt(tokens[1]);
					salaire = Double.parseDouble(tokens[2]);
					langage = tokens[3];
					mentorN = Integer.parseInt(tokens[4]);
					Employer mentee = new Mentore(employer, numPaye, salaire);
					//Employer mentor = enterprise.getEmployee(mentorN);
					//System.out.println(mentor.toString());
					mentee.setLangage(langage);
					enterprise.addEmployer(mentee,mentorN);

				}else{
					System.err.println("Mauvais arguments");
				}
				break;
			default:
				System.out.println("RETRY MOTHAFOCKA!!!!");
				break;
			}

		}while(exit!=1);


		System.out.println("Fin de programme, Fly Safe!!");
	}

}
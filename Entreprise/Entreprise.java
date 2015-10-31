//package Entreprise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;


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
		// Ajout de l'employer a la liste de l'entreprise et ajout de l'entreprise à l'employer
		employers.add(employer);
		employer.addEntreprise(this);
	}

	void addEmployer(Mentore employer, Mentor mentor) {
		// Un mentoree doit pratiquer le même langage de prog que le mentor
		// Dans le cas contraire il est simplement ajouté a la liste des employer
		// And display error log
		employers.add(employer);
		if( employer.getLangage() == mentor.getLangage()){
			employer.addMentor(mentor);
			mentor.addMentee(employer);
		}else{
			System.out.println("L'employer "+employer.getNom()+" ne pratique pas le même langage que le mentor "+mentor.getNom()+"\n");
		}
		
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
					
					//enterprise.addEmployer(mentore, enterprise.employers.c);
				}else{
					// 4 elem for mentor
					nom = tokens[0];
					numPaye = Integer.parseInt(tokens[1]);
					paye = Double.parseDouble(tokens[2]);
					langage = tokens[3];
					Mentor mentor = new Mentor(nom, numPaye, paye);
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
			String mentor = null;
			int numPaye = 0;
			double salaire = 0;
			String langage = null;

			System.out.println("==== Panel de controle de l'entreprise XXX ====");
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
				// Afficher la liste des employers
			case 1:
				enterprise.displayEmployees();
				break;
				// Générer le rapport
			case 2:
				System.out.println("Indiquer le nom du rapport (rapport.txt par défaut) : ");
				try{
					ligne = IN.readLine();
				}catch(Exception e){
					System.err.println(e);
				}
				if(ligne.isEmpty()) ligne = "rapport.txt";
				enterprise.displayReport("../"+ligne);
				break;
				// Afficher un employé particulier séléctionné par son numéro de paye
			case 3:
				System.out.println("Saisir numéro de paye de l'employer cherché :");
				try{
					ligne = IN.readLine();
				}catch(Exception e){
					System.err.println(e);
				}

				break;
				// Modifier le langage d'un employer
			case 4:
				System.out.println("Saisir le nom de l'employé a moddifier et le nouveau langage :");
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
					System.out.println("Employer : "+employer+"\nLangage : "+langage);
				}
				break;
				// Ajouter un employer
			case 5:
				System.out.println("Renseigner le nouvel employer sous la forme :\n"
						+ "Nom,Numéro de paye,Salaire,Langage,Mentore(optionnel)");
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
					mentor = tokens[4];
					Mentore m = new Mentore(employer, numPaye, salaire);
					m.setLangage(langage);
					enterprise.addEmployer(m);

				}else{
					System.err.println("Mauvais arguments");
				}
				break;
			default:
				System.out.println("Mauvais arguments");
				break;
			}

		}while(exit!=1);

		System.out.println("Fin de programme, Fly Safe!!");
/*
		// Create all employees
		Mentor mentor1 = new Mentor("Roger", 1, 4000);
		Mentor mentor2 = new Mentor("Huguette", 2, 3500);
		mentor1.setLangage("C++");
		mentor2.setLangage("caml");

		Mentore mentoree1 = new Mentore("Kevin", 10, 1500);
		mentoree1.setLangage("C++");
		Mentore mentoree2 = new Mentore("Kevina", 11, 1200);
		mentoree2.setLangage("Java");
		Mentore mentoree3 = new Mentore("Jhonny", 12, 1000);
		mentoree3.setLangage("C++");

		enterprise.addEmployer(mentor1);
		enterprise.addEmployer(mentor2);

		enterprise.addEmployer(mentoree1, mentor1);
		enterprise.addEmployer(mentoree2, mentor2);
		enterprise.addEmployer(mentoree3, mentor2);

		// Display salary of all employees
		enterprise.displayEmployees();
		enterprise.displayReport("FileTest.txt");

		// On console and in file.txt
*/
	}

}
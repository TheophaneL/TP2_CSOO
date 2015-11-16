package Entreprise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static void main(String[] args) {
		// TODO List
		/*
		 * - Modifier addEmployer(2) - Done
		 * - Factoriser getSalaire - Done
		 * - Gérer la mise a jours des mentor-montore au changement de langage - Done
		 * - Gérer le changement Mentor-Mentoree - Done
		 * - Move main - Done
		 * 
		 * - Factoriser toString
		 */

		// Create One company
		Entreprise enterprise = new Entreprise("FulguroCode");

		// Lecture Fichier d'init
		BufferedReader in = null;
		try{

			in= new BufferedReader(new FileReader("init.txt"));

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
			//Une fois la lecture faite, comme pendant la création à la volé il n'est pas possible de savoir
			//si un employé est un mentor ou un employer libre, tous ceux sans numéro de mentor sont crées comme mentor
			// > On va considéré que ceux qui ne sont pas des mentor seront des mentore sans mentor.
			// >> Un passage sur la liste d'employés après coup va permettre de typer correctement tous les employés
			// >>> A chaque tour de boucle, on s'assure de bien faire les mises a jours
			enterprise.correctStatus();
			int select=-1;
			String ligne=null;
			String[] tokens =null ;
			String employer = null;
			int mentorN = 0;
			int numPaye = 0;
			double salaire = 0;
			String langage = null;

			System.out.println("\n==== Panel de controle de l'entreprise "+enterprise.getNom()+" ===");
			System.out.println("- Afficher  la liste des employés, saisir 1");
			System.out.println("- Générer le rapport, saisir 2");
			System.out.println("- Chercher un employé en particulié, saisir 3");
			System.out.println("- Modifier qualification d'un employé, saisir 4");
			System.out.println("- Ajouter un employé, saisir 5");
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
				System.out.println("Merci de saisir un des numéros indiqués!");
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
				System.out.println("Saisir numéro de paye de l'employé cherché :");
				try{
					ligne = IN.readLine();
				}catch(Exception e){
					System.err.println(e);
				}
				Employer employ = enterprise.getEmployee(Integer.parseInt(ligne));
				if(employ != null){
					System.out.println(employ);
				}else {
					System.err.println("Il n'existe pas d'employé avec le numéro "+ligne);
				}

				break;

			case 4: // Modifier le langage d'un employer
				System.out.println("Saisir le numéro de l'employé a modifier et le nouveau langage :\nNuméro,Langage");
				try{
					tokens = IN.readLine().split(",");
				}catch(Exception e){
					System.err.println(e);
				}

				if(tokens.length !=2){
					System.err.println("Mauvais arguments");
				}else{
					numPaye = Integer.parseInt(tokens[0]);
					langage = tokens[1];
					Employer empl= enterprise.getEmployee(numPaye);
					empl.setLangage(langage);
				}
				break;

			case 5: // Ajouter un employer
				System.out.println("Pour un nouveau mentor : 1");
				System.out.println("Pour un nouveau mentoré : 2");
				System.out.println("Pour un simple employé : 3");
				try{
					ligne = IN.readLine();
				}catch(Exception e){
					System.err.println(e);
				}
				// Mentor ou mentoré
				switch (Integer.parseInt(ligne)) {
				case 1:
					System.out.println("Renseigner le nouveau mentor sous la forme :\nNom,Numéro de paye,Salaire,Langage");
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
						//Recherche si le mentor existe déjà
						if((enterprise.getEmployee(numPaye))!=null){
							System.err.println("Le mentor numéro "+numPaye+" est déjà enregistré dans l'entreprise");
						}else{
							Employer m = new Mentor(employer,numPaye,salaire);
							m.setLangage(langage);
							enterprise.addEmployer(m);
						}

						System.out.println("Indiquer les employés a superviser sous la forme :\nNuméro 1,Numéro 2,...");
						try{
							tokens = IN.readLine().split(",");
						}catch(Exception e){
							System.err.println(e);
						}
						for (String numMentee : tokens) {
							Employer ebuff = enterprise.getEmployee(Integer.parseInt(numMentee));
							if(ebuff!=null){
								if(ebuff instanceof Mentore){
									if(((Mentore)ebuff).mentor == null){
										enterprise.addEmployer(enterprise.getEmployee(Integer.parseInt(numMentee)), numPaye);	
									}else{
										System.err.println("L'employé numéro "+numMentee+" a déjà un mentor" );
									}
								}else{
									System.err.println("L'employé numéro "+numMentee+" est un mentor");
								}
							}else{
								System.err.println("L'employé numéro "+numMentee+" n'est pas enregistré dans l'entreprise");
							}

						}

					}else{
						System.err.println("Mauvais arguments");
					}
					break;
				case 2:
					System.out.println("Renseigner le nouveau mentoré sous la forme :\n"
							+ "Nom,Numéro de paye,Salaire,Langage,Numero du Mentor");
					try{
						tokens = IN.readLine().split(",");
					}catch(Exception e){
						System.err.println(e);
					}
					if(tokens.length==5){
						employer = tokens[0];
						numPaye = Integer.parseInt(tokens[1]);
						salaire = Double.parseDouble(tokens[2]);
						langage = tokens[3];
						mentorN = Integer.parseInt(tokens[4]);
						Employer mentee = new Mentore(employer, numPaye, salaire);
						mentee.setLangage(langage);
						enterprise.addEmployer(mentee,mentorN);
					}else{
						System.err.println("Mauvais arguments");
					}
					break;
				case 3:
					System.out.println("Renseigner le nouvel employé sous la forme :\n"
							+ "Nom,Numéro de paye,Salaire,Langage");
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
						//Recherche si le mentor existe déjà
						if((enterprise.getEmployee(numPaye))!=null){
							System.err.println("Il existe déjà un employé avec le nméro "+numPaye);
						}else{
							Mentor m = new Mentor(employer,numPaye,salaire);
							m.setLangage(langage);
							enterprise.addEmployer(m);
						}
					}else{
						System.err.println("Mauvais arguments");
					}
					break;
				default:
					System.err.println("Mauvais arguments");
					break;
				}
				break;
			default:
				System.err.println("Mauvais arguments");
				break;
			}

		}while(exit!=1);


		System.out.println("Fin de programme, Fly Safe!!");
	}

}

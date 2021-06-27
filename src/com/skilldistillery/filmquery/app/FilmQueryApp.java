package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() {
//    Film film = db.findFilmById(1);
//    System.out.println(film);

//    Actor actor = db.findActorById(5);
//    System.out.println(actor);

	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {

		boolean mainMenu = true;

		while (mainMenu) {
			printMainMenu();
			int option = input.nextInt();
			input.nextLine();

			switch (option) {
			case 1:
				lookUpById(input);
				break;
			case 2:
				lookUpByKeyword(input);
				break;
			case 3:
				mainMenu = false;
				System.out.println("Thanks for visiting our Film Application! Goodbye.");
				break;
			default:
				System.err.println("Invalid entry! Select option 1 - 3");
			}
		}
		input.close();

	}

	public void lookUpById(Scanner sc) {

		System.out.println("Please enter a film ID");
		int filmId = sc.nextInt();
		sc.nextLine();
		Film film = db.findFilmById(filmId);
		if (film == null) {
			System.err.println("No movie with that ID. Please try again.");
		} else {
			System.out.println(film);
			printSubMenu();
			int subChoice = sc.nextInt();
			sc.nextLine();
			switch (subChoice) {
			case 1:
				System.out.println("Returning to Main Menu");
				break;
			case 2:
				System.out.println(db.findFilmByIdDisplayAllData(filmId).detailedInformation());
				break;
			default:
				System.err.println("Invalid entry! Select option 1 - 2");
				System.out.println("Returning to Main Menu");
			}
		}
		
	}

	public void lookUpByKeyword(Scanner sc) {
		System.out.println("Enter a phrase or snippet to match to the movie");
		String keyword = sc.nextLine();
		List<Film> list = db.findFilmByKeyword(keyword);
		if (list.isEmpty()) {
			System.err.println("No movies match that keyword. Please try again.");
		} else {
			for (Film film : list) {
				System.out.println(film);
			}
			// change this to call findFilmByIDDisplayAllData method
			// and prompt user to select the film from the keyword search that they
			// want to see all the details on
			printSubMenu2();
			int subChoice = sc.nextInt();
			sc.nextLine();
			switch (subChoice) {
			case 1:
				System.out.println("Returning to Main Menu");
				break;
			case 2:
				System.out.println("Please enter the film ID");
				int filmId = sc.nextInt();
				sc.nextLine();
				Film film = db.findFilmById(filmId);
				if (film == null) {
					System.err.println("No movie with that ID. Please try again.");
					break;
				} else {
					System.out.println(db.findFilmByIdDisplayAllData(filmId).detailedInformation());
					break;

				}
			default:
				System.err.println("Invalid entry! Select option 1 - 2");
				System.out.println("Returning to Main Menu");
			}
		}

	}

	public void printMainMenu() {
		System.out.println("\n---------------Main Menu---------------");
		System.out.println("1) Look up film by its ID");
		System.out.println("2) Look up a film by a search keyword");
		System.out.println("3) Exit the application");
		System.out.println("---------------------------------------\n");
		System.out.print("Please select a Menu option: \n");
	}

	public void printSubMenu() {
		System.out.println("\n--------------SubMenu--------------");
		System.out.println("1) Return to the Main Menu");
		System.out.println("2) Display all current film details");
		System.out.println("-----------------------------------\n");
		System.out.print("Please select a SubMenu option: \n");
	}

	public void printSubMenu2() {
		System.out.println("\n--------------SubMenu------------------");
		System.out.println("1) Return to the Main Menu");
		System.out.println("2) Use film's ID to display further details");
		System.out.println("---------------------------------------\n");
		System.out.print("Please select a SubMenu option: \n");
	}

}

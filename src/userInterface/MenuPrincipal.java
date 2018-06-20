package userInterface;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import daoClasses.DaoException;
import models.Aluno;
import models.Curso;
import models.Materia;
import models.Professor;
import sistemaAcademico.ControleAcesso;

@SuppressWarnings("unused")

public class MenuPrincipal implements IMenu {

	private boolean menuOn = true;

	ControleAcesso controleAcesso = new ControleAcesso();
	
	MenuAluno menuAluno = new MenuAluno(controleAcesso);
	MenuProfessor menuProfessor = new MenuProfessor(controleAcesso);
	MenuMateria menuMateria = new MenuMateria(controleAcesso);
	MenuCurso menuCurso = new MenuCurso(controleAcesso);

	public void menuControl() {

		while (menuOn) {

			printOptions();

			switch (InputReader.getInputInt()) {
			case 0:
				exit();
				break;

			case 1:
				menuAluno.menuControl();
				break;

			case 2:
				menuProfessor.menuControl();
				break;
				
			case 3:
				menuMateria.menuControl();
				break;
				
			case 4:
				menuCurso.menuControl();
				break;

			default:
				System.out.println("Op��o Inv�lida");

			}
			
			if (menuOn) {
				System.out.println("Pressione ENTER para continuar...");
				InputReader.nextLine();
			}

		}
	}

	public void printOptions() {
		System.out.println("Escolha uma op��o: ");
		System.out.println("0. Sair");
		System.out.println("1. Menu Alunos");
		System.out.println("2. Menu Professores");
		System.out.println("3. Menu Mat�rias");
		System.out.println("4. Menu Cursos");
		System.out.println();
	}

	private void exit() {
		System.out.println("Saindo do sistema acad�mico");
		InputReader.close();
		menuOn = false;
	}

}

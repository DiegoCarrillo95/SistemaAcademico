package userInterface;

import java.util.List;

import models.Professor;
import sistemaAcademico.ControleAcesso;

public class MenuProfessor implements IMenu{
	
	ControleAcesso controleAcesso;
	
	public MenuProfessor(ControleAcesso controleAcesso) {
		this.controleAcesso = controleAcesso;
	}

	@Override
	public void menuControl() {
		printOptions();

		switch (InputReader.getInputInt()) {
		case 0:
			break;

		case 1:
			cadastraProfessor();
			break;

		case 2:
			listarProfessores();
			break;

		default:
			System.out.println("Opção Inválida");

		}
		
	}

	@Override
	public void printOptions() {
		System.out.println("0. Voltar");
		System.out.println("1. Cadastrar professor");
		System.out.println("2. Listar todos os professores");
	}
	
	public void cadastraProfessor() {
		Professor prof = getProfessorFromUser();
		if (prof != null) {
			if (controleAcesso.incluirProfessor(prof)) {
				System.out.println("Professor cadastrado!");
			}
		}
	}

	public void listarProfessores() {
		List<Professor> profs = controleAcesso.listarProfessores();
		if (profs != null) {
			for (int i = 0; i < profs.size(); i++) {
				System.out.println(profs.get(i).toString());
				System.out.println();
			}
		}
	}
	
	public Professor getProfessorFromUser() {
		System.out.println("Digite o nome do professor: ");
		String nome_professor = InputReader.getInputString();
		System.out.println("Digite o código do professor: ");
		int cod_professor = InputReader.getInputInt();
		return new Professor(nome_professor, cod_professor);
	}
}

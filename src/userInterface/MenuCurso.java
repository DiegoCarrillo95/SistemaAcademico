package userInterface;

import java.util.List;

import models.Curso;
import sistemaAcademico.ControleAcesso;

public class MenuCurso implements IMenu {

	ControleAcesso controleAcesso;

	public MenuCurso(ControleAcesso controleAcesso) {
		this.controleAcesso = controleAcesso;
	}

	@Override
	public void menuControl() {
		printOptions();

		switch (InputReader.getInputInt()) {
		case 0:
			break;

		case 1:
			cadastrarCurso();
			break;

		case 2:
			listarCursos();
			break;

		default:
			System.out.println("Op��o Inv�lida");

		}

	}

	@Override
	public void printOptions() {
		System.out.println("Escolha uma op��o: ");
		System.out.println("0. Voltar");
		System.out.println("1. Cadastrar curso");
		System.out.println("2. Listar todos os cursos");

	}

	private void cadastrarCurso() {
		System.out.println("Digite o nome do curso: ");
		String nome_curso = InputReader.getInputString();
		Curso novoCurso = new Curso();
		novoCurso.setNome(nome_curso);
		if (controleAcesso.incluirCurso(novoCurso)) {
			System.out.println("Curso cadastrado!");
		}
	}

	private void listarCursos() {
		List<Curso> cursos = controleAcesso.listarCursos();
		if (cursos != null) {
			for (int i = 0; i < cursos.size(); i++) {
				System.out.println("Curso: " + cursos.get(i).getNome());
				System.out.println("C�digo: " + cursos.get(i).getId());
				System.out.println();
			}
		}
	}

}

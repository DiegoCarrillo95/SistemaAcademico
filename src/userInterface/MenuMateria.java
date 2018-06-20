package userInterface;

import java.util.List;

import models.Aluno;
import models.Curso;
import models.Materia;
import models.Professor;
import sistemaAcademico.ControleAcesso;

public class MenuMateria implements IMenu{

	ControleAcesso controleAcesso;
	
	public MenuMateria(ControleAcesso controleAcesso) {
		this.controleAcesso = controleAcesso;
	}
	
	@Override
	public void menuControl() {
		printOptions();

		switch (InputReader.getInputInt()) {
		case 0:
			break;

		case 1:
			cadastrarMateria();
			break;

		case 2:
			listarMaterias();
			break;

		case 3:
			listarAlunosMatriculadosPorMateria();
			break;

		default:
			System.out.println("Op��o Inv�lida");

		}
		
	}

	@Override
	public void printOptions() {
		System.out.println("0. Voltar");
		System.out.println("1. Cadastrar mat�ria");
		System.out.println("2. Listar todas as mat�rias");
		System.out.println("3. Listar alunos matriculados em mat�ria");
	}
	
	private void cadastrarMateria() {
		Materia materia = getMateriaFromUser();
		if (materia != null) {
			if (controleAcesso.incluirMateria(materia)) {
				System.out.println("Mat�ria cadastrada!");
			}
		}
	}

	private void listarMaterias() {
		List<Materia> materias = controleAcesso.listarMaterias();
		for (int i = 0; i < materias.size(); i++) {
			System.out.println("Nome: " + materias.get(i).getNome());
			System.out.println("C�digo: " + materias.get(i).getCodigoMateria());
			Professor prof_materia_aux = controleAcesso.consultarProfessor(materias.get(i).getCodigoProfessor());
			System.out.println("Professor: "
					+ (prof_materia_aux != null ? prof_materia_aux.getNome() : "Professor n�o cadastrado"));
			Curso curso_materia_aux = controleAcesso.consultarCurso(materias.get(i).getCodigoCurso());
			System.out.println(
					"Curso: " + (curso_materia_aux != null ? curso_materia_aux.getNome() : "Curso n�o cadastrado"));
			System.out.println();
		}
	}

	private void listarAlunosMatriculadosPorMateria() {
		System.out.println("Digite o c�digo da mat�ria:");
		Materia materia_listar_alunos = controleAcesso.consultarMateria(InputReader.getInputInt());
		if (materia_listar_alunos == null) {
			System.out.println("Mat�ria n�o encontrada!");
			return;
		}
		System.out.println("Alunos matriculados em " + materia_listar_alunos.getNome() + ":");

		List<Aluno> alunos_materia = controleAcesso.listarAlunosMatriculados(materia_listar_alunos);
		for (int i = 0; i < alunos_materia.size(); i++) {
			if (i < alunos_materia.size() - 1) {
				System.out.print(alunos_materia.get(i).getNome() + "/ ");
			} else {
				System.out.println(alunos_materia.get(i).getNome());
				System.out.println();
			}
		}
	}
	
	private Materia getMateriaFromUser() {
		System.out.println("Digite o nome da mat�ria: ");
		String nome_materia = InputReader.getInputString();
		System.out.println("Digite o c�digo do professor da mat�ria: ");
		int prof_materia = InputReader.getInputInt();
		if (controleAcesso.consultarProfessor(prof_materia) == null) {
			System.out.println("Professor n�o encontrado!");
			return null;
		}
		System.out.println("Digite o c�digo do curso da mat�ria: ");
		int curso_materia = InputReader.getInputInt();
		if (controleAcesso.consultarCurso(curso_materia) == null) {
			System.out.println("Curso n�o encontrado!");
			return null;
		}
		return new Materia(nome_materia, prof_materia, curso_materia);
	}

}

package userInterface;

import java.util.Iterator;
import java.util.List;

import models.Aluno;
import models.Curso;
import models.Materia;
import sistemaAcademico.ControleAcesso;

public class MenuAluno implements IMenu {

	ControleAcesso controleAcesso;

	MenuAluno(ControleAcesso controleAcesso) {
		this.controleAcesso = controleAcesso;
	}

	@Override
	public void menuControl() {
		printOptions();

		switch (InputReader.getInputInt()) {
		case 0:
			break;

		case 1:
			cadastraAluno();
			break;

		case 2:
			listaAlunos();
			break;

		case 3:
			excluiAluno();
			break;

		case 4:
			consultarAlunoPelaMatr�cula();
			break;

		case 5:
			matricularAluno();
			break;

		case 6:
			listarMateriasMatriculadasPorAluno();
			break;

		case 7:
			trocarAlunoMateria();
			break;

		case 8:
			excluirCadastrosRepetidos();
			break;

		default:
			System.out.println("Op��o Inv�lida");

		}

	}

	@Override
	public void printOptions() {
		System.out.println("Escolha uma op��o: ");
		System.out.println("0. Voltar");
		System.out.println("1. Cadastrar aluno");
		System.out.println("2. Listar todos os alunos");
		System.out.println("3. Excluir aluno");
		System.out.println("4. Consultar aluno pela matr�cula");
		System.out.println("5. Matricular aluno");
		System.out.println("6. Listar mat�rias matriculadas de aluno");
		System.out.println("7. Trocar aluno de mat�ria (transaction)");
		System.out.println("8. Verificar e excluir cadastro repetido de alunos");

	}

	public void cadastraAluno() {
		Aluno aluno = getAlunoFromUser();
		if (aluno != null) {
			if (controleAcesso.incluirAluno(aluno)) {
				System.out.println("Aluno cadastrado!");
			}
		}
	}

	public void listaAlunos() {
		List<Aluno> alunos = controleAcesso.listarAlunos();
		if (alunos != null) {
			for (int i = 0; i < alunos.size(); i++) {
				System.out.println(alunos.get(i).toString());
				Curso curso_aluno = controleAcesso.consultarCurso(alunos.get(i).getCodigoCurso());
				System.out.println("Curso: " + (curso_aluno != null ? curso_aluno.getNome() : "Curso n�o cadastrado"));
				System.out.println();
			}
		}
	}

	public void excluiAluno() {
		System.out.println("Digite a matr�cula do aluno: ");
		int matricula_excluir = InputReader.getInputInt();

		Aluno aluno_excluir = controleAcesso.consultarAluno(matricula_excluir);
		if (aluno_excluir == null) {
			System.out.println("Aluno n�o encontrado!");
		}
		controleAcesso.excluirAluno(aluno_excluir);
		System.out.println("Aluno exclu�do!");
	}

	public Aluno getAlunoFromUser() {
		System.out.println("Digite o nome do aluno: ");
		String nome_aluno = InputReader.getInputString();

		System.out.println("Digite o c�digo do curso do aluno: ");
		int curso = InputReader.getInputInt();

		if (controleAcesso.consultarCurso(curso) == null) {
			System.out.println("Curso n�o encontrado!");
			return null;
		}
		System.out.println("Digite a matr�cula do aluno: ");
		int matricula = InputReader.getInputInt();
		return new Aluno(nome_aluno, curso, matricula);
	}

	private void consultarAlunoPelaMatr�cula() {
		System.out.println("Digite a matr�cula do aluno ");
		int matricula_consultar = InputReader.getInputInt();

		Aluno aluno_consulta = controleAcesso.consultarAluno(matricula_consultar);

		if (aluno_consulta == null)
			System.out.println("Aluno n�o encontrado!");
		else {
			System.out.println("Nome: " + aluno_consulta.getNome());
			System.out.println("Matr�cula: " + aluno_consulta.getMatricula());
			System.out.println("Curso: " + aluno_consulta.getCodigoCurso());
			System.out.println();
		}
	}

	private void matricularAluno() {
		System.out.println("Digite a matr�cula do aluno:");
		Aluno aluno_matricular = controleAcesso.consultarAluno(InputReader.getInputInt());

		if (aluno_matricular == null) {
			System.out.println("Aluno n�o encontrado!");
			return;
		}

		System.out.println("Digite o c�digo da mat�ria:");
		Materia materia_matricular = controleAcesso.consultarMateria(InputReader.getInputInt());

		if (materia_matricular == null) {
			System.out.println("Mat�ria n�o encontrada!");
			return;
		}

		if (controleAcesso.matricularAluno(aluno_matricular, materia_matricular)) {
			System.out.println("Aluno matriculado!");
		}
	}

	private void listarMateriasMatriculadasPorAluno() {
		System.out.println("Digite a matr�cula do aluno:");
		Aluno aluno_listar_materias = controleAcesso.consultarAluno(InputReader.getInputInt());
		if (aluno_listar_materias == null) {
			System.out.println("Aluno n�o encontrado!");
			return;
		}

		System.out.println("Mat�rias de " + aluno_listar_materias.getNome() + ":");

		List<Materia> materias_aluno = controleAcesso.listarMateriasMatriculadas(aluno_listar_materias);
		for (int i = 0; i < materias_aluno.size(); i++) {
			if (i < materias_aluno.size() - 1) {
				System.out.print(materias_aluno.get(i).getNome() + "/ ");
			} else {
				System.out.println(materias_aluno.get(i).getNome());
				System.out.println();
			}
		}
	}

	private void trocarAlunoMateria() {
		System.out.println("Digite a matr�cula do aluno: ");
		int matricula_aluno14 = InputReader.getInputInt();
		Aluno aluno14 = controleAcesso.consultarAluno(matricula_aluno14);
		if (aluno14 == null) {
			System.out.println("Aluno n�o encontrado!");
			return;
		}

		System.out.println("Digite o c�digo da mat�ria para desmatricular " + aluno14.getNome() + ": ");
		int codigo_materia_desmat = InputReader.getInputInt();
		Materia materia_desmatricular14 = controleAcesso.consultarMateria(codigo_materia_desmat);
		if (materia_desmatricular14 == null) {
			System.out.println("Mat�ria n�o encontrada!");
			return;
		}

		System.out.println("Digite o c�digo da mat�ria para matricular " + aluno14.getNome() + ": ");
		int codigo_materia_mat = InputReader.getInputInt();
		Materia materia_matricular14 = controleAcesso.consultarMateria(codigo_materia_mat);
		if (materia_matricular14 == null) {
			System.out.println("Mat�ria n�o encontrada!");
			return;
		}

		if (controleAcesso.trocarMateriaAluno(aluno14, materia_desmatricular14, materia_matricular14)) {
			System.out.println("Troca de mat�ria realizada");

		} else {
			System.out.println("N�o foi poss�vel realizar troca de mat�ria");
		}
	}

	private void excluirCadastrosRepetidos() {
		List<Aluno> alunos = controleAcesso.contarAlunosRepetidos();
		if (alunos == null) {
			return;
		} else if (alunos.size() == 1) {
			System.out.println("Removendo " + alunos.size() + " aluno com matricula duplicada:");
			System.out.println(alunos.get(0).toString());

			if (controleAcesso.excluirAluno(alunos.get(0))) {
				System.out.println("Aluno exclu�do!");
			} else
				System.out.println("N�o foi poss�vel excluir!");

			System.out.println();

		} else if (alunos.size() > 1) {
			System.out.println("Removendo " + alunos.size() + " alunos com matricula duplicada, s�o eles:");
			for (Iterator<Aluno> iterator = alunos.iterator(); iterator.hasNext();) {
				Aluno aluno_rep = iterator.next();
				System.out.println(aluno_rep.toString());

				if (controleAcesso.excluirAluno(aluno_rep)) {
					System.out.println("Aluno exclu�do!");
				} else
					System.out.println("N�o foi poss�vel excluir!");

				System.out.println();

			}

		} else {
			System.out.println("N�o existem cadastros repetidos!");

		}
	}

}

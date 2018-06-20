package sistemaAcademico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daoClasses.DaoException;
import daoClasses.Aluno.AlunoMongoDao;
import daoClasses.Aluno.AlunoMySqlDao;
import daoClasses.Curso.CursoMongoDao;
import daoClasses.Curso.CursoMySqlDao;
import daoClasses.Materia.MateriaMongoDao;
import daoClasses.Materia.MateriaMySqlDao;
import daoClasses.Professor.ProfessorMongoDao;
import daoClasses.Professor.ProfessorMySqlDao;
import models.Aluno;
import models.Curso;
import models.Materia;
import models.Professor;

@SuppressWarnings("unused")

public class ControleAcesso {

	AlunoMySqlDao daoAluno = new AlunoMySqlDao();
	ProfessorMySqlDao daoProfessor = new ProfessorMySqlDao();
	MateriaMySqlDao daoMateria = new MateriaMySqlDao();
	CursoMySqlDao daoCurso = new CursoMySqlDao();
	
	/*AlunoMongoDao daoAluno = new AlunoMongoDao();
	ProfessorMongoDao daoProfessor = new ProfessorMongoDao();
	MateriaMongoDao daoMateria = new MateriaMongoDao();
	CursoMongoDao daoCurso = new CursoMongoDao();*/

	public boolean incluirAluno(Aluno aluno) {
		try {

			daoAluno.incluirAluno(aluno);
			return true;

		} catch (DaoException e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Aluno> listarAlunos() {
		try {

			return daoAluno.listarAlunos();

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean excluirAluno(Aluno aluno) {
		try {

			daoAluno.excluirAluno(aluno);
			return true;

		} catch (DaoException e) {
			System.out.println(e);
			return false;
		}
	}

	public Aluno consultarAluno(int matricula) {
		try {

			return daoAluno.consultarAluno(matricula);

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean incluirProfessor(Professor professor) {
		try {

			daoProfessor.incluirProfessor(professor);
			return true;

		} catch (DaoException e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Professor> listarProfessores() {
		try {

			return daoProfessor.listarProfessores();

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public Professor consultarProfessor(int codigo) {
		try {
			return daoProfessor.consultarProfessor(codigo);
		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean incluirMateria(Materia materia) {
		try {

			daoMateria.incluirMateria(materia);
			return true;

		} catch (DaoException e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Materia> listarMaterias() {
		try {

			return daoMateria.listarMaterias();

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public Materia consultarMateria(int codigo) {
		try {

			return daoMateria.consultarMateria(codigo);

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean incluirCurso(Curso curso) {
		try {

			daoCurso.incluirCurso(curso);
			return true;

		} catch (DaoException e) {
			System.out.println(e);
			return false;
		}

	}

	public List<Curso> listarCursos() {
		try {

			return daoCurso.listarCursos();

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public Curso consultarCurso(int codigo) {
		try {

			return daoCurso.consultarCurso(codigo);

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean matricularAluno(Aluno aluno, Materia materia) {
		try {

			daoAluno.matricularAluno(aluno, materia);
			return true;

		} catch (DaoException e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Materia> listarMateriasMatriculadas(Aluno aluno) {
		try {

			return daoAluno.listarMateriasMatriculadas(aluno);

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public List<Aluno> listarAlunosMatriculados(Materia materia) {
		try {

			return daoMateria.listarAlunosMatriculados(materia);

		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean trocarMateriaAluno(Aluno aluno, Materia materiaOld, Materia materiaNew) {
		try {

			if (daoAluno.trocarMateriaAluno(aluno, materiaOld, materiaNew) == 1) {
				return true;
			} else
				return false;

		} catch (DaoException e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Aluno> contarAlunosRepetidos() {

		List<Aluno> alunos_rep = new ArrayList<Aluno>();

		List<Aluno> alunos = this.listarAlunos();

		if (alunos != null) {
			for (int i = 0; i < alunos.size(); i++) {
				for (int j = i + 1; j < alunos.size(); j++) {
					if (alunos.get(i).equals(alunos.get(j))) {
						alunos_rep.add(alunos.get(j));
						break;
					}
				}
			}
		}

		return alunos_rep;
	}

}

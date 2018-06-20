package daoClasses.Aluno;

import java.util.List;

import daoClasses.DaoException;
import models.Aluno;
import models.Materia;

public interface IAlunoDao {
	
	public void incluirAluno(Aluno aluno) throws DaoException;

	public List<Aluno> listarAlunos() throws DaoException;
	
	public void excluirAluno(Aluno aluno) throws DaoException;
	
	public List<Materia> listarMateriasMatriculadas(Aluno aluno) throws DaoException;
	
	public void matricularAluno(Aluno aluno, Materia materia) throws DaoException;
	
	public int trocarMateriaAluno(Aluno aluno, Materia matAntiga, Materia matNova) throws DaoException;

	public Aluno consultarAluno(int matricula) throws DaoException;
}

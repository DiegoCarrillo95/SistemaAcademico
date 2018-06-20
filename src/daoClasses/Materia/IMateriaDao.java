package daoClasses.Materia;

import java.util.List;

import daoClasses.DaoException;
import models.Aluno;
import models.Materia;

public interface IMateriaDao {
	public List<Aluno> listarAlunosMatriculados(Materia materia) throws DaoException;

	public void incluirMateria(Materia materia) throws DaoException;

	public List<Materia> listarMaterias() throws DaoException;
	
	public Materia consultarMateria(int codigo) throws DaoException;
}

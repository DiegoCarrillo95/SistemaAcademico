package daoClasses.Curso;

import java.util.List;

import daoClasses.DaoException;
import models.Curso;

public interface ICursoDao {

	public void incluirCurso(Curso curso) throws DaoException;

	public List<Curso> listarCursos() throws DaoException;

	public Curso consultarCurso(int codigo) throws DaoException;
}

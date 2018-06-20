package daoClasses.Professor;

import java.util.List;

import daoClasses.DaoException;
import models.Professor;

public interface IProfessorDao {

	public void incluirProfessor(Professor professor) throws DaoException;

	public List<Professor> listarProfessores() throws DaoException;
	
	public Professor consultarProfessor(int codigo) throws DaoException;
}

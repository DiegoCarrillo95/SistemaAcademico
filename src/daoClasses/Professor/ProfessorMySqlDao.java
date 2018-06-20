package daoClasses.Professor;

import java.util.List;

import daoClasses.DaoException;
import daoClasses.MySqlDao;
import models.Professor;

public class ProfessorMySqlDao extends MySqlDao implements IProfessorDao {

	// Queries:
	private static final String INCLUIR_PROFESSOR = "insert into professores (nome,codigo) values (?,?)";
	private static final String CONSULTAR_PROFESSORES = "select * from professores";
	private static final String CONSULTAR_PROFESSOR = "select * from professores where codigo = ?";

	@Override
	public void incluirProfessor(Professor professor) throws DaoException {
		jdbcTemplate.update(INCLUIR_PROFESSOR,
				new Object[] { professor.getNome(), professor.getCodigo()});
	}

	@Override
	public List<Professor> listarProfessores() throws DaoException {
		return jdbcTemplate.query(CONSULTAR_PROFESSORES, new ProfessorRowMapper());
	}

	@Override
	public Professor consultarProfessor(int codigo) throws DaoException {
		List<Professor> list = jdbcTemplate.query(CONSULTAR_PROFESSOR, new Object[] {codigo}, new ProfessorRowMapper());
		if(list.isEmpty()) {
			return null;
		}
		else return list.get(0);
	}
}

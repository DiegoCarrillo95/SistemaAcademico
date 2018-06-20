package daoClasses.Curso;

import java.util.List;

import org.springframework.dao.DataAccessException;

import daoClasses.DaoException;
import daoClasses.MySqlDao;
import models.Curso;

public class CursoMySqlDao extends MySqlDao implements ICursoDao {

	// Queries:
	private static final String CONSULTAR_CURSO = "select * from cursos where id_curso = ?";
	private static final String LISTAR_CURSOS = "select * from cursos";
	private static final String INCLUIR_CURSO = "insert into cursos (id_curso, nome) values (?,?)";


	@Override
	public Curso consultarCurso(int codigo) throws DaoException {
		try {
			List<Curso> list = jdbcTemplate.query(CONSULTAR_CURSO, new Object[] { codigo }, new CursoRowMapper());
			if (list.isEmpty()) {
				return null;
			} else
				return list.get(0);
		} catch (DataAccessException mysqlE) {
			String err = "erro ao consultar curso no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

	@Override
	public void incluirCurso(Curso curso) throws DaoException {
		try {
			jdbcTemplate.update(INCLUIR_CURSO,
					new Object[] {curso.getId(), curso.getNome()});
		} catch (DataAccessException mysqlE) {
			String err = "erro ao incluir curso no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}

	}

	@Override
	public List<Curso> listarCursos() throws DaoException {
		try {
			return jdbcTemplate.query(LISTAR_CURSOS, new CursoRowMapper());
		} catch (DataAccessException mysqlE) {
			String err = "erro ao listar cursos no servidor MySQL";
			throw new DaoException(err + "\n" + mysqlE.getMessage(), mysqlE);
		}
	}
}

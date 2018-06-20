package daoClasses.Materia;

import java.util.List;

import org.springframework.dao.DataAccessException;

import daoClasses.DaoException;
import daoClasses.MySqlDao;
import daoClasses.Aluno.AlunoRowMapper;
import models.Aluno;
import models.Materia;

public class MateriaMySqlDao extends MySqlDao implements IMateriaDao {

	// Queries:
	private static final String CONSULTAR_ALUNOS_EM_MATERIA = "select alunos.id_aluno, alunos.nome, alunos.codigo_curso, alunos.matricula "
			+ "from alunos inner join matriculas on alunos.matricula = matriculas.matricula_aluno where matriculas.codigo_materia = ? order by alunos.nome";
	private static final String INCLUIR_MATERIA = "insert into materias (nome, codigo_professor, codigo_curso) values (?,?,?)";
	private static final String CONSULTAR_MATERIAS = "select * from materias";
	private static final String CONSULTAR_MATERIA = "select * from materias where id_materia = ?";

	@Override
	public List<Aluno> listarAlunosMatriculados(Materia materia) throws DaoException {
		try {
			return jdbcTemplate.query(CONSULTAR_ALUNOS_EM_MATERIA, new Object[] { materia.getCodigoMateria() },
					new AlunoRowMapper());
		} catch (DataAccessException mysqlE) {
			String err = "erro ao listar alunos matriculados em matéria no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

	@Override
	public void incluirMateria(Materia materia) throws DaoException {
		try {
			jdbcTemplate.update(INCLUIR_MATERIA,
					new Object[] { materia.getNome(), materia.getCodigoProfessor(), materia.getCodigoCurso() });
		} catch (DataAccessException mysqlE) {
			String err = "erro ao incluir matéria no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

	@Override
	public List<Materia> listarMaterias() throws DaoException {
		try {
			return jdbcTemplate.query(CONSULTAR_MATERIAS, new MateriaRowMapper());
		} catch (DataAccessException mysqlE) {
			String err = "erro ao listar matérias no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

	@Override
	public Materia consultarMateria(int codigo) throws DaoException {
		try {
			List<Materia> list = jdbcTemplate.query(CONSULTAR_MATERIA, new Object[] { codigo }, new MateriaRowMapper());
			if (list.isEmpty()) {
				return null;
			} else
				return list.get(0);
		} catch (DataAccessException mysqlE) {
			String err = "erro ao consultar matéria no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}
}

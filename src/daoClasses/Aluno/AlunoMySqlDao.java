package daoClasses.Aluno;

import java.util.List;

import org.springframework.dao.DataAccessException;

import daoClasses.DaoException;
import daoClasses.MySqlDao;
import daoClasses.Materia.MateriaRowMapper;
import models.Aluno;
import models.Materia;

public class AlunoMySqlDao extends MySqlDao implements IAlunoDao {

	private static final String INCLUIR_ALUNO = "insert into alunos (nome,codigo_curso,matricula) values (?,?,?)";
	private static final String CONSULTAR_ALUNOS = "select * from alunos order by nome";
	private static final String EXCLUIR_ALUNO = "delete from alunos where matricula = ?";
	private static final String CONSULTAR_MATERIAS_DE_ALUNO = "select matriculas.codigo_materia, materias.nome, professores.codigo, materias.codigo_curso"
			+ "  from matriculas inner join materias on matriculas.codigo_materia = materias.id_materia inner join professores on materias.codigo_professor = professores.codigo"
			+ " inner join cursos on materias.codigo_curso = id_curso where matriculas.matricula_aluno = ? order by materias.nome";
	private static final String MATRICULAR_ALUNO = "insert into matriculas (matricula_aluno,codigo_materia) values (?,?)";
	// private static final String DESMATRICULAR_ALUNO = "delete from matriculas
	// where matricula_aluno = ? and codigo_materia = ?";
	// private static final String VERIFICAR_MATRICULA = "select * from matriculas
	// where matricula_aluno = ? and codigo_materia = ?";
	private static final String CONSULTAR_ALUNO = "select * from alunos where matricula = ?";

	@Override
	public void incluirAluno(Aluno aluno) throws DaoException {
		try {
			jdbcTemplate.update(INCLUIR_ALUNO,
					new Object[] { aluno.getNome(), aluno.getCodigoCurso(), aluno.getMatricula() });
		} catch (DataAccessException mysqlE) {
			String err = "erro ao incluir aluno no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

	@Override
	public List<Aluno> listarAlunos() throws DaoException {
		try {
			return jdbcTemplate.query(CONSULTAR_ALUNOS, new AlunoRowMapper());
		} catch (DataAccessException mysqlE) {
			String err = "erro ao listar alunos no servidor MySQL";
			throw new DaoException(err + "\n" + mysqlE.getMessage(), mysqlE);
		}
	}

	@Override
	public void excluirAluno(Aluno aluno) throws DaoException {
		try {
			jdbcTemplate.update(EXCLUIR_ALUNO, new Object[] { aluno.getMatricula() });
		} catch (DataAccessException mysqlE) {
			String err = "erro ao excluir aluno no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

	@Override
	public List<Materia> listarMateriasMatriculadas(Aluno aluno) throws DaoException {
		try {
			return jdbcTemplate.query(CONSULTAR_MATERIAS_DE_ALUNO, new MateriaRowMapper());
		} catch (DataAccessException mysqlE) {
			String err = "erro ao listar matérias matriculadas de aluno no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

	@Override
	public void matricularAluno(Aluno aluno, Materia materia) throws DaoException {
		try {
			jdbcTemplate.update(MATRICULAR_ALUNO, new Object[] { aluno.getMatricula(), materia.getCodigoMateria() });
		} catch (DataAccessException mysqlE) {
			String err = "erro ao matricular aluno no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

	@Override
	public int trocarMateriaAluno(Aluno aluno, Materia matAntiga, Materia matNova) throws DaoException {
		throw new DaoException("Ainda não implementado com JdbcTemplate");
		/*
		 * int ret = 0; Connection conn = null; PreparedStatement ps1 = null;
		 * PreparedStatement ps2 = null; PreparedStatement ps3 = null;
		 * 
		 * try {
		 * 
		 * boolean commit = true;
		 * 
		 * conn = DriverManager.getConnection(url, user, password); ps1 =
		 * conn.prepareStatement(DESMATRICULAR_ALUNO); ps2 =
		 * conn.prepareStatement(MATRICULAR_ALUNO); ps3 =
		 * conn.prepareStatement(VERIFICAR_MATRICULA);
		 * 
		 * // Inicia da transaction conn.setAutoCommit(false);
		 * 
		 * // Retira matrícula antiga da lista de matrículas ps1.setInt(1,
		 * aluno.getMatricula()); ps1.setInt(2, matAntiga.getCodigoMateria());
		 * ps1.executeUpdate(); if (ps1.getUpdateCount() == 0) { commit = false; ret =
		 * 0; }
		 * 
		 * // Verifica se aluno já não está matriculado na nova matéria ps3.setInt(1,
		 * aluno.getMatricula()); ps3.setInt(2, matNova.getCodigoMateria()); ResultSet
		 * rs = ps3.executeQuery(); if (rs.next()) { commit = false; ret = 0; }
		 * 
		 * // Matricula aluno na nova matéria ps2.setInt(1, aluno.getMatricula());
		 * ps2.setInt(2, matNova.getCodigoMateria()); ps2.executeUpdate(); if
		 * (ps2.getUpdateCount() == 0) { commit = false; ret = 0; }
		 * 
		 * // Finaliza transaction if (commit) { conn.commit(); ret = 1; } else
		 * conn.rollback();
		 * 
		 * } catch (SQLException e) { System.out.println(e.getMessage()); if (conn !=
		 * null) { try { conn.rollback(); } catch (SQLException excep) {
		 * System.out.println(excep.getMessage()); } } String err =
		 * "erro ao trocar matéria de aluno no servidor MySQL, executando roll back na transação"
		 * ; throw new DaoException(err, e);
		 * 
		 * } finally { if (ps1 != null) { try { ps1.close(); } catch (SQLException e) {
		 * throw new DaoException(e); } } if (ps2 != null) { try { ps2.close(); } catch
		 * (SQLException e) { throw new DaoException(e); } } if (ps3 != null) { try {
		 * ps3.close(); } catch (SQLException e) { throw new DaoException(e); } } if
		 * (conn != null) { try { conn.setAutoCommit(true); conn.close(); } catch
		 * (SQLException e) { throw new DaoException(e); } } }
		 * 
		 * return ret;
		 */
	}

	@Override
	public Aluno consultarAluno(int matricula) throws DaoException {
		try {
			List<Aluno> list = jdbcTemplate.query(CONSULTAR_ALUNO, new Object[] { matricula }, new AlunoRowMapper());
			if (list.isEmpty()) {
				return null;
			} else
				return list.get(0);
		} catch (DataAccessException mysqlE) {
			String err = "erro ao consultar aluno no servidor MySQL";
			throw new DaoException(err, mysqlE);
		}
	}

}

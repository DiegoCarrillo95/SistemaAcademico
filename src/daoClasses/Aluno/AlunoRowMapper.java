package daoClasses.Aluno;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.Aluno;

public class AlunoRowMapper implements RowMapper<Aluno>{

	@Override
	public Aluno mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Aluno aluno = new Aluno();
        aluno.setIdAluno(resultSet.getInt(1));
        aluno.setNome(resultSet.getString(2));
        aluno.setCodigoCurso(resultSet.getInt(3));
        aluno.setMatricula(resultSet.getInt(4));
        return aluno;
	}
	

}

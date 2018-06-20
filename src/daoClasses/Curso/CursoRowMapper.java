package daoClasses.Curso;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.Curso;

public class CursoRowMapper implements RowMapper<Curso>{

	@Override
	public Curso mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Curso curso = new Curso();
        curso.setId(resultSet.getInt(1));
        curso.setNome(resultSet.getString(2));
        return curso;
	}
	

}

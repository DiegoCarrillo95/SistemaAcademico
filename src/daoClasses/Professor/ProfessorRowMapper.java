package daoClasses.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.Professor;

public class ProfessorRowMapper implements RowMapper<Professor>{

	@Override
	public Professor mapRow(ResultSet resultSet, int arg1) throws SQLException {
		return new Professor(resultSet.getString(2),resultSet.getInt(3));
	}
	

}

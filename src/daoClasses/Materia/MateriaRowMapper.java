package daoClasses.Materia;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import models.Materia;

public class MateriaRowMapper implements RowMapper<Materia>{

	@Override
	public Materia mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Materia materia = new Materia();
		materia.setCodigoMateria(resultSet.getInt(1));
		materia.setNome(resultSet.getString(2));
		materia.setCodigoProfessor(resultSet.getInt(3));
		materia.setCodigoCurso(resultSet.getInt(4));
        return materia;
	}
	

}

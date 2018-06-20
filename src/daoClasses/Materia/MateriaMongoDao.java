package daoClasses.Materia;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import daoClasses.DaoException;
import daoClasses.MongoDao;
import models.Aluno;
import models.Materia;

public class MateriaMongoDao extends MongoDao implements IMateriaDao {

	@Override
	public List<Aluno> listarAlunosMatriculados(Materia materia) throws DaoException {
		List<Aluno> list = new ArrayList<Aluno>();

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("alunos");
			BasicDBObject query = new BasicDBObject();
			query.put("matriculas", materia.getCodigoMateria());
			DBCursor cursor = collection.find(query);

			while (cursor.hasNext()) {
				DBObject document = cursor.next();
				String nome = (String) document.get("nome");
				int codigo_curso = (int) document.get("codigo_curso");
				int matricula = (int) document.get("matricula");

				Aluno aluno = new Aluno();
				aluno.setNome(nome);
				aluno.setCodigoCurso(codigo_curso);
				aluno.setMatricula(matricula);

				list.add(aluno);
			}

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao listar alunos matriculados em matéria no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}

		return list;
	}

	@Override
	public void incluirMateria(Materia materia) throws DaoException {
		try {
			MongoClient mongoClient = new MongoClient(host, port);
			DB db = mongoClient.getDB(database_name);
			DBCollection col = db.getCollection("materias");

			BasicDBObject document = new BasicDBObject();
			document.put("nome", materia.getNome());
			document.put("codigo_professor", materia.getCodigoProfessor());
			document.put("codigo_curso", materia.getCodigoCurso());
			document.put("codigo_materia", getMaxCodigoMateria() + 1);

			col.insert(document);

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao incluir matéria no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}

	}

	@Override
	public List<Materia> listarMaterias() throws DaoException {
		List<Materia> list = new ArrayList<Materia>();

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("materias");
			DBCursor cursor = collection.find();

			while (cursor.hasNext()) {
				DBObject document = cursor.next();
				int codigo_materia = (int) document.get("codigo_materia");
				String nome = (String) document.get("nome");
				int codigo_professor = (int) document.get("codigo_professor");
				int codigo_curso = (int) document.get("codigo_curso");

				Materia materia = new Materia(nome, codigo_professor, codigo_curso);
				materia.setCodigoMateria(codigo_materia);
				list.add(materia);
			}

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao listar matérias no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}

		return list;
	}

	@Override
	public Materia consultarMateria(int codigo) throws DaoException {
		Materia materia = null;

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("materias");
			BasicDBObject query = new BasicDBObject();
			query.put("codigo_materia", codigo);
			DBCursor cursor = collection.find(query);

			if (cursor.hasNext()) {
				DBObject document = cursor.next();
				int codigo_curso = (int) document.get("codigo_curso");
				int codigo_professor = (int) document.get("codigo_professor");
				String nome = (String) document.get("nome");

				materia = new Materia();
				materia.setNome(nome);
				materia.setCodigoCurso(codigo_curso);
				materia.setCodigoProfessor(codigo_professor);
				materia.setCodigoMateria(codigo);
			}

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao consultar matéria no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}

		return materia;
	}

	// Retorna o maior código de matéria existente no DB, a fim de gerar um código
	// novo unico para uma próxima entrada
	private int getMaxCodigoMateria() throws DaoException {

		int max_id = 0;

		try {
			MongoClient mongoClient = new MongoClient(host, port);
			DB db = mongoClient.getDB(database_name);
			DBCollection col = db.getCollection("materias");

			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("codigo_materia", -1);
			DBCursor cursor = col.find().sort(orderBy).limit(1);
			if (cursor.hasNext()) {
				DBObject document = cursor.next();
				max_id = (int) document.get("codigo_materia");
			}

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao gerar um código de matéria no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}

		return max_id;
	}

}

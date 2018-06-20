package daoClasses.Aluno;

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
import daoClasses.Materia.MateriaMongoDao;
import models.Aluno;
import models.Materia;

public class AlunoMongoDao extends MongoDao implements IAlunoDao {

	@Override
	public void incluirAluno(Aluno aluno) throws DaoException {
		try {
			MongoClient mongoClient = new MongoClient(host, port);
			DB db = mongoClient.getDB(database_name);
			DBCollection col = db.getCollection("alunos");

			BasicDBObject document = new BasicDBObject();
			document.put("nome", aluno.getNome());
			document.put("codigo_curso", aluno.getCodigoCurso());
			document.put("matricula", aluno.getMatricula());

			col.insert(document);

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao incluir aluno no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}
	}

	@Override
	public List<Aluno> listarAlunos() throws DaoException {
		List<Aluno> list = new ArrayList<Aluno>();

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("alunos");
			DBCursor cursor = collection.find().sort(new BasicDBObject("i", +1));

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
			String err = "erro ao listar alunos no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}

		return list;
	}

	@Override
	public void excluirAluno(Aluno aluno) throws DaoException {
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("alunos");
			BasicDBObject document = new BasicDBObject();
			document.put("matricula", aluno.getMatricula());
			document.put("nome", aluno.getNome());

			collection.remove(document);

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao incluir professor no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}
	}

	@Override
	public List<Materia> listarMateriasMatriculadas(Aluno aluno) throws DaoException {

		List<Materia> list = new ArrayList<Materia>();

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("alunos");
			BasicDBObject query = new BasicDBObject();
			query.put("matricula", aluno.getMatricula());
			DBCursor cursor = collection.find(query);

			if (cursor.hasNext()) {
				DBObject document = cursor.next();
				List<?> matriculas = (List<?>) document.get("matriculas");
				if (matriculas != null) {
					for (int i = 0; i < matriculas.size(); i++){
						MateriaMongoDao daoMateria = new MateriaMongoDao();
						Materia materia = daoMateria.consultarMateria((Integer) matriculas.get(i));
						list.add(materia);
					}
				}
			}

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao listar matérias matriculadas de aluno no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}

		return list;
	}

	@Override
	public void matricularAluno(Aluno aluno, Materia materia) throws DaoException {
		try {
			MongoClient mongoClient = new MongoClient(host, port);
			DB db = mongoClient.getDB(database_name);
			DBCollection col = db.getCollection("alunos");

			BasicDBObject document1 = new BasicDBObject();
			document1.put("matricula", aluno.getMatricula());

			BasicDBObject document2 = new BasicDBObject();
			BasicDBObject nestedDocument = new BasicDBObject();
			nestedDocument.put("matriculas", materia.getCodigoMateria());
			document2.put("$push", nestedDocument);

			col.update(document1, document2);

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao matricular aluno no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}
	}

	@Override
	public int trocarMateriaAluno(Aluno aluno, Materia matAntiga, Materia matNova) throws DaoException {
		String err = "a troca de matéria de aluno não é implementada em MongoDB";
		throw new DaoException(err);
	}

	@Override
	public Aluno consultarAluno(int matricula) throws DaoException {
		Aluno aluno = null;

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("alunos");
			BasicDBObject query = new BasicDBObject();
			query.put("matricula", matricula);
			DBCursor cursor = collection.find(query);

			if (cursor.hasNext()) {
				DBObject document = cursor.next();
				int codigo_curso = (int) document.get("codigo_curso");
				String nome = (String) document.get("nome");

				aluno = new Aluno(nome, codigo_curso, matricula);
			}

			mongoClient.close();

		} catch (Exception mongoE) {
			String err = "erro ao consultar aluno no servidor MongoDB";
			throw new DaoException(err, mongoE);
		}

		return aluno;
	}

}

package daoClasses.Curso;

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
import models.Curso;

public class CursoMongoDao extends MongoDao implements ICursoDao{
	
	@Override
	public void incluirCurso(Curso curso) throws DaoException{
		try {
			MongoClient mongoClient = new MongoClient(host, port);
			DB db = mongoClient.getDB(database_name);
			DBCollection col = db.getCollection("cursos");

			BasicDBObject document = new BasicDBObject();
			document.put("nome", curso.getNome());
			document.put("codigo_curso", getMaxCodigoCurso() + 1);

			col.insert(document);

			mongoClient.close();

        } catch (Exception mongoE) {
            String err = "erro ao incluir curso no servidor MongoDB";
            throw new DaoException(err, mongoE);
        }
	}

	@Override
	public List<Curso> listarCursos() throws DaoException{

		List<Curso> list = new ArrayList<Curso>();

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("cursos");
			DBCursor cursor = collection.find();

			while (cursor.hasNext()) {
				DBObject document = cursor.next();
				int codigo_curso = (int) document.get("codigo_curso");
				String nome = (String) document.get("nome");

				Curso curso = new Curso();
				curso.setId(codigo_curso);
				curso.setNome(nome);
				list.add(curso);
			}

			mongoClient.close();
        } catch (Exception mongoE) {
            String err = "erro ao listar cursos no servidor MongoDB";
            throw new DaoException(err, mongoE);
        }

		return list;
	}

	@Override
	public Curso consultarCurso(int codigo) throws DaoException{
		
		Curso curso = null;

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("cursos");
			BasicDBObject query = new BasicDBObject();
			query.put("codigo_curso", codigo);
			DBCursor cursor = collection.find(query);

			if (cursor.hasNext()) {
				DBObject document = cursor.next();
				int codigo_curso = (int) document.get("codigo_curso");
				String nome = (String) document.get("nome");

				curso = new Curso();
				curso.setNome(nome);
				curso.setId(codigo_curso);
			}

			mongoClient.close();

        } catch (Exception mongoE) {
            String err = "erro ao consultar curso no servidor MongoDB";
            throw new DaoException(err, mongoE);
        }

		return curso;
	}

	private int getMaxCodigoCurso() throws DaoException{

		int max_id = 0;

		try {
			MongoClient mongoClient = new MongoClient(host, port);
			DB db = mongoClient.getDB(database_name);
			DBCollection col = db.getCollection("cursos");

			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("codigo_curso", -1);
			DBCursor cursor = col.find().sort(orderBy).limit(1);
			if (cursor.hasNext()) {
				DBObject document = cursor.next();
				max_id = (int) document.get("codigo_curso");
			}

			mongoClient.close();

        } catch (Exception mongoE) {
            String err = "erro ao gerar um código de curso no servidor MongoDB";
            throw new DaoException(err, mongoE);
        }

		return max_id;
	}

}

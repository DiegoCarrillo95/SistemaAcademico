package daoClasses.Professor;

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
import models.Professor;

public class ProfessorMongoDao extends MongoDao implements IProfessorDao{

	@Override
	public void incluirProfessor(Professor professor) throws DaoException {
		try {
			MongoClient mongoClient = new MongoClient(host, port);
			DB db = mongoClient.getDB(database_name);
			DBCollection col = db.getCollection("professores");

			BasicDBObject document = new BasicDBObject();
			document.put("nome", professor.getNome());
			document.put("codigo", professor.getCodigo());

			col.insert(document);

			mongoClient.close();

        } catch (Exception mongoE) {
            String err = "erro ao incluir professor no servidor MongoDB";
            throw new DaoException(err, mongoE);
        }
	}

	@Override
	public List<Professor> listarProfessores() throws DaoException {
		List<Professor> list = new ArrayList<Professor>();

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("professores");
			DBCursor cursor = collection.find();

			while (cursor.hasNext()) {
				DBObject document = cursor.next();
				String nome = (String) document.get("nome");
				int codigo = (int) document.get("codigo");

				Professor prof = new Professor(nome, codigo);
				list.add(prof);
			}

			mongoClient.close();

        } catch (Exception mongoE) {
            String err = "erro ao listar professores no servidor MongoDB";
            throw new DaoException(err, mongoE);
        }

		return list;
	}

	@Override
	public Professor consultarProfessor(int codigo) throws DaoException {
		Professor professor = null;

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB(database_name);
			DBCollection collection = db.getCollection("professores");
			BasicDBObject query = new BasicDBObject();
			query.put("codigo", codigo);
			DBCursor cursor = collection.find(query);

			if (cursor.hasNext()) {
				DBObject document = cursor.next();
				int codigo_prof = (int) document.get("codigo");
				String nome = (String) document.get("nome");

				professor = new Professor(nome, codigo_prof);
			}

			mongoClient.close();

        } catch (Exception mongoE) {
            String err = "erro ao consultar professor no servidor MongoDB";
            throw new DaoException(err, mongoE);
        }

		return professor;
	}

}

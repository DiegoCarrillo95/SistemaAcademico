package junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import daoClasses.Aluno.AlunoMySqlDao;

class AlunoMySqlDaoTest {

	AlunoMySqlDao alunoDao = new AlunoMySqlDao(); //Seria necessário transaction? 
	
	@Test
	void test() {
		assertTrue(true);
	}

}

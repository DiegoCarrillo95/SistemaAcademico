package models;

public class Materia {
	
	private String nome;
	private int codigoMateria;
	private int codigoProfessor;
	//Professor professor; ? 
	private int codigoCurso;
	//List<Curso> cursos; ?? 
	//List<Aluno> alunos; ?? 
	
	public Materia() {
		
	}
	public Materia(String nome, int cod_prof, int cod_curso) {
		this.nome = nome;
		this.codigoProfessor = cod_prof;
		this.codigoCurso = cod_curso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getCodigoMateria() {
		return codigoMateria;
	}
	public void setCodigoMateria(int codigoMateria) {
		this.codigoMateria = codigoMateria;
	}
	public int getCodigoProfessor() {
		return codigoProfessor;
	}
	public void setCodigoProfessor(int codigoProfessor) {
		this.codigoProfessor = codigoProfessor;
	}
	public int getCodigoCurso() {
		return codigoCurso;
	}
	public void setCodigoCurso(int codigoCurso) {
		this.codigoCurso = codigoCurso;
	}
	
}

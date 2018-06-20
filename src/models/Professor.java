package models;

public class Professor extends Pessoa{
	
	private int codigo;
	// List<Curso> cursos;
	// Materias<Materia> materias; 
	
	public Professor(String nome, int codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}
	
	@Override
	public String toString() {
		return "Nome: " + this.nome + "\nCódigo: " + this.codigo;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}

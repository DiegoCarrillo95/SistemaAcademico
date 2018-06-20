package models;

public class Aluno extends Pessoa{

		private int idAluno;
		private int codigoCurso;
		//Curso curso; ??
		private int matricula;
		// List<Materia> materias;
		
		public Aluno() {}

		public Aluno(String nome, int codigoCurso, int matricula){
			this.nome = nome;
			this.codigoCurso = codigoCurso;
			this.matricula = matricula;
		}
		
		public int getIdAluno() {
			return idAluno;
		}
		public void setIdAluno(int idAluno) {
			this.idAluno = idAluno;
		}
		public int getCodigoCurso() {
			return codigoCurso;
		}
		public void setCodigoCurso(int codigoCurso) {
			this.codigoCurso = codigoCurso;
		}
		public int getMatricula() {
			return matricula;
		}
		public void setMatricula(int matricula) {
			this.matricula = matricula;
		} 
		
		@Override
		public String toString() {
			String string = ("Nome: " + this.nome + "\nMatrícula: " + this.matricula);
			return string;	
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Aluno other = (Aluno) obj;
			if (matricula != other.matricula) {
				return false;
			}
			return true;
		}
		
		@Override
		public int hashCode() {
			return matricula;
		}
		
}

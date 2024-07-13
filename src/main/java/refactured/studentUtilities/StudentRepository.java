package refactured.studentUtilities;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.student.Student;

public class StudentRepository {

	private EntityManager em;
	private EntityManagerFactory emf;

	public StudentRepository() {
		this.emf = Persistence.createEntityManagerFactory("Student_PU");
		this.em = this.emf.createEntityManager();
	}

	public Student addStudent(Student std) {
		em.getTransaction().begin();
		em.persist(std);
		em.getTransaction().commit();

		return std;
	}

	public Student findStudent(long id) {
		return em.find(Student.class, id);
	}
	
	public Student findbyId(long id) {
		Query query = em.createNamedQuery("find student by id");
		query.setParameter("id", id);

		return (Student)query.getSingleResult();
	}

	public Student updateStudent(Student std) {
		Student stdUpdate = findStudent(std.getId());
		em.getTransaction().begin();
		stdUpdate.setFname(std.getFname());
		stdUpdate.setLname(std.getLname());
		em.getTransaction().commit();

		return stdUpdate;
	}

	public void deleteStudent(Student std) {
		Student stdUpdate = findStudent(std.getId());
		em.getTransaction().begin();
		em.remove(stdUpdate);
		em.getTransaction().commit();
	}
	
	public List<String> findfirstnames(){
		Query query = em.createQuery("SELECT s.fname FROM Student s");
		return query.getResultList();
	}
	
	public List<String> findlasttnames(){
		Query query = em.createQuery("SELECT s.lname FROM Student s");
		
		return query.getResultList();
	}

	public void close() {
		this.em.close();
		this.emf.close();
	}

}

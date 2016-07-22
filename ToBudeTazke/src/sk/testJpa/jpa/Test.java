package sk.testJpa.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Test {

	public static void main(String[] args) throws Exception {
		Student student = new Student();
		student.setMeno("Ferko");
		student.setPriezvisko("Hrasko");
		student.setVek(18);
		
		JpaHelper.beginTransaction();
		EntityManager em = JpaHelper.getEntityManager();
		em.persist(student);
		JpaHelper.commitTransaction();
		
		
//		Query query = em.createQuery("Select s from Student s where s.meno = :meno");
//		query.setParameter("meno", "Ferko");
//		System.out.println(query.getResultList());
//		
//		JpaHelper.beginTransaction();
//		student = em.find(Student.class, 6);
//		student.setVek(20);
//		System.out.println(student);
//		
//		em.remove(student);
//		
//		JpaHelper.commitTransaction();
//		JpaHelper.closeAll();
	}

}

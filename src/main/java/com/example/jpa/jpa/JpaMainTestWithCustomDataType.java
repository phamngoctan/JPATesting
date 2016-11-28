package com.example.jpa.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.example.jpa.domain.Employee;
import com.example.jpa.domain.Department;

public class JpaMainTestWithCustomDataType {

	private EntityManager manager;

	public JpaMainTestWithCustomDataType(EntityManager manager) {
		this.manager = manager;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("testing-h2");
		EntityManager manager = factory.createEntityManager();
		JpaMainTestWithCustomDataType test = new JpaMainTestWithCustomDataType(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			test.createEmployees();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();

		test.listEmployees();

		System.out.println(".. done");
	}

	private void createEmployees() {
		int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
		if (numOfEmployees == 0) {
			Department department = new Department("java");
			manager.persist(department);

			manager.persist(new Employee("Jakab Gipsz",department, Boolean.TRUE));
			manager.persist(new Employee("Captain Nemo",department, 4l));
			manager.persist(new Employee("Daniel", department, "Hello World!"));
		}
	}

	private void listEmployees() {
		List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
		System.out.println("num of employess:" + resultList.size());
		for (Employee next : resultList) {
			System.out.println("next employee: " + next);
		}
	}

}

package com.example.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.example.jpa.type.ObjectDataType;

@Entity
@TypeDef(name="ObjectDataType", typeClass=ObjectDataType.class)
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="dept_id", referencedColumnName="id")
	private Department department;
	
	@Column(name="data")
	@Type(type="ObjectDataType")
	Object objectData;

	public Employee() {}

	public Employee(String name, Department department, Object data) {
		this.name = name;
		this.department = department;
		this.objectData = data;
	}
	

	public Employee(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Object getObjectData() {
		return objectData;
	}

	public void setObjectData(Object objectData) {
		this.objectData = objectData;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department="
				+ department.getName() + "]";
	}

}

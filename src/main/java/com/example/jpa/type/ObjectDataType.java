package com.example.jpa.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class ObjectDataType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object obj) throws HibernateException {
		return obj;
	}

	@Override
	public Serializable disassemble(Object obj) throws HibernateException {
		return (Serializable) obj;
	}

	@Override
	public boolean equals(Object obj1, Object obj2) throws HibernateException {
		return  obj1 != null ? obj1.equals(obj2) : (obj2 != null ? obj2.equals(obj1) : true);
	}

	@Override
	public int hashCode(Object obj) throws HibernateException {
		assert(obj != null);
		return obj.hashCode();
	}

	/**
	 * our return data is immutable
	 */
	@Override
	public boolean isMutable() {
		return false;
	}

	/**
	 * we will parse our value when hibernate get data from database
	 */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		if(names == null || names.length == 0) {
			return null;
		}
		String value = names[0];
		if(value == null || "".equals(value.trim())) {
			return null;
		}
		
		if("true".equals(value) || "TRUE".equals(value)) {
			return Boolean.TRUE;
		} else if("false".equals(value) || "FALSE".equals(value)) {
			return Boolean.FALSE;
		} else if(Pattern.compile("-?[1-9][0-9]+").matcher(value).matches()) {
			try {
				Long __value = Long.parseLong(value);
				return __value;
			} catch (Exception e) {
				return value;
			}
		} else if(Pattern.compile("-?[1-9][0-9]+.[0-9]+").matcher(value).matches()) {
			try {
				Double __value = Double.parseDouble(value);
				return __value;
			} catch (Exception e) {
				return value;
			}
		} else {
			return value;
		}
	}

	/**
	 * check data safe type to make statement, it will help us avoid some exeption when entity working with hibernate
	 */
	@Override
	public void nullSafeSet(PreparedStatement stmt, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if(value != null) {
			if(value instanceof Integer || value instanceof Long) {
				Long __value = (Long) value;
				stmt.setLong(index, __value);
			} else if(value instanceof Float || value instanceof Double) {
				Double __value = (Double) value;
				stmt.setDouble(index, __value);
			} else if(value instanceof Boolean) {
				Boolean __value = (Boolean) value;
				stmt.setBoolean(index, __value);
			} else {
				stmt.setString(index, value.toString());
			}
		}
	}

	/**
	 * return object when insert/update action call, return original means our return stored object is immutable
	 */
	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	/**
	 * data type to return when entity is loaded
	 */
	@Override
	public Class<Object> returnedClass() {
		return Object.class;
	}

	/**
	 * data type to store in database
	 */
	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR};
	}

}

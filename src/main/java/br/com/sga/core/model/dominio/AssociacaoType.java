package br.com.sga.core.model.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

import br.com.sga.core.constantes.Constantes;

public class AssociacaoType implements UserType, ParameterizedType {

	private Class<?> enumClass;
    private Method recreateEnumMthd;
    private Method recreateStringMthd;
	private String metodoGetEnum = "get";
	private String metodoGetValue = "getValue";
	
	@Override
	public void setParameterValues(Properties parameters) {
		if (parameters != null) {
			if (StringUtils.isNotEmpty(parameters.getProperty("metodoGetEnum"))) {
				metodoGetEnum = parameters.getProperty("metodoGetEnum");
			}
			
			if (StringUtils.isNotEmpty(parameters.getProperty("metodoGetValue"))) {
				metodoGetValue = parameters.getProperty("metodoGetValue");
			}
			
			String className = parameters.getProperty("enumClassName");
			Class<?> returnType = null;

			try {
				enumClass = Class.forName(Constantes.ENUM_PACKAGE.concat(className));
				recreateStringMthd = enumClass.getMethod(metodoGetValue, new Class[] {});
				returnType = recreateStringMthd.getReturnType();
				recreateEnumMthd = enumClass.getMethod(metodoGetEnum, new Class[] { returnType });
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}			
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.CHAR };
	}

	@Override
	public Class<?> returnedClass() {
		return enumClass;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return ObjectUtils.nullSafeEquals(x, y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		String value = rs.getString(names[0]);
		Object returnVal = null;

		if (value == null) {
			return null;
		} else {
			try {
				returnVal = recreateEnumMthd.invoke(enumClass, new Object[] { value });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return returnVal;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		
		String prepStmtVal = null;
		
		if (value == null) {
			st.setObject(index, null);
		} else {
			try {
				prepStmtVal = (String) recreateStringMthd.invoke(value, new Object[] {});
				st.setString(index, prepStmtVal);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) {
			return null;
		} else {
			return (IAppEnum) value;
		}
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		Object deepCopy = deepCopy(value);

		if (deepCopy instanceof Serializable) {
			return (Serializable) deepCopy;
		}

		return null;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return deepCopy(original);
	}
}


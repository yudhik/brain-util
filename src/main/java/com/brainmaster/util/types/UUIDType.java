package com.brainmaster.util.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.ImmutableType;

import com.brainmaster.util.helper.uuid.UUIDHelper;

public class UUIDType extends ImmutableType implements DiscriminatorType {
	private static final long serialVersionUID = -3823179974122420715L;

	public Object get(ResultSet rs, String name) throws SQLException {
		return UUIDHelper.stringToUUID(rs.getString(name));
	}

	public Class<?> getReturnedClass() {
		return UUID.class;
	}

	public void set(PreparedStatement st, Object value, int index) throws SQLException {
		st.setString(index, UUIDHelper.uuidToString((UUID) value));
	}

	public int sqlType() {
		return Types.VARCHAR;
	}

	public String getName() {
		return "UUID";
	}

	public String objectToSQLString(Object value, Dialect dialect) throws Exception {
		return '\'' + UUIDHelper.uuidToString((UUID) value) + '\'';
	}

	public Object stringToObject(String xml) throws Exception {
		return UUIDHelper.stringToUUID(xml);
	}

	public Object fromStringValue(String xml) {
		return UUIDHelper.stringToUUID(xml);
	}

	public String toString(Object value) {
		return UUIDHelper.uuidToString((UUID) value);
	}

}
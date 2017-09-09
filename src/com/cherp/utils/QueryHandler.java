package com.cherp.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cherp.dbconnection.DBHandler;

public class QueryHandler {

	private DBHandler dbhandler;
	private static Connection connection;

	private QueryHandler() {
		dbhandler = DBHandler.getInstance();
		connection = dbhandler.getConnection();
	}

	public static PreparedStatement getPreparedStatement(String query) {
		System.out.println("get PreparedStatement");
		PreparedStatement stmt = null;
		try {
			new QueryHandler();
			stmt = connection.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}

	public static void setParameters(PreparedStatement ps, Object[] parameters) {
		
		System.out.println("Set Parameters");

		if (ps == null || parameters.length == 0) {
			return;
		}

		ParameterMetaData setMetaData = null;

		// loop over parameters
		for (int i = 1; i <= parameters.length; i++) {
			Object parameter = parameters[i - 1];

			if (parameter == null) {
				try {
					setMetaData = ps.getParameterMetaData();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// get sql type from prepared statement meta data
				int sqlType = 0;
				try {
					sqlType = setMetaData.getParameterType(i);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// set null parameter
				try {
					ps.setNull(i, sqlType);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				continue;
			}

			try {

				@SuppressWarnings("rawtypes")
				final Class type = parameter.getClass();

				if (type == Boolean.class || type == boolean.class) {
					ps.setBoolean(i, (Boolean) parameter);
				} else if (type == Byte.class || type == byte.class) {
					ps.setByte(i, (Byte) parameter);
				} else if (type == Short.class || type == short.class) {
					ps.setShort(i, (Short) parameter);
				} else if (type == Integer.class || type == int.class) {
					ps.setInt(i, (Integer) parameter);
				} else if (type == Long.class || type == long.class) {
					ps.setLong(i, (Long) parameter);
				} else if (type == Float.class || type == float.class) {
					ps.setFloat(i, (Float) parameter);
				} else if (type == Double.class || type == double.class) {
					ps.setDouble(i, (Double) parameter);
				} else if (type == Character.class || type == char.class) {
					ps.setString(i, parameter == null ? null : "" + (Character) parameter);
				} else if (type == char[].class) {
					// not efficient, will create a new String object
					ps.setString(i, parameter == null ? null : new String((char[]) parameter));
				} else if (type == Character[].class) {
					// not efficient, will duplicate the array and create a new String object
					final Character[] src = (Character[]) parameter;
					final char[] dst = new char[src.length];
					for (int j = 0; j < src.length; j++) { // can't use System.arraycopy here
						dst[j] = src[j];
					}
					ps.setString(i, new String(dst));
				} else if (type == String.class) {
					ps.setString(i, (String) parameter);
				} else if (type == BigDecimal.class) {
					ps.setBigDecimal(i, (BigDecimal) parameter);
				} else if (type == byte[].class) {
					ps.setBytes(i, (byte[]) parameter);
				} else if (type == Byte[].class) {
					// not efficient, will duplicate the array
					final Byte[] src = (Byte[]) parameter;
					final byte[] dst = new byte[src.length];
					for (int j = 0; j < src.length; j++) { // can't use System.arraycopy here
						dst[j] = src[j];
					}
					ps.setBytes(i, dst);
				} else if (parameter instanceof java.io.Reader) {
					final java.io.Reader reader = (java.io.Reader) parameter;

					// the jdbc api for setCharacterStream requires the number
					// of characters to be read so this will end up reading
					// data twice (here and inside the jdbc driver)
					// besides, the reader must support reset()
					int size = 0;
					try {
						reader.reset();
						while (reader.read() != -1) {
							size++;
						}
						reader.reset();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ps.setCharacterStream(i, reader, size);
				} else if (parameter instanceof java.io.InputStream) {
					final java.io.InputStream inputStream = (java.io.InputStream) parameter;

					// the jdbc api for setBinaryStream requires the number of
					// bytes to be read so this will end up reading the stream
					// twice (here and inside the jdbc driver)
					// besides, the stream must support reset()
					int size = 0;
					try {
						inputStream.reset();
						while (inputStream.read() != -1) {
							size++;
						}
						inputStream.reset();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ps.setBinaryStream(i, inputStream, size);
				} else if (parameter instanceof Clob) {
					ps.setClob(i, (Clob) parameter);
				} else if (parameter instanceof Blob) {
					ps.setBlob(i, (Blob) parameter);
				} else if (type == java.util.Date.class) {
					final java.util.Date date = (java.util.Date) parameter;
					ps.setTimestamp(i, new java.sql.Timestamp(date.getTime()));
				} else if (type == java.sql.Date.class) {
					ps.setDate(i, (java.sql.Date) parameter);
				} else if (type == java.sql.Time.class) {
					ps.setTime(i, (java.sql.Time) parameter);
				} else if (type == java.sql.Timestamp.class) {
					ps.setTimestamp(i, (java.sql.Timestamp) parameter);
				} else {
					// last resort; this should cover all database-specific
					// object types
					ps.setObject(i, parameter);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}

	}
}

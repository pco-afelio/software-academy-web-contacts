package be.afelio.software_academy.pco.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import be.afelio.software_academy.pco.beans.Contact;
import be.afelio.software_academy.pco.beans.Country;
import be.afelio.software_academy.pco.beans.Tag;

public class DataRepository {
	
	private String url;
	private String user;
	private String password;
	
	public DataRepository(String url, String user, String password) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public List<Tag> findAllTags() {
		List<Tag> list = new ArrayList<>();
		String sql = "select id as TagId, tag as tagValue from tags";
		try (
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql)
		) {
			while (resultSet.next()) {
				Tag tag = createTag(resultSet);
				list.add(tag);
			}
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return list;
	}
	
	public Tag findOneTagById(int id) {
		Tag tag = null;
		String sql = "select id as TagId, tag as tagValue from tags where id = ?";
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
		) {
			statement.setInt(1, id);
			try (
				ResultSet resultSet = statement.executeQuery()
			) {
				if (resultSet.next()) {
					tag = createTag(resultSet);
				}
			}
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return tag;
	}
	
	public Tag findOneTagByValue(String value) {
		Tag tag = null;
		if (value != null && !value.isBlank()) {
			String sql = "select id as TagId, tag as tagValue from tags where tag = ?";
			try (
				Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
			) {
				statement.setString(1, value);
				try (
					ResultSet resultSet = statement.executeQuery()
				) {
					if (resultSet.next()) {
						tag = createTag(resultSet);
					}
				}
			} catch(SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		}
		return tag;
	}
	
	public void deleteTagById(int id) {
		String sql = "delete from tags where id = ?";
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
		) {
			connection.setAutoCommit(true);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}
	
	public void addTag(String value) {
		if (value != null && !value.isBlank() && findOneTagByValue(value) == null) {
			String sql = "insert into tags(tag) values(?)";
			try (
				Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
			) {
				connection.setAutoCommit(true);
				statement.setString(1, value);
				statement.executeUpdate();
			} catch(SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		}
	}

	public List<Country> findAllCountries() {
		List<Country> list = new ArrayList<>();
		String sql = "select id as countryId, nom as countryName, abreviation as countryAbbreviation from pays";
		try (
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql)
		) {
			while (resultSet.next()) {
				Country country = createCountry(resultSet);
				list.add(country);
			}
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return list;
	}
	
	public Country findOneCountryById(int id) {
		Country country = null;
		String sql = "select id as countryId, nom as countryName, abreviation as countryAbbreviation from pays where id = ?";
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql)
		) {
			statement.setInt(1, id);
			try (
				ResultSet resultSet = statement.executeQuery()
			) {
				if (resultSet.next()) {
					country = createCountry(resultSet);
				}
			}
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return country;
	}

	public Country findOneCountryByName(String name) {
		Country country = null;
		if (name != null && !name.isBlank()) {
			String sql = "select id as countryId, nom as countryName, abreviation as countryAbbreviation from pays where nom = ?";
			try (
				Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)
			) {
				statement.setString(1, name);
				try (
					ResultSet resultSet = statement.executeQuery()
				) {
					if (resultSet.next()) {
						country = createCountry(resultSet);
					}
				}
			} catch(SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		}
		return country;
	}
	
	public Country findOneCountryByAbbreviation(String abbreviation) {
		Country country = null;
		if (abbreviation != null && !abbreviation.isBlank()) {
			String sql = "select id as countryId, nom as countryName, abreviation as countryAbbreviation "
					+ "from pays where abreviation = ?";
			try (
				Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)
			) {
				statement.setString(1, abbreviation);
				try (
					ResultSet resultSet = statement.executeQuery()
				) {
					if (resultSet.next()) {
						country = createCountry(resultSet);
					}
				}
			} catch(SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		}
		return country;
	}
	
	public void addCountry(String name, String abbreviation) {
		if (name != null && !name.isBlank() && abbreviation != null && !abbreviation.isBlank()
				&& findOneCountryByAbbreviation(abbreviation) == null
				&& findOneCountryByName(name) == null) {
			String sql = "insert into pays(nom, abreviation) values(?, ?)";
			try (
				Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
			) {
				connection.setAutoCommit(true);
				statement.setString(1, name);
				statement.setString(2, abbreviation);
				statement.executeUpdate();
			} catch(SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		}
	}	
	
	public void deleteCountryById(int id) {
		String sql = "delete from pays where id = ?";
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
		) {
			connection.setAutoCommit(true);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

	public List<Contact> findAllContact() {
		List<Contact> list = new ArrayList<>();
		String sql = 
				  " select c.id as contactId, c.prenom as contactFirstname, c.nom as contactName, c.email as contactEmail, "
				+ "        p.id as countryId, p.nom as countryName, p.abreviation as countryAbbreviation,"
				+ "        t.id as tagId, t.tag as tagValue "
				+ " from contacts c left join pays p on p.id = c.pays "
				+ "                 left join contacts_tags ct on c.id = ct.contact "
				+ "                 left join tags t on t.id = ct.tag "
				+ " order by c.prenom, c.nom ";
		try (
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql)
		) {
			Contact contact = null;
			while (resultSet.next()) {
				int contactId = resultSet.getInt("contactId");
				if (contact == null || contact.getId() != contactId) {
					contact = createContact(resultSet);
					list.add(contact);
				}
				if (resultSet.getString("tagValue") != null) {
					Tag tag = createTag(resultSet);
					contact.getTags().add(tag);
				}
			}
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return list;
	}	
	
	public Contact findOneContactByFirstnameAndName(String firstname, String name) {
		Contact contact = null;
		String sql = 
				  " select c.id as contactId, c.prenom as contactFirstname, c.nom as contactName, c.email as contactEmail, "
				+ "        p.id as countryId, p.nom as countryName, p.abreviation as countryAbbreviation,"
				+ "        t.id as tagId, t.tag as tagValue "
				+ " from contacts c left join pays p on p.id = c.pays "
				+ "                 left join contacts_tags ct on c.id = ct.contact "
				+ "                 left join tags t on t.id = ct.tag "
				+ " where c.prenom = ? and c.nom = ? "
				+ " order by c.prenom, c.nom ";
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql)
		) {
			statement.setString(1, firstname);
			statement.setString(2, name);
			try (
				ResultSet resultSet = statement.executeQuery()
			) {
				while (resultSet.next()) {
					if (contact == null) {
						contact = createContact(resultSet);
					}
					if (resultSet.getString("tagValue") != null) {
						Tag tag = createTag(resultSet);
						contact.getTags().add(tag);
					}
				}
			}
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return contact;
	}	
	
	public Contact findOneContactById(int id) {
		Contact contact = null;
		String sql = 
				  " select c.id as contactId, c.prenom as contactFirstname, c.nom as contactName, c.email as contactEmail, "
				+ "        p.id as countryId, p.nom as countryName, p.abreviation as countryAbbreviation,"
				+ "        t.id as tagId, t.tag as tagValue "
				+ " from contacts c left join pays p on p.id = c.pays "
				+ "                 left join contacts_tags ct on c.id = ct.contact "
				+ "                 left join tags t on t.id = ct.tag "
				+ " where c.id = ? "
				+ " order by c.prenom, c.nom ";
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql)
		) {
			statement.setInt(1, id);
			try (
				ResultSet resultSet = statement.executeQuery()
			) {
				while (resultSet.next()) {
					if (contact == null) {
						contact = createContact(resultSet);
					}
					if (resultSet.getString("tagValue") != null) {
						Tag tag = createTag(resultSet);
						contact.getTags().add(tag);
					}
				}
			}
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return contact;
	}
	
	public void addContact(String firstname, String name, String email, Integer countryId, Integer[] tagIds) {
		if (name != null && !name.isBlank() 
				&& firstname != null && !firstname.isBlank() 
				&& email != null && !email.isBlank()
				&& (countryId == null || findOneCountryById(countryId) != null)
				&& findOneContactByFirstnameAndName(firstname, name) == null) {
			boolean doInsert = true;
			for (int i = 0; doInsert && i < tagIds.length; i++) {
				doInsert = findOneTagById(tagIds[i]) != null;
			}
			if (doInsert) {
				try (
					Connection connection = createConnection()
				) {
					connection.setAutoCommit(false);
					int contactId = insertContactRowAndReturnGeneratedKey(connection, firstname, name, email, countryId);
					insertContactTagsRows(connection, contactId, tagIds);
					connection.commit();
				} catch(SQLException sqle) {
					throw new RuntimeException(sqle);
				}
			}
		}
	}
	
	public void deleteContactById(int id) {
		String sql = "delete from contacts where id = ?";
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
		) {
			connection.setAutoCommit(true);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}
	
	protected Connection createConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
	protected Tag createTag(ResultSet rs) throws SQLException {
		int id = rs.getInt("tagId");
		String value = rs.getString("tagValue");
		Tag t = new Tag();
		t.setId(id);
		t.setValue(value);
		return t;
	}
	
	protected Country createCountry(ResultSet rs) throws SQLException {
		int id = rs.getInt("countryId");
		String name = rs.getString("countryName");
		String abbreviation = rs.getString("countryAbbreviation");
		Country country = new Country();
		country.setId(id);
		country.setName(name);
		country.setAbbreviation(abbreviation);
		return country;
	}
	
	protected Contact createContact(ResultSet rs) throws SQLException {
		int id = rs.getInt("contactId");
		String firstname = rs.getString("contactFirstname");
		String name = rs.getString("contactName");
		String email = rs.getString("contactEmail");
		
		Country country = null;
		if (rs.getString("countryAbbreviation") != null) {
			country = createCountry(rs);
		}
		
		Contact contact = new Contact();
		contact.setId(id);
		contact.setFirstname(firstname);
		contact.setName(name);
		contact.setEmail(email);
		contact.setCountry(country);
		
		return contact;
	}
	
	protected int insertContactRowAndReturnGeneratedKey(Connection connection, String firstname, String name, String email, Integer countryId)
		throws SQLException {
		int id = 0;
		String sql = "insert into contacts(prenom, nom, email, pays) values(?, ?, ?, ?)";
		try (
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
		) {
			statement.setString(1, firstname);
			statement.setString(2, name);
			statement.setString(3, email);
			if (countryId != null) {
				statement.setInt(4, countryId);
			} else {
				statement.setNull(4, Types.INTEGER);
			}
			statement.executeUpdate();
			try (
				ResultSet resultSet = statement.getGeneratedKeys()	
			) {
				resultSet.next();
				id = resultSet.getInt(1);
			}
			
		}
		return id;
	}
	
	protected void insertContactTagsRows(Connection connection, Integer contactId, Integer[] tagIds)
		throws SQLException {
		String sql = "insert into contacts_tags(contact, tag) values(?, ?)";
		try (
			PreparedStatement statement = connection.prepareStatement(sql)
		) {
			statement.setInt(1, contactId);
			for (int tagId : tagIds) {
				statement.setInt(2, tagId);
				statement.executeUpdate();
			}
		}
	}
}








package se.lexicon.data;

import se.lexicon.DB.MySQLConnection;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PeopleDAOIMPL implements PeopleDAO {


    @Override
    public Person create(Person person) {
        String createSql = "INSERT INTO person (person_id, first_name, last_name) VALUES (?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet  = null;
        Person addedPerson = null; //new Person(personId);


        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, person.getPersonId());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getLastName());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()){
                addedPerson = new Person(resultSet.getInt(1), person.getFirstName(), person.getLastName());
            }

            person = addedPerson  ;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public Collection<Person> findAll() {
        Collection<Person> peopleFound = new ArrayList<>();
        String findAll = "SELECT * FROM person";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet  = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(findAll);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                peopleFound.add(new Person(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peopleFound;
    }

    @Override
    public Person findById(int personId) {
        String findById = "SELECT * FROM person WHERE person_id = ?";
        Person personFound = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(findById);

            preparedStatement.setInt(1, personId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                personFound = new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personFound;
    }

    @Override
    public Collection<Person> findByName(String name) {
        Collection<Person> namesFound = new ArrayList<>();
        String findByName = "SELECT * FROM person WHERE first_name = ?";
        Person personFound = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(findByName);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                namesFound.add( new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return namesFound;

    }

    @Override
    public Person update(Person person) {
        String update = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected= 0;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1,person.getFirstName());
            preparedStatement.setString(2,person.getLastName());
            preparedStatement.setInt(3,person.getPersonId());

            rowsAffected = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (rowsAffected >= 1){
            return person;
        }else {
            return null;
        }
    }

    @Override
    public boolean deleteById(int personId) {

        String deleteById = "DELETE FROM person WHERE person_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean wasDeleted = false;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(deleteById);
            preparedStatement.setInt(1, personId);

            wasDeleted = preparedStatement.executeUpdate() >= 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wasDeleted;
    }





}


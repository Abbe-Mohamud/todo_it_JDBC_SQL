package se.lexicon.data;

import se.lexicon.DB.MySQLConnection;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class TodoItemDAOIMPL implements TodoItemDAO{

    @Override
    public Todo create(Todo todo) {
        String create = "INSERT INTO todo_item (todo_id, title, description, deadline, done, assignee_id) VALUES (?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet  = null;



        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, todo.getTodoId());
            preparedStatement.setString(2, todo.getTitle());
            preparedStatement.setString(3, todo.getDescription());
            preparedStatement.setString(4, todo.getDeadLine().toString());
            preparedStatement.setBoolean(5, todo.isDone());
            preparedStatement.setInt(6, todo.getAssigneeId());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

           if (resultSet.next()){
               todo = new Todo(
                       resultSet.getInt(1), todo.getTitle(), todo.getDescription(), todo.getDeadLine(),
                                todo.isDone(), todo.getAssigneeId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    @Override
    public Collection<Todo> findAll() {
        Collection<Todo> todosFound = new ArrayList<>();
        String findAll = "SELECT * FROM todo_item";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(findAll);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                todosFound.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todosFound;

    }

    @Override
    public Todo findById(int todoId) {
        String findById = "SELECT * FROM todo_item WHERE todo_id = ?";
        Todo todoFound = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(findById);

            preparedStatement.setInt(1, todoId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                todoFound = new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoFound;

    }

    @Override
    public Collection<Todo> findByDoneStatus(boolean done) {
        Collection<Todo> todosDone = new ArrayList<>();
        String findDoneStatus = "SELECT * FROM todo_item WHERE done = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(findDoneStatus);
            preparedStatement.setBoolean(1, done);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                todosDone.add( new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)));

            }

        } catch (SQLException e) {
        e.printStackTrace();
    }

        return todosDone;
    }

    @Override
    public Collection<Todo> findByAssignee(int assignee) {

        Collection<Todo> todosAssigned = new ArrayList<>();
        String findByAssignee = "SELECT * FROM todo_item WHERE assignee_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(findByAssignee);
            preparedStatement.setInt(1, assignee);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                todosAssigned.add( new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todosAssigned;
    }

    @Override
    public Collection<Todo> findByAssignee(Person person) {
        Collection<Todo> todosAssigned = new ArrayList<>();
        String findByAssignee = "SELECT * FROM todo_item WHERE assignee_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(findByAssignee);
            preparedStatement.setInt(1, person.getPersonId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                todosAssigned.add( new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todosAssigned;
    }

    @Override
    public Collection<Todo> unAssignedTodoItems() {
        Collection<Todo> todosUnAssigned = new ArrayList<>();
        String findUnAssigned = "SELECT * FROM todo_item WHERE assignee_id = null";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(findUnAssigned);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                todosUnAssigned.add( new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(null)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todosUnAssigned;

    }

    @Override
    public Todo update(Todo todo) {
        String update = "Update todo_item set title = ? , description = ? , deadline = ? , done = ? , Assignee_id = ? where todo_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setDate(3, Date.valueOf(todo.getDeadLine()));
            preparedStatement.setBoolean(4, todo.isDone());
            preparedStatement.setInt(5, todo.getAssigneeId());
            preparedStatement.setInt(6, todo.getTodoId());
            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        if (rowsAffected >= 1){
            return todo;
        }else {
            return null;
        }
    }

    @Override
    public boolean deleteById(int todoId) {
        String deleteById = "DELETE FROM todo_item WHERE todo_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean wasDeleted = false;

        try {
            connection = MySQLConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(deleteById);
            preparedStatement.setInt(1, todoId);

            wasDeleted = preparedStatement.executeUpdate() >= 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wasDeleted;
    }



}







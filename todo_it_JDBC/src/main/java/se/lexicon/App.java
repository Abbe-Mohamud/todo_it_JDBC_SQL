package se.lexicon;


import se.lexicon.data.PeopleDAO;
import se.lexicon.data.PeopleDAOIMPL;
import se.lexicon.data.TodoItemDAO;
import se.lexicon.data.TodoItemDAOIMPL;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.util.Collection;

public class App {

    private static Object PeopleDAO;

    public static void main(String[] args ){


        /* #########################################################*/
                // ######## Person #########//

        PeopleDAO pe = new PeopleDAOIMPL();

        // CREATE
        // Person person = new Person (6,"jamne", "bond");
        // pe.create(person);
        //System.out.println("person = " + person);

        //  ####  findAll   #####  //
        //Collection<Person> all = peopleDAO.findAll();
        // all.forEach(System.out::println);

        // ####  DELETE #### //
        //boolean wasDeleted = pe.deleteById(6);
        //System.out.println(wasDeleted);

        //  ####  findById   #####  //
        //Person  person = pe.findById(1);
        //System.out.println(person);

        //  ####  findByName   #####  //
        // Collection<Person> person = pe.findByName("james");
        //System.out.println(person);

        //  ####  UPDATE   #####  //
        // Person person = pe.findById(6);
        //person.setFirstName("Layla");
         //pe.update(person);




        /* #########################################################*/
                   // ######## TODOITEMS #########//

        TodoItemDAO to = new TodoItemDAOIMPL();

        // ####   CREATE   #### //
        //Todo todo = new Todo(5, "Hino", "Truck", LocalDate.of(2021,8,29), true,5);
        //todo = to.create(todo);
        //System.out.println("todo = " + todo);

            // #####  findAll  #### //
        //Collection<Todo> todo = to.findAll();
        //System.out.println(todo.toString());

          //  ####  findById   #####  //
         //Todo  todo = to.findById(1);
         //System.out.println(todo);


        //  ####  FINDBYASSIGNEE(assignee)   #####  //
        // Collection<Todo> todo4 = to.findByAssignee(2);
        //System.out.println(todo4);

        //  ####  UPDATE   #####  //
        //Todo todo = to.findById(5);
        //todo.setDescription("boat");
        //todo = to.update(todo);

        // ####  DELETE #### //
        //boolean wasDeleted = to.deleteById(6);
        //System.out.println(wasDeleted);


        

    }


}





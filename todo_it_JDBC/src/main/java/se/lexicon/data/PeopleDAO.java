package se.lexicon.data;

import se.lexicon.model.Person;

import java.util.Collection;

public interface PeopleDAO {

    Person create(Person person);
    Collection<Person> findAll();
    Person findById(int personId);
    Collection<Person> findByName(String name);
    Person update(Person person);
    boolean deleteById(int personId);


}

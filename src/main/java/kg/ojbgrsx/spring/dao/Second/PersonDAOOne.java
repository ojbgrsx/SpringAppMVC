package kg.ojbgrsx.spring.dao.Second;

import kg.ojbgrsx.spring.models.Second.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String email){
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?",new Object[]{email},new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void create(Person person) {
        jdbcTemplate.update("INSERT INTO person(name,age,email,address) VALUES (?,?,?,?)", person.getName(), person.getAge(), person.getEmail(),person.getAddress());
    }

    public void edit(int id, Person person) {
        jdbcTemplate.update("update person set name = ?,age = ?, email=?,address=? where id = ?", person.getName(), person.getAge(), person.getEmail(), person.getAddress(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public void testMultipleUpdate(){
        List<Person> people = create1000People();

        System.out.println(people);

        long before = System.currentTimeMillis();

        for(Person person:people){
            jdbcTemplate.update("INSERT INTO person(name,age,email) VALUES (?,?,?)", person.getName(), person.getAge(), person.getEmail());
        }

        long after = System.currentTimeMillis();

        System.out.println("Time: "+(after-before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for(int i=0;i<1000;i++){
            people.add(new Person("NAME "+i,45,"test"+i+"@gmail.com", "address"));
        }
        return people;
    }

    public void testBatchUpdate(){
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO person (name,age,email) VALUES (?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1,people.get(i).getName());
                ps.setInt(2,people.get(i).getAge());
                ps.setString(3,people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });


        long after = System.currentTimeMillis();

        System.out.println("Time: " +(after-before));
    }
}

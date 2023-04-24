package ezenweb.web.domain.todo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoEntityRepository extends JpaRepository< TodoEntity , Integer> {

}

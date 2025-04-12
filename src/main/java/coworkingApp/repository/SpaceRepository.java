package coworkingApp.repository;

import coworkingApp.entity.CoworkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SpaceRepository extends JpaRepository<CoworkingSpace, Integer> {
    List<CoworkingSpace> findByIsAvailable(boolean b);
}

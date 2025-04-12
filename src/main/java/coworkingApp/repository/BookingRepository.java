package coworkingApp.repository;

import coworkingApp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>  {
    List<Booking> findByCustomer_Id(int customerId);
}


package com.jobscheduler.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jobscheduler.dto.DebitCardEntity;

@Repository
public interface CardRepository extends JpaRepository<DebitCardEntity, Long>{
	
	@Query(
			  value = "SELECT * FROM debit_card_entity u WHERE u.status not in ('E') ", 
			  nativeQuery = true)
			public Collection<DebitCardEntity> findNotExpiredDebitCard();
}

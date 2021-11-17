package com.jobscheduler;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.jobscheduler.dto.DebitCardEntity;
import com.jobscheduler.repository.CardRepository;

@SpringBootApplication
@EnableScheduling
public class JobschedulerApplication {

	@Autowired
	private CardRepository cardRepository;

	public static void main(String[] args) {
		SpringApplication.run(JobschedulerApplication.class, args);
	}

	@Scheduled(fixedDelay = 10000L)
	public void runJob() {

		Collection<DebitCardEntity> list = cardRepository.findNotExpiredDebitCard();

		List<DebitCardEntity> list2 = list.stream()
				.filter(x -> x.getExpiredDate().before(new Timestamp(new Date().getTime()))).map(y -> {
					y.setStatus("E");
					return y;
				}).collect(Collectors.toList());
		
		if (!list2.isEmpty()) {
			cardRepository.saveAllAndFlush(list2);
		}
		System.out.println("Job Started ......");
	}
}

package edu.gmu.springboot.survey.repository;

import edu.gmu.springboot.survey.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}

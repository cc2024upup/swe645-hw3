package edu.gmu.springboot.survey.controller;

import edu.gmu.springboot.survey.model.Survey;
import edu.gmu.springboot.survey.repository.SurveyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/surveys")
@CrossOrigin(origins = "*")
public class SurveyController {

    private final SurveyRepository surveyRepository;

    public SurveyController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    @PostMapping
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyRepository.save(survey);
    }

    @GetMapping("/{id}")
    public Optional<Survey> getSurveyById(@PathVariable Long id) {
        return surveyRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Survey updateSurvey(@PathVariable Long id, @RequestBody Survey survey) {
        survey.setId(id);
        return surveyRepository.save(survey);
    }

    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable Long id) {
        surveyRepository.deleteById(id);
    }
}

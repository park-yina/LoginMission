package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysite.sbb.DataNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@RequiredArgsConstructor
@Service
public class QuestionService {
private final QuestionRepository questionRepo;
public Page<Question>getList( int page){
	List<Sort.Order>sorts=new ArrayList<>();
	sorts.add(Sort.Order.desc("createDate"));
	Pageable pageable=PageRequest.of(page, 10,Sort.by(sorts));
	return this.questionRepo.findAll(pageable);
}
public List<Question> getList(){
	return this.questionRepo.findAll();
}
public Question getQuestion(Integer id) {
	Optional<Question> question=this.questionRepo.findById(id);
	if(question.isPresent()) {
		return question.get();
	}
	else {
		throw new DataNotFoundException("question not found");
	}
}
public void create(String subject,String content) {
	Question q=new Question();
	q.setSubject(subject);
	q.setContent(content);
	q.setCreateDate(LocalDateTime.now());
	this.questionRepo.save(q);
}
}

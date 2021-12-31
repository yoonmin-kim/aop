package hello.aop.exam;

import org.springframework.stereotype.Service;

import hello.aop.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamService {

	private final ExamRepository examRepository;

	@Trace
	public String request(String itemId) {
		return examRepository.save(itemId);
	}
}

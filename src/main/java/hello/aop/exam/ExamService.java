package hello.aop.exam;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamService {

	private final ExamRepository examRepository;

	public String request(String itemId) {
		return examRepository.save(itemId);
	}
}

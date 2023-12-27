package com.web.refactor.service;

import com.web.refactor.auth.CustomPasswordEncoder;
import com.web.refactor.domain.entity.employee.Employee;
import com.web.refactor.model.dto.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SendMailService {

	private final JavaMailSender javaMailSender;
	private final CustomPasswordEncoder customPasswordEncoder;

	@Value("${spring.mail.username}")
	String FROM_ADDRESS;

	public void createMailAndChangePassword(Employee employee) {

		String str = getTempPassword();

		MailDTO script = MailDTO.builder()
				.address(employee.getEmail())
				.title(employee.getEmployeeName() + " 님의 임시 비밀번호 안내 관련 이메일입니다.")
				.message("안녕하세요. [ " + employee.getEmployeeName() + " ] 님의 임시 비밀번호는 " + str + " 입니다.")
				.build();

		mailSend(script);
		employee.setPassword(customPasswordEncoder.encode(str));
		employee.setIsLocked(true);
	}

	private String getTempPassword() {

		char[] charSet = new char[]{
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};

		StringBuilder tempPassword = new StringBuilder();

		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			char randomWord = charSet[random.nextInt(charSet.length)];
			tempPassword.append(randomWord);
		}

		return tempPassword.toString();
	}

	private void mailSend(MailDTO mailDTO) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailDTO.getAddress());
		message.setFrom(FROM_ADDRESS);
		message.setSubject(mailDTO.getTitle());
		message.setText(mailDTO.getMessage());

		javaMailSender.send(message);
	}
}

package com.example.demo.controller;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.MemberDao;
import com.example.demo.vo.MemberVo;

@RestController
public class MemberController {
	
	@Autowired
	private MemberDao dao;

	public void setDao(MemberDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	   private JavaMailSender mailSender;

	   public void setJavaMailSender(JavaMailSender mailSender) {
	      this.mailSender = mailSender;
	   }
	
	   
	   @RequestMapping("/email.do")
	   public String mail(String in1)
	   {
		  Random rd = new Random();
		  String r = "" + rd.nextInt(10);
		  
		  System.out.println(in1);
		  
	      mailSender.send(new MimeMessagePreparator() {
	         public void prepare(MimeMessage mimeMessage) throws MessagingException {
	           MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	           message.setFrom(in1);
	           message.setTo(in1);
	           message.setSubject("인증 번호");
	           message.setText(r, true);
	           //message.addInline("myLogo", new ClassPathResource("static/mylogo.gif"));
	           //message.addAttachment("myDocument.pdf", new ClassPathResource("doc/myDocument.pdf"));
	         }
	         
	       });
	      return r;
		}  
	   
	   
	
	@RequestMapping("/insert.do")
	public String insert(MemberVo m) {
		String str = "";
		int re = dao.insert(m);
		
		if(re>0) {
			str = "회원가입 성공";
		}else {
			str = "회원가입 실패";
		}
		
		return str;
	}
}

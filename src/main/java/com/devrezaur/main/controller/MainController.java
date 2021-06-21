package dev.rezaur.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {
	
	@GetMapping("/admin")
	public String hello() {
		return "Hello... admin";
	}
	
	@GetMapping("/faculty")
	public String faculty() {
		return "Hello... faculty";
	}
}

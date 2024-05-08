package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
@GetMapping("/sbb")
@ResponseBody
public String index() {
	return "testing중";
}
@RequestMapping(value="/", method={RequestMethod.GET, RequestMethod.POST})
	public String root() {
		return "redirect:/question/list";
	}
}

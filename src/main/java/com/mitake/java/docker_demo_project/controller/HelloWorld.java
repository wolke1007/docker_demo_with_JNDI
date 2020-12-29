package com.mitake.java.docker_demo_project.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mitake.java.docker_demo_project.dao.DemoDao;


@Controller
public class HelloWorld {

	@Autowired
	private DemoDao demoDao;
	
	@GetMapping(path = "/echo")
	public @ResponseBody String helloWorld(){
        return "Hello Wolrd!";
    }
	
	@GetMapping(path = "/read")
	public @ResponseBody String readDB() throws SQLException{
		String result = demoDao.find();
		return result;
    }
	
	@GetMapping(path = "/write")
	public @ResponseBody String writeDB(@RequestParam(name = "param") String param){
        return demoDao.insert(param) == 1 ? "Write " + param + " into DB Success" : "Write DB Fail";
    }
}

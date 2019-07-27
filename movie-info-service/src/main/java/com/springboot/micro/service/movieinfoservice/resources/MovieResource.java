package com.springboot.micro.service.movieinfoservice.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.micro.service.movieinfoservice.models.Movie;

@RequestMapping("/movies")
@RestController
public class MovieResource {
	
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId)
	{
		return new Movie(movieId, "Sultan");
	}

}

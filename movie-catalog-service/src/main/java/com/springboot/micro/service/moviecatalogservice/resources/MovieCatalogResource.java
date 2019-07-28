package com.springboot.micro.service.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.springboot.micro.service.moviecatalogservice.models.GetCatalogItem;
import com.springboot.micro.service.moviecatalogservice.models.Movie;
import com.springboot.micro.service.moviecatalogservice.models.Rating;
import com.springboot.micro.service.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder builder ;
	
	@RequestMapping("/{userId}")
	public List<GetCatalogItem> getCatalog(@PathVariable("userId") String userId)
	{
		
		
		//List<Rating> ratings = Arrays.asList(new Rating("1234", 4),new Rating("5678", 3));
		
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
		return ratings.getUserRating().stream().map(rating ->
		{
		
			Movie movie= restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
			return new GetCatalogItem(movie.getName(), "Sultan desc", rating.getRating());
			
		}).collect(Collectors.toList());
	
		 //return Collections.singletonList(new GetCatalogItem("Sultan", "Sultan moview", 4));
	}

}

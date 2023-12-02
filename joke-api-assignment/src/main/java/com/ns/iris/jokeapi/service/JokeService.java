package com.ns.iris.jokeapi.service;

import com.ns.iris.jokeapi.model.JokeApiResponse;

public interface JokeService {

   JokeApiResponse fetchJokes(String type, int amount);
}

package com.ns.iris.jokeapi.controller;

import com.ns.iris.jokeapi.model.JokeApiResponse;
import com.ns.iris.jokeapi.service.JokeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokeController {

    @Autowired
    private JokeService jokeService;

    @Operation(summary = "Fetch a joke"
            ,description = "Returns a short decent random joke from an external Joke API"
            ,tags = { "iris" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = JokeApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Jokes not found"),
            @ApiResponse(responseCode = "204", description = "No decent Jokes found")})
    @GetMapping(value = "/joke", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JokeApiResponse> fetchJoke(@RequestParam("type") String type, @RequestParam("amount") int amount){
        return new ResponseEntity<>(jokeService.fetchJokes(type, amount), HttpStatus.OK);
    }
}

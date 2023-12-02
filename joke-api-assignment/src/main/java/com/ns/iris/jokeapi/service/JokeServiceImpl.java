package com.ns.iris.jokeapi.service;

import com.ns.iris.jokeapi.exception.JokesNotFoundException;
import com.ns.iris.jokeapi.model.JokeApiResponse;
import com.ns.iris.jokeapi.model.ResponseDto;
import com.ns.iris.jokeapi.utils.JokeApiConstants;
import com.ns.iris.jokeapi.utils.JokeApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class JokeServiceImpl implements JokeService{

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${api.external.joke.uri}")
    private String externalUri;

    @Override
    public JokeApiResponse fetchJokes(String type, int amount) {

        String url = externalUri+"?type={type}&amount={amount}";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", type);
        uriVariables.put("amount", String.valueOf(amount));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponseDto.class, uriVariables);

        ResponseDto responseDto = response.getBody();

        if(responseDto != null && !CollectionUtils.isEmpty(responseDto.getJokes()))
            return JokeApiUtils.mapExternalResponseToJokeApiResponse(responseDto);
        else
            throw new JokesNotFoundException(JokeApiConstants.JOKES_NOT_FOUND_EXCEPTION);
    }
}

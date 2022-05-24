package com.ead.course.clients;

import com.ead.course.dtos.CourseUserDto;
import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsService utilsService;

    //Com o Register Service (Eureka) isso vai ficar dinamico!
    @Value("${ead.api.url.authuser}")
    private String REQUEST_URI_AUTHUSER;

    public Page<UserDto> getAllUsersByCourses(UUID courseId, Pageable pageable){

        List<UserDto> searchResult = null;
        ResponseEntity<ResponsePageDto<UserDto>> result = null;

        String url = REQUEST_URI_AUTHUSER + utilsService.createUrlGetAllUsersByCourses(courseId, pageable);

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);
        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType); // Resposta aqui é Page
            searchResult = result.getBody().getContent(); // Resposta aqui é somente DTO, tiramos do Page
            log.debug("Response Number of Elements: {} ", searchResult.size());
            log.debug("Response Number of Elements: {}", searchResult.size());
        }catch (HttpStatusCodeException e){

            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        return result.getBody();
    }

    public ResponseEntity<UserDto> getOneUserById(UUID userId){
        String url = REQUEST_URI_AUTHUSER + "/users/" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {
        String url = REQUEST_URI_AUTHUSER + "/users/" + userId +"/courses/subscription";
        var courseUserDto = new CourseUserDto();
        courseUserDto.setCourseId(courseId);
        courseUserDto.setUserId(userId);
        restTemplate.postForObject(url, courseUserDto, String.class);
    }
}

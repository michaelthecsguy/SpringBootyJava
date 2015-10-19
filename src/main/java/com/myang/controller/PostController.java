package com.myang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.myang.dao.Impl.PostDAOImpl;
import com.myang.dao.PostDAO;
import com.myang.dto.NewPostPayloadDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by myang on 10/16/15.
 */

@RestController
public class PostController
{
  //Data persists in Memory.  There is no DB involved for now
  private PostDAO postDao = new PostDAOImpl();
  public Gson gson = new Gson();


  @RequestMapping(method= RequestMethod.GET, value="/helloworld")
  @ResponseBody
  public String helloWorld()
  {
    return "Hello World!";
  }

  /*
  localhost:8080/hello
  localhost:8080/hello?name=You
  */
  @RequestMapping(method = RequestMethod.GET, value = "/hello")
  @ResponseBody
  public String sayHelloToSomeone(@RequestParam(value="name", defaultValue="World") String name)
  {
    return "Hello " + name + "!";
  }

  //Use ResponseEntity to write the HTTP Status
  //-Reference-
  //http://stackoverflow.com/questions/26134331/how-to-respond-with-http-status-code-in-a-spring-mvc-restcontroller-responsebo
  //-Reference about writing a response in Spring-
  //http://stackoverflow.com/questions/3146461/does-spring-mvc-have-response-write-to-output-to-the-browser-directly
  @RequestMapping(method = RequestMethod.POST, value = "/createAPost")
  public ResponseEntity<?> createAPost(HttpServletResponse response, @RequestBody(required = true) String newPostObject)
  {
    try {
      ObjectMapper mapper = new ObjectMapper();
      NewPostPayloadDTO newPostData = mapper.readValue(newPostObject, NewPostPayloadDTO.class);
      if (!newPostData.isValid()) {

        //response.status(HTTP_BAD_REQUEST);
        //return "";
        return new ResponseEntity<String>("Invalid Input Object", HttpStatus.BAD_REQUEST);
      }
      int id = postDao.createPost(newPostData.getTitle(), newPostData.getContent(), newPostData.getCategories());

      //response.status(200);
      //response.type("application/json");
      return new ResponseEntity<String>(""+id, HttpStatus.OK);
    } catch (IOException ex) {
      //response.status(HTTP_BAD_REQUEST);
      return new ResponseEntity<String>("Request Body does not match with DTO Object", HttpStatus.BAD_REQUEST);
    }
  }

  //Spring dictates the HTTP status code
  @RequestMapping(method = RequestMethod.POST, value = "/createPost")
  @ResponseBody
  public String createAPost(@RequestBody(required = true) String newPostObject)
  {
    try {
      ObjectMapper mapper = new ObjectMapper();
      NewPostPayloadDTO newPostData = mapper.readValue(newPostObject, NewPostPayloadDTO.class);

      int id = postDao.createPost(newPostData.getTitle(), newPostData.getContent(), newPostData.getCategories());

      //response.status(200);
      //response.type("application/json");
      return ""+id;
    } catch (IOException ex) {
      //response.status(HTTP_BAD_REQUEST);
      return "Request Body does not match with DTO Object but HTTP status is still 200 instead of 400 in PostMan";
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/listAllPosts")
  @ResponseBody
  public String listAllPosts()
  {
    return dataToJson(postDao.getAllPostsInList());
  }

  public static String dataToJson(Object data)
  {
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      StringWriter sw = new StringWriter();
      mapper.writeValue(sw, data);

      return sw.toString();
    }
    catch (IOException e)
    {
      throw new RuntimeException("IOException from a StringWriter?");
    }
  }
}

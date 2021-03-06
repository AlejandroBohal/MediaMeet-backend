package edu.eci.arsw.mediameet.controllers;

import edu.eci.arsw.mediameet.model.Media;
import edu.eci.arsw.mediameet.service.externalservices.youtube.YoutubeService;
import edu.eci.arsw.mediameet.service.MediaMeetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

//@CrossOrigin(origins = {"http://localhost:4200","https://media-meet.web.app"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    YoutubeService youtubeService;

    @GetMapping("/youtube")
    public ResponseEntity<?> getVideo(@RequestParam String query) throws UnsupportedEncodingException {
        Media video = null;
        Map<String, Object> response = new HashMap<>();
        try {
            video = youtubeService.getVideo(query);
            video.setTime(0);
        } catch (MediaMeetException | IOException e) {
            response.put("mensaje", "No se encontraron videos");
            response.put("error",e.toString());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Media>(video, HttpStatus.OK);
    }

}

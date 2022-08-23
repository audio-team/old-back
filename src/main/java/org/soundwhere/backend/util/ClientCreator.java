package org.soundwhere.backend.util;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ClientCreator {

    public WebClient create() {
        return WebClient.create("http://localhost:5000/rest");
    }
}

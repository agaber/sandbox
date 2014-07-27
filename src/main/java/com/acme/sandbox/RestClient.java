package com.acme.sandbox;

import static java.lang.System.out;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/** Example rest call with Jersey. */
public class RestClient {
	public static void main(String[] args) throws Exception {
	  WebResource resource = Client
	      .create()
        .resource("https://www.googleapis.com/freebase/v1/search");
    ClientResponse response = resource
        .queryParam("filter", "(all owner:\"Berkshire Hathaway\")")
        .queryParam("limit", "5")
        .queryParam("output", "(description)")
        .accept(MediaType.APPLICATION_JSON)
        .get(ClientResponse.class);

    if (response.getStatus() != 200) {
     throw new RuntimeException(
         "Failed : HTTP error code : " + response.getStatus());
    }

    String output = response.getEntity(String.class);
    out.println(output);
	}
}

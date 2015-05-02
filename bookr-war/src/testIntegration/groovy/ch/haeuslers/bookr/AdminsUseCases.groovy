package ch.haeuslers.bookr

import spock.lang.Specification

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget

class AdminsUseCases extends Specification {


    private WebTarget target;

    def setup() {
        Client client = ClientBuilder.newClient();
        target = client.target('http://localhost:8080/bookr/rest/v1/persons');
    }

    def "no users"() {
        expect:
        target.request().get(List.class).isEmpty();
    }
}

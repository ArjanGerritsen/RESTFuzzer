package nl.ou.se.rest.fuzzer.data.domain.rdm;

public enum RdmResponse {

    OK(200, "OK"), BAD_REQUEST(400, "Bard Request"), NOT_FOUND(404, "Not Found");

    Integer statusCode;
    String description;

    RdmResponse(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }
}
package org.faze.userservice.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import org.springframework.http.HttpStatus;

@JsonPropertyOrder({
    "success",
    "message"
})
public class ApiResponse implements Serializable {

  @JsonIgnore
  private static final long serialVersionUID = 1L;

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonIgnore
  private HttpStatus status;

  public ApiResponse() {

  }

  public ApiResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public ApiResponse(Boolean success, String message, HttpStatus httpStatus) {
    this.success = success;
    this.message = message;
    this.status = httpStatus;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }
}

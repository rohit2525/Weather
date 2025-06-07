package org.example.weather.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "Success"),
  @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
  @ApiResponse(responseCode = "404", description = "Not Found - City not found"),
  @ApiResponse(responseCode = "500", description = "Internal Server Error")
})
public @interface StandardWeatherResponses {}

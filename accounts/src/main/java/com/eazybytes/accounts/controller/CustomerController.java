package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.service.ICustomerService;
import jakarta.validation.constraints.Pattern;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private ICustomerService customerService;
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("eazybank-correlation-id") String correlationid,
                                                                   @RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                   String mobileNumber){

        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber,correlationid);
        logger.debug("eazybank-correlationId found: {}",correlationid);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);

    }
}

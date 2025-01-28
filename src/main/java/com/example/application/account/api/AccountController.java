package com.example.application.account.api;

import com.example.application.account.dao.dto.AccountDto;
import com.example.application.account.exception.AccountNotFoundException;
import com.example.application.account.exception.AccountDisabledException;
import com.example.application.account.service.AccountService;
import com.example.application.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Account management system")
public class AccountController extends BaseController {

    private static final String API_PATH = "account";
    private final AccountService accountService;

    @GetMapping(value = API_PATH)
    @ApiOperation(value = "Get account by id")
    public @ResponseBody ResponseEntity<AccountDto> getAccount(@RequestParam Long id) throws AccountNotFoundException, AccountDisabledException {
        return new ResponseEntity<>(accountService.getAccountDtoById(id), HttpStatus.ACCEPTED);
    }
}

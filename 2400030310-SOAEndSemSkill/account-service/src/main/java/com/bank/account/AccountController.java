package com.bank.account;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Internal routes. Clients never call these directly; they go through the gateway.
 * The legacy prefix /api/v1/internal/accounts is also accepted.
 */
@RestController
@RequestMapping({"/internal/accounts", "/api/v1/internal/accounts"})
public class AccountController {

    private static final List<Account> ACCOUNTS = List.of(
            new Account("1001", "Asha Rao", "SAVINGS", 52000.75),
            new Account("1002", "Ravi Kumar", "CURRENT", 120500.00),
            new Account("1003", "Meena Iyer", "SAVINGS", 8450.30));

    @GetMapping
    public List<Account> all() {
        return ACCOUNTS;
    }

    @GetMapping("/{id}")
    public Account byId(@PathVariable String id) {
        return ACCOUNTS.stream().filter(a -> a.id().equals(id)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }
}

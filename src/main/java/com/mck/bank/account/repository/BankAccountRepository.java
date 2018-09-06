package com.mck.bank.account.repository;

import com.mck.bank.account.domain.BankAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}

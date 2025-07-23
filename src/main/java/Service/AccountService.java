package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account register(Account account){
        Account exists = accountDAO.getAccountByUsername(account.getUsername());
        if (exists != null){
            return null;
        }
        return accountDAO.insertAccount(account);
    }
    public Account login(Account account){
        return accountDAO.getAccountByLogin(account.getUsername(), account.getPassword());
    }
    
}

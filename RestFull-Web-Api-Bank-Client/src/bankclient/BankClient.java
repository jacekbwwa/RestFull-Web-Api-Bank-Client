/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankclient;

import Server.Account;
import Server.Customer;
import Server.Transaction;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.GenericType;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Admin
 */
public class BankClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                BankClientForm ex = new BankClientForm();
                ex.setVisible(true);
            }
        });     
        
        /*CustomerClient client = new CustomerClient();
        Customer c = client.find_XML(Customer.class, "1");
        System.out.println(c.getFirstname());
        client.close();*/
    }
    
    public static void CreateCustomer(String firstname, String lastname, 
            String address, String email, String mobile, String pass)
    {
        Customer c = new Customer();
        c.setFirstname(firstname);
        c.setLastname(lastname);
        c.setAddress(address);
        c.setEmail(email);
        c.setMobile(mobile);
        c.setPass(pass);
        
        CustomerClient client = new CustomerClient();
        client.create_XML(c);        
        client.close();        
    }
    public static String EmailExist(String email)
    {
        CustomerClient client = new CustomerClient();
        Object o = client.findByEmail_XML(Customer.class, email);        
        client.close();
        Customer c =(Customer) o ;
        if (c == null) return null;
        else return c.getFirstname();        
    }
    
    public static Customer GetCustomerByEmail(String email)
    {
        CustomerClient client = new CustomerClient();
        Object o = client.findByEmail_XML(Customer.class, email);        
        client.close();
        Customer c =(Customer) o ;
        return c; 
    }
    
    public static Account[] GetCustomerAccounts(int id)
    {
        //GenericType<List<Account>> listAc = new GenericType<List<Account>>() {};
        //String s = listAc.getClass().toString();        
        AccountClient account = new AccountClient();
        String idStr = Integer.toString(id);
        Account[] lista = account.customerAccountsAsArray_XML(Account[].class, idStr);
        
        return lista;
    }
    
    public static String GetAccountBalance( int accountId)
    {
        AccountClient account = new AccountClient();
        String s = account.accountBalance_XML(String.class, Integer.toString(accountId));
        //return Float.parseFloat(s);
        return s;
    }
    
    public static void CreateAccount(String number, String sortCode, Customer c)
    {
        Account acc = new Account();
        acc.setAccountNumber(number);
        acc.setSortCode(sortCode);
        acc.setBalance(BigDecimal.ZERO);
        acc.setCustomerId(c);
        
        AccountClient acn = new AccountClient();
        acn.create_XML(acc);        
        acn.close();        
    }
    
    // tranakcje dla konta
    public static Transaction[] GetTransactions(int id)
    {                
        TransactionClient account = new TransactionClient();
        String idStr = Integer.toString(id);
        Transaction[] lista = account.accountTransactions_XML(Transaction[].class, idStr);
        
        return lista;
    }
    
    public static void CreateTransaction(String amount, String sortCode, 
            String description, Account acc)
    {
        Transaction tr = new Transaction();
        tr.setAccountId(acc);
        //BigDecimal b = new BigDecimal(str);
        tr.setBalance(new BigDecimal(amount));
        tr.setSortCode(sortCode);
        tr.setDescription(description);
        Date date = new Date();
        tr.setTransactionDate(date.toString());
        TransactionClient acn = new TransactionClient();
        acn.create_XML(tr);        
        acn.close();        
    }
}

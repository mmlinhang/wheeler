package lh.wheeluses.service;

import lh.wheel.annotation.mvc.Service;
import lh.wheel.annotation.transaction.Transaction;

@Service
public class TestService {
    @Transaction
    public void testTransaction() {
        System.out.println("testTransaction");
    }
}

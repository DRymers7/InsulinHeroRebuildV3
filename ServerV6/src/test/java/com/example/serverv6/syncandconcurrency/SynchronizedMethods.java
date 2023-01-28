package com.example.serverv6.syncandconcurrency;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SynchronizedMethods {

    private int sum = 0;

    public synchronized void calculate() {
        setSum(getSum() + 1);
    }

    public void performSyncTask() {
        synchronized (this) {
            setSum(getSum()+1);
        }
    }

}

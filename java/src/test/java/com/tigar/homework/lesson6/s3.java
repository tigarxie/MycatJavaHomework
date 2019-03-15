package com.tigar.homework.lesson6;

import org.junit.Test;

/**
 * @author tigar
 * @date 2018/4/21.
 */
public class s3 {

    static Object myobj = new Object();

    @Test
    public void TestWait() throws Exception {

        SimpleObj myobjA = new SimpleObj();
        new Thread() {

//            synchrnized(myobjA) {
//                if (myobjA.isOk()) {
//                    try {
//                        myobjA.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }

            ;

        }.

                run();
    }


    public class SimpleObj {
        private volatile boolean ok = false;
        public boolean isOk() {
            return ok;
        }

        public void setOk(){
            ok = true;
        }
    }
    //public void doIt() {synchrnized(myobj) { if(xxx) { myobjA.wait();}...}
}

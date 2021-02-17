/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import interpreter.bytecode.*;
import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author senatori
 */
public class Program {

    Vector<ByteCode> byteList;
    private java.util.HashMap<String, Integer> addressHash;

    public Program() {
        byteList = new Vector<ByteCode>();
        addressHash = new java.util.HashMap<String, Integer>();



    }

    public void set(ByteCode code) {


        byteList.add(code);  //puts bytecodes in vector container

        //String tmp = code.getClass().toString();
        if (code instanceof LabelCode) {

            String label = ((LabelCode) code).getArgs(); //typecasting ByteCode class to LabelCode to call getArgs 
            Integer index = new Integer((byteList.size()) - 1);  //index of label 

            addressHash.put(label, index);
            // System.out.print("Label Address Identified");
        }
    }

    public void resolveAddress() {

        //Integer index = addressHash.get(hashKey);

        ByteCode code;
        Integer Index;
        int i;

        Iterator<ByteCode> iterator = byteList.iterator();
        while (iterator.hasNext()) {

            code = iterator.next();
            if (code instanceof AddressResolution) { //AddressResolution is an interface

                AddressResolution ARCode = (AddressResolution) code; //cast to interface type to allow dynamic binding

                String label = ARCode.getArgs();
                Index = addressHash.get(label);
                ARCode.setAddress(Index.intValue()); //dynamic binding :)

                //System.out.println("Address verified for" + code.toString());

            }

        }

    }
    
    public ByteCode getCode(int i){
    
        return byteList.get(i);
        
    }
}

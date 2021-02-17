/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import interpreter.bytecode.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author senatori
 */
public class ByteCodeLoader {

    private BufferedReader in;

    public ByteCodeLoader(String programFile) throws IOException {

        in = new BufferedReader(new FileReader(programFile));


    }

    public Program loadCodes() {

        String hashKey, hashValue;
        String nextLine;
        ByteCode bytecode;
        ArrayList<String> tokenList = new ArrayList<String>();  //container to pass literals & id/func names to bytecode instance


        Program program = new Program();
        try {

            nextLine = in.readLine(); //priming the while loop

            while (nextLine != null) {


                StringTokenizer st = new StringTokenizer(nextLine);

                hashKey = st.nextToken();

                hashValue = CodeTable.get(hashKey);
                bytecode = (ByteCode) (Class.forName("interpreter.bytecode." + hashValue).newInstance());


                while (st.hasMoreTokens()) {  //check for things such as literal value & id
                    tokenList.add(st.nextToken());  //ArrayList container, will pass to ByteCode objects 

                }

                bytecode.init(tokenList); //pass list of bytecode arguments bundled in a container

                program.set(bytecode);  //

                tokenList.clear();
                
                nextLine = in.readLine();
            }

        } catch (Exception e) {

            System.out.println("***Something Went Wrong***");
            //throw new IOException("***End of File***");
        }
        
        program.resolveAddress();
        
        return program;
    }
}

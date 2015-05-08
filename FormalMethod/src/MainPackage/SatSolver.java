package MainPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import Swing.MainFrame;

public class SatSolver {

	private String stringDecoded="";
	private int RESULT_CODE=-1;
	public int getRESULT_CODE() {
		return RESULT_CODE;
	}

	public void setRESULT_CODE(int rESULT_CODE) {
		RESULT_CODE = rESULT_CODE;
	}

	public SatSolver() {
		ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(5*60); // 1 hour timeout
        Reader reader = new DimacsReader(solver);
        // CNF filename is given on the command line 
        try {
        	
            IProblem problem = reader.parseInstance("cnf/input.cnf");
            if (problem.isSatisfiable()) {
            	RESULT_CODE=1;
                System.out.println("Satisfiable !");
                stringDecoded=reader.decode(problem.model());
                String[] temp = stringDecoded.split(" ");
                for(int i=0;i<temp.length;i++){
                	MainFrame.textPaneOutput.append(temp[i]+"\n");
                }
                PrintWriter pw = new PrintWriter("cnf/output.cnf");
                reader.decode(problem.model(), pw);
                pw.close();
            } else {
            	RESULT_CODE=0;
                System.out.println("Unsatisfiable !");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        } catch (ParseFormatException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");      
        }
	}
	
	public String getString(){
		return stringDecoded;
	}
}

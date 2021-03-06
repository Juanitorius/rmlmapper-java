package be.ugent.rml;

import be.ugent.rml.functions.FunctionLoader;
import be.ugent.rml.functions.FunctionUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Custom_RML_FnO_Mapper_Test extends TestFunctionCore {

    @Test
    public void evaluate_A001() {
        try {
            File myFile = new File("./src/test/resources/rml-fno-test-cases/functions_dynamic.ttl");
            FunctionLoader functionLoader = new FunctionLoader(myFile);
            Executor executor = this.createExecutor("./rml-fno-test-cases/RMLFNOTC0001-CSV/mapping.ttl", functionLoader);
            doMapping(executor, "./rml-fno-test-cases/RMLFNOTC0001-CSV/output.ttl");
            assertTrue(functionLoader.getLibraryPath("GrelFunctions").endsWith("GrelFunctions_dynamic.jar"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            fail();
        }
    }

    @Test
    public void evaluate_A002() {
        Executor executor = doPreloadMapping("./rml-fno-test-cases/RMLFNOTC0001-CSV/mapping.ttl", "./rml-fno-test-cases/RMLFNOTC0001-CSV/output.ttl");
        assertEquals(executor.getFunctionLoader().getLibraryPath("GrelFunctions"), "__local");
    }

    @Test
    public void evaluate_A003() {
        Executor executor = doMapping("./rml-fno-test-cases/RMLFNOTC0001-CSV/mapping.ttl", "./rml-fno-test-cases/RMLFNOTC0001-CSV/output.ttl");
        String libPath = executor.getFunctionLoader().getLibraryPath("GrelFunctions");
        Class cls = null;
        try {
            cls = FunctionUtils.functionRequire(new File(libPath), "GrelFunctions");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            fail();
        }
        assertEquals("GrelFunctions", cls.getName());
    }

    @Test
    public void evaluate_A004() {
        doPreloadMapping("./rml-fno-test-cases/RMLFNOTCA004/mapping.ttl", "./rml-fno-test-cases/RMLFNOTCA004/output.ttl");
    }

    @Test
    public void evaluate_A004b() {
        doPreloadMapping("./rml-fno-test-cases/RMLFNOTCA004b/mapping.ttl", "./rml-fno-test-cases/RMLFNOTCA004b/output.ttl");
    }

    /**
     * Tests whether the function idlab-fn:readFile is supported correctly by the mapper
     */
    @Test
    public void evaluate_A005() {
        doPreloadMapping("./rml-fno-test-cases/RMLFNOTCA005/mapping.ttl", "./rml-fno-test-cases/RMLFNOTCA005/output.ttl");
    }
}

package br.com.saquepague.web.rest;

import br.com.saquepague.DemoServerApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the DemoServerRest REST controller.
 *
 * @see DemoServerRestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoServerApp.class)
public class DemoServerRestResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        DemoServerRestResource demoServerRestResource = new DemoServerRestResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(demoServerRestResource)
            .build();
    }

    /**
    * Test teste
    */
    @Test
    public void testTeste() throws Exception {
        restMockMvc.perform(get("/api/demo-server-rest/teste"))
            .andExpect(status().isOk());
    }

}

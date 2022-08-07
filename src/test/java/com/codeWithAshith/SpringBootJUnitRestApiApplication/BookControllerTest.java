package com.codeWithAshith.SpringBootJUnitRestApiApplication;

import com.codeWithAshith.SpringBootJUnitRestApiApplication.controller.BookController;
import com.codeWithAshith.SpringBootJUnitRestApiApplication.entity.Book;
import com.codeWithAshith.SpringBootJUnitRestApiApplication.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book RECORD_1 = new Book(1L,"ABCD", "Summary one", 5);
    Book RECORD_2 = new Book(2L,"EFGH", "Summary two", 4);
    Book RECORD_3 = new Book(3L,"IJKL", "Summary three", 3);

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }
}

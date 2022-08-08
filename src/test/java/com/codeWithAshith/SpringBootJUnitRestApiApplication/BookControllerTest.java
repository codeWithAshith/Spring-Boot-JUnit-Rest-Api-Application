package com.codeWithAshith.SpringBootJUnitRestApiApplication;

import com.codeWithAshith.SpringBootJUnitRestApiApplication.controller.BookController;
import com.codeWithAshith.SpringBootJUnitRestApiApplication.entity.Book;
import com.codeWithAshith.SpringBootJUnitRestApiApplication.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {


    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book RECORD_1 = new Book(1L, "ABCD", "Summary one", 5);
    Book RECORD_2 = new Book(2L, "EFGH", "Summary two", 4);
    Book RECORD_3 = new Book(3L, "IJKL", "Summary three", 3);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks_Success() throws Exception {
        List<Book> bookList = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(bookRepository.findAll()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookList)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("IJKL"));
    }

    @Test
    public void getBookById_Success() throws Exception {
        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RECORD_1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ABCD"));
    }

    @Test
    public void createBook_Success() throws Exception {
        Book book = Book.builder()
                .bookId(4L)
                .name("name")
                .summary("sum")
                .rating(3)
                .build();

        Mockito.when(bookRepository.save(book)).thenReturn(book);

        String content = objectWriter.writeValueAsString(book);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);


        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"));
    }

    @Test
    public void updateBook_Success() throws Exception {
        Book updatedBook = Book.builder()
                .bookId(1L)
                .name("updated name")
                .summary("updated sum")
                .rating(3)
                .build();

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(RECORD_1));
        Mockito.when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        String content = objectWriter.writeValueAsString(updatedBook);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updated name"));
    }
}

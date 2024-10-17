import org.example.textingquest.daos.AnswerDAO;
import org.example.textingquest.entities.Answer;
import org.example.textingquest.utils.ConnectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class AnswerDAOTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private AnswerDAO answerDAO;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        answerDAO = AnswerDAO.getInstance();

        // Подменяем ConnectionManager для возврата нашего мок-объекта соединения
        // В этом примере мы будем использовать Mockito, чтобы сделать это
        // Вместо создания нового соединения
        mockStatic(ConnectionManager.class);
        when(ConnectionManager.open()).thenReturn(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }
    @Test
    void testGetAnswersByQuestionId() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getObject("id", Integer.class)).thenReturn(1);
        when(mockResultSet.getObject("question_id", Integer.class)).thenReturn(1);
        when(mockResultSet.getObject("answer_text", String.class)).thenReturn("Answer text");
        when(mockResultSet.getObject("next_question_id", Integer.class)).thenReturn(2);
        when(mockResultSet.getObject("description", String.class)).thenReturn("Description");

        List<Answer> answers = answerDAO.getAnswersByQuestionId(1);
        assertNotNull(answers);
        assertEquals(1, answers.size());
        assertEquals("Answer text", answers.get(0).getAnswer_text());
        assertEquals(1, answers.get(0).getQuestion_id());
    }

    @Test
    void testFindById() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Возвращаем 1 ответ

        when(mockResultSet.getObject("id", Integer.class)).thenReturn(1);
        when(mockResultSet.getObject("question_id", Integer.class)).thenReturn(1);
        when(mockResultSet.getObject("answer_text", String.class)).thenReturn("Answer text");
        when(mockResultSet.getObject("next_question_id", Integer.class)).thenReturn(2);
        when(mockResultSet.getObject("description", String.class)).thenReturn("Description");


        Answer answer = answerDAO.findById(1);

        assertNotNull(answer);
        assertEquals(1, answer.getId());
        assertEquals("Answer text", answer.getAnswer_text());
    }

    @Test
    void testFindById_NotFound() throws SQLException {

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Answer answer = answerDAO.findById(999);
        assertNull(answer);
    }
}

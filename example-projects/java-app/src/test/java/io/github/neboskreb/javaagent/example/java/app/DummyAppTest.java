package io.github.neboskreb.javaagent.example.java.app;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DummyAppTest {
    @Mock
    private List<String> mockList;

    @Test
    void test() {
        assertFalse(mockList.isEmpty());
    }
}

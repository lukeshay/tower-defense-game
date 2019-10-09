package com.example.towerDefender;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

public class MockitoTests extends TestCase {

    @Test
    public void testMockito_basic(){
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

// stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

// the following prints "first"
        System.out.println(mockedList.get(0));

// the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
    }
}

package com.epam.rd.qa.aggregation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    static Map<Integer, Executable> executableMap;

    @BeforeAll
    static void setUp() {
        executableMap = new HashMap<>();
        executableMap.put(0, BaseDeposit::new);
        executableMap.put(1, SpecialDeposit::new);
        executableMap.put(2, LongDeposit::new);
    }

    interface Executable {
        Deposit execute(BigDecimal amount, int period);
    }
    static final Deposit[] DEPOSITS = new Deposit[]{
            new BaseDeposit(new BigDecimal("10.5"), 1),
            new BaseDeposit(new BigDecimal("1000.1"), 2),
            new BaseDeposit(new BigDecimal("2000.1"), 5),
            new SpecialDeposit(new BigDecimal("10.5"), 1),
            new SpecialDeposit(new BigDecimal("1000.1"), 3),
            new SpecialDeposit(new BigDecimal("1000.1"), 6),
            new LongDeposit(new BigDecimal("1"), 1),
            new LongDeposit(new BigDecimal("3000"), 6),
            new LongDeposit(new BigDecimal("2999.99"), 7),
            new LongDeposit(new BigDecimal("2999.99"), 9),
    };

    @Test
    void testDefaultConstructor() throws ReflectiveOperationException {
        Class<Client> clientClass = Client.class;
        Field depositsField = clientClass.getDeclaredField("deposits");
        Client client = new Client();
        depositsField.setAccessible(true);
        int length = Array.getLength(depositsField.get(client));
        assertEquals(10, length, "The length of 'deposits' field " +
                "must be 10");
    }

    @Test
    void testAddDeposit() {
        Client client = new Client();
        for (Deposit deposit : DEPOSITS) {
            assertTrue(client.addDeposit(deposit));

        }
        assertFalse(client.addDeposit(DEPOSITS[0]));
        assertFalse(client.addDeposit(DEPOSITS[0]));
    }

    @ParameterizedTest
    @MethodSource("casesTotalIncome")
    void testTotalMaxIncome(Client client, String expectedTotalIncome, String expectedMaxIncome,
                            int number, String expectedIncome) {
        assertEquals(new BigDecimal(expectedTotalIncome), client.totalIncome(),
                "totalIncome must be " + expectedTotalIncome);
        assertEquals(new BigDecimal(expectedMaxIncome), client.maxIncome(),
                "totalIncome must be " + expectedMaxIncome);
        assertEquals(new BigDecimal(expectedIncome), client.getIncomeByNumber(number),
                "totalIncome must be " + expectedMaxIncome);
    }

    static Stream<Arguments> casesTotalIncome() {
        Random r = new Random(13);
        return Stream.of(
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(7, 5).toArray(Deposit[]::new)), "12228.21", "3729.05",
                        nextInt(r, 0, 10), "1017.74"),
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(5, 2347).toArray(Deposit[]::new)), "5619.26", "2994.91",
                        nextInt(r, 0, 10), "2994.91"),
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(5, 748).toArray(Deposit[]::new)), "4907.88", "1421.75",
                        nextInt(r, 0, 10), "0.00"),
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(5, 12).toArray(Deposit[]::new)), "5596.36", "1846.95",
                        nextInt(r, 0, 10), "0.00"),
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(12, 15).toArray(Deposit[]::new)), "11529.04", "2189.44",
                        nextInt(r, 0, 10), "728.88")
        );
    }

    static Stream<Deposit> generateDeposits(int limit, int seed) {
        Random r = new Random(seed);
        Random classRandom = new Random(seed);
        return Stream.generate(() -> executableMap.get(nextInt(classRandom, 0, 3))
                        .execute(new BigDecimal(nextInt(r, 500, 2000)), nextInt(r, 3, 12)))
                .limit(limit);
    }

    static int nextInt(Random r, int origin, int bound) {
        return r.nextInt(bound - origin) + origin;
    }

    private static class ClientFactory {
        public static Client newInstance(Deposit[] toArray) {
            Client client = new Client();
            for (Deposit deposit : toArray) {
                client.addDeposit(deposit);
            }
            return client;
        }
    }
}
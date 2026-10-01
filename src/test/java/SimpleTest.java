import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    // ===================== PASS =====================

    @Test
    @DisplayName("Kiểm tra phép cộng cơ bản - Case thành công")
    void testAdditionSuccess() {
        System.out.println("Đang chạy: testAdditionSuccess");
        assertEquals(5, 2 + 3, "2 + 3 phải bằng 5");
    }

    @Test
    @DisplayName("Kiểm tra chuỗi không rỗng - Case thành công")
    void testStringNotEmpty() {
        String data = "AgileTest Practice";
        assertFalse(data.isEmpty(), "Chuỗi không được rỗng");
    }

    @Test
    @DisplayName("Kiểm tra tính đúng đắn của logic - Case thành công")
    void testBooleanLogic() {
        boolean isLearningAgileTest = true;
        assertTrue(isLearningAgileTest, "Pass Test Case");
    }

    @Test
    @DisplayName("Kiểm tra nhiều điều kiện cùng lúc với assertAll - Case thành công")
    void testMultipleAssertions() {
        String name = "AgileTest";
        assertAll("Thuộc tính chuỗi",
                () -> assertEquals(9, name.length(), "Độ dài phải là 9"),
                () -> assertTrue(name.startsWith("Agile"), "Phải bắt đầu bằng 'Agile'"),
                () -> assertTrue(name.endsWith("Test"), "Phải kết thúc bằng 'Test'")
        );
    }

    @Test
    @DisplayName("Kiểm tra ném exception đúng loại - Case thành công")
    void testExpectedException() {
        ArithmeticException ex = assertThrows(ArithmeticException.class, () -> {
            int result = 10 / 0;
        });
        assertEquals("/ by zero", ex.getMessage());
    }

    @Test
    @DisplayName("Kiểm tra danh sách và mảng - Case thành công")
    void testCollections() {
        List<String> tools = new ArrayList<>(List.of("Jira", "AgileTest", "GitHub"));
        assertEquals(3, tools.size());
        assertTrue(tools.contains("AgileTest"));
        assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
    }

    @Test
    @DisplayName("Kiểm tra giá trị null / not null - Case thành công")
    void testNullChecks() {
        String value = null;
        String other = "not null";
        assertNull(value);
        assertNotNull(other);
    }

    @Test
    @DisplayName("Kiểm tra hoàn thành trong thời gian cho phép - Case thành công")
    void testWithinTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> Thread.sleep(100));
    }

    @RepeatedTest(value = 3, name = "Lặp lại lần {currentRepetition}/{totalRepetitions}")
    @DisplayName("Kiểm tra lặp lại nhiều lần - Case thành công")
    void testRepeated(RepetitionInfo info) {
        System.out.println("Lần chạy: " + info.getCurrentRepetition());
        assertTrue(info.getCurrentRepetition() <= info.getTotalRepetitions());
    }

    // ===================== FAIL (assertion sai) =====================

    @Test
    @DisplayName("Ví dụ một Test Case thất bại (Fail)")
    void testSubtractionFail() {
        System.out.println("Đang chạy: testSubtractionFail");
        // Cố tình làm sai để kiểm tra cách hiển thị trên Jira
        assertEquals(5, 20 - 10, "20 - 10 không bằng 5 nên case này sẽ FAIL");
    }

    @Test
    @DisplayName("Ví dụ so sánh chuỗi thất bại (Fail)")
    void testStringCompareFail() {
        assertEquals("AgileTest", "Agile Test", "Hai chuỗi khác nhau về khoảng trắng");
    }

    @Test
    @DisplayName("Ví dụ vượt quá thời gian cho phép (Fail do timeout)")
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    void testTimeoutFail() throws InterruptedException {
        Thread.sleep(500);
    }

    // ===================== ERROR (exception không mong muốn) =====================

    @Test
    @DisplayName("Ví dụ lỗi runtime ngoài dự kiến (Error)")
    void testUnexpectedError() {
        String text = null;
        // NullPointerException -> JUnit XML ghi nhận là <error> thay vì <failure>
        text.length();
    }

    // ===================== SKIPPED =====================

    @Test
    @Disabled("Tạm tắt: chức năng chưa phát triển xong")
    @DisplayName("Ví dụ Test Case bị bỏ qua (Disabled)")
    void testDisabled() {
        fail("Case này không được chạy");
    }

    @Test
    @DisplayName("Ví dụ bỏ qua theo điều kiện môi trường (Assumption)")
    void testSkippedByAssumption() {
        // Chỉ chạy khi có biến môi trường RUN_SLOW_TESTS=true, nếu không sẽ SKIPPED
        Assumptions.assumeTrue("true".equals(System.getenv("RUN_SLOW_TESTS")),
                "Bỏ qua vì RUN_SLOW_TESTS chưa bật");
        assertEquals(4, 2 * 2);
    }
}

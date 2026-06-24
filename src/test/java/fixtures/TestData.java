package fixtures;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

/**
 * TestData - Kelas untuk membaca dan menyediakan data uji dari testdata.json.
 *
 * Semua test data dipusatkan di sini agar mudah diubah tanpa menyentuh kode test.
 * Ini memenuhi prinsip "separation of concerns" dan "maintainability".
 */
public class TestData {

    private static JsonNode rootNode;

    static {
        loadTestData();
    }

    /**
     * Membaca file testdata.json dari resources.
     */
    private static void loadTestData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = TestData.class.getClassLoader()
                    .getResourceAsStream("testdata.json");
            if (inputStream == null) {
                throw new RuntimeException("File testdata.json tidak ditemukan di resources!");
            }
            rootNode = mapper.readTree(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Gagal membaca testdata.json: " + e.getMessage(), e);
        }
    }

    /**
     * @return Username untuk login
     */
    public static String getUsername() {
        return rootNode.get("login").get("username").asText();
    }

    /**
     * @return Password untuk login
     */
    public static String getPassword() {
        return rootNode.get("login").get("password").asText();
    }

    /**
     * @return Kata kunci pencarian produk
     */
    public static String getSearchKeyword() {
        return rootNode.get("search").get("keyword").asText();
    }
}

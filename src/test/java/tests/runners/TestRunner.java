package tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestRunner - Kelas runner yang menghubungkan Cucumber dengan TestNG.
 *
 * Kelas ini mengkonfigurasi:
 * - Lokasi file .feature (skenario BDD)
 * - Lokasi step definitions (implementasi Java)
 * - Format laporan output
 *
 * Dijalankan oleh Maven Surefire melalui testng.xml.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"tests.steps"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-report.html",
                "json:target/cucumber-reports/cucumber-report.json"
        },
        monochrome = true,
        tags = "@checkout"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Override data provider agar skenario bisa dijalankan secara paralel.
     * Set parallel = true jika ingin menjalankan skenario secara paralel.
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

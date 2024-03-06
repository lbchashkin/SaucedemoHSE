import org.testng.Assert;

import java.util.List;
import java.util.function.BiPredicate;

public class SortTest extends BaseTest {
    String errorMessage;

    @org.testng.annotations.Test(testName = "Проверка сортировки по имени в обратном порядке")
    public void CheckSortByNameDesc() {
        loginPage.open()
                .login("standard_user", "secret_sauce")
                .selectSort("Name (Z to A)");
        Assert.assertTrue(checkSort(productsPage.getProductNamesList(), SortTest::compareZtoA), errorMessage);
    }

    @org.testng.annotations.Test(testName = "Проверка сортировки по имени")
    public void CheckSortByNameAsc() {
        loginPage.open()
                .login("standard_user", "secret_sauce");
        Assert.assertTrue(checkSort(productsPage.getProductNamesList(), SortTest::compareAtoZ), errorMessage);
    }

    public boolean checkSort(List<String> productNamesList, BiPredicate<String, String> comparator) {
        int i = 1;
        while (i < productNamesList.size()) {
            String productName_prev = productNamesList.get(i - 1);
            String productName = productNamesList.get(i);
            if (!comparator.test(productName_prev, productName)) {
                this.errorMessage = String.format("%s -> %s: Wrong order", productName_prev, productName);
                return false;
            }
            i++;
        }
        return true;
    }

    public static boolean compareAtoZ(String s_prev, String s) {
        return s.compareTo(s_prev) >= 0;
    }

    public static boolean compareZtoA(String s_prev, String s) {
        return s_prev.compareTo(s) >= 0;
    }
}

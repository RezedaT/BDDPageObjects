package ru.netology.test.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.test.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amountInput = $("[data-test-id='amount'] input");
    private SelenideElement fromInput = $("[data-test-id='from'] input");
    private SelenideElement actionButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private SelenideElement headerTransfer = $(byText("Пополнение карты"));

    public TransferPage() {
        headerTransfer.shouldBe(visible);
    }
    public DashboardPage successfulTransfer(DataHelper.Transfer info) {
        amountInput.setValue(info.getAmount().toString());
        fromInput.setValue(info.getCardNumber());
        actionButton.click();
        return new DashboardPage();

    }
    public TransferPage emptyTransfer(String expectedError) {
        amountInput.setValue("");
        fromInput.setValue("");
        actionButton.click();
        errorNotification.shouldBe(visible);
        return null;
    }

    public DashboardPage cancelTransfer(String amountToTransfer, DataHelper.Transfer info) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(info.getCardNumber());
        cancelButton.click();
        return new DashboardPage();
    }

    public void ErrorMessage(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
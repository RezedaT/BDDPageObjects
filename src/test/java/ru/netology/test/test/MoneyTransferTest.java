package ru.netology.test.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.test.data.DataHelper;
import ru.netology.test.page.DashboardPage;
import ru.netology.test.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.test.data.DataHelper.cardFirst;
import static ru.netology.test.data.DataHelper.cardSecond;

class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        var loginPage = open("http://Localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);

    }

    @Test
    @DisplayName("Перевод с первой карты на вторую")
    void shouldTransferMoneyFromFirstToSecond() {
        var firstCardValue = dashboardPage.getFirstCardBalance();
        var secondCardValue = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.clickOnSecondButton();
        var transfer = DataHelper.getValidTransfer(Math.abs(firstCardValue), cardFirst);
        var anotherPage = transferPage.successfulTransfer(transfer);

        var actualBalanceOfFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceOfSecondCard = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(secondCardValue + transfer.getAmount(), actualBalanceOfSecondCard);
        Assertions.assertEquals(firstCardValue - transfer.getAmount(), actualBalanceOfFirstCard);
    }


    @Test
    @DisplayName("Перевод со второй карты на первую")
    void shouldTransferMoneyFromSecondToFirst() {
        var firstCardValue = dashboardPage.getFirstCardBalance();
        var secondCardValue = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.clickOnFirstButton();
        var transfer = DataHelper.getValidTransfer(Math.abs(secondCardValue), cardSecond);
        var anotherPage = transferPage.successfulTransfer(transfer);
        var actualBalanceOfFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceOfSecondCard = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(firstCardValue + transfer.getAmount(), actualBalanceOfFirstCard);
        Assertions.assertEquals(secondCardValue - transfer.getAmount(), actualBalanceOfSecondCard);
    }

}


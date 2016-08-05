package ru.sbt;

public class Main {

    public static void main(String[] args) throws TerminalException, PinValidatorException, TerminalServerException {
        double money = 10000.0;
        String pin = "1234";

        TerminalImpl i = new TerminalImpl();
        i.mainMenu(money, pin, false);
    }
}